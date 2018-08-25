package cn.linkmore.prefecture.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants.BindOrderStatus;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallLockClusterMapper;
import cn.linkmore.prefecture.dao.master.StallLockMasterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.entity.StallLock;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 - 车位信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StallServiceImpl implements StallService { 
	@Autowired
	private StallMasterMapper stallMasterMapper;
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private LockFactory lockFactory;
	@Autowired
	private OrderClient orderClient;
	@Autowired
	private StallLockMasterMapper stallLockMasterMapper;
	@Autowired
	private StallLockClusterMapper stallLockClusterMapper;
	@Autowired
	private SendClient sendClient;
	

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void order(Long id) {
		Stall stall = new Stall();
		stall.setId(id);
		stall.setStatus(StallStatus.USED.status);
		stall.setLockStatus(LockStatus.UP.status);
		stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
		stallMasterMapper.order(stall);
	}

	@Override
	public boolean cancel(Long stallId) {
		Boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null) {
			stall.setStatus(StallStatus.FREE.status);
			stall.setLockStatus(LockStatus.UP.status);
			stall.setBindOrderStatus((short)BindOrderStatus.FREE.status);
			stallMasterMapper.cancel(stall);
			flag = true;
		}
		return flag;
	}
	class StallUpThread extends Thread{ 
		private Stall stall;
		public StallUpThread(Stall stall) {
			this.stall = stall; 
		}
		public void run() { 
			ResponseMessage<LockBean> res = lockFactory.lockUp(stall.getLockSn());
			int code = res.getMsgCode();
			log.info("lock msg:{}", JsonUtil.toJson(res));
			if (code == 200) {  
				redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn()); 
			}
		}
	}
	

	@Override
	public boolean checkout(Long stallId) { 
		log.info("checkout stall :{}",stallId);
		boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			flag = true;
			stall.setUpdateTime(new Date());
			stall.setStatus(StallStatus.FREE.status);
			stall.setLockStatus(LockStatus.UP.status);
			stall.setBindOrderStatus((short)BindOrderStatus.FREE.status);
			this.stallMasterMapper.checkout(stall);
			new StallUpThread(stall).start();
		} 
		return flag;
	} 
	private void downing(ReqOrderStall reqos) {
		Stall stall = stallClusterMapper.findById(reqos.getStallId());
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("downing... name:{},sn:{}",stall.getStallName(),stall.getLockSn()); 
			ResponseMessage<LockBean> res = lockFactory.lockDown(stall.getLockSn());
			log.info("res:{}", JsonUtil.toJson(res));
			int code = res.getMsgCode();
			if (code == 200) {
				stall.setLockStatus(LockStatus.DOWN.status);
				stallMasterMapper.lockdown(stall);
				this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key+reqos.getOrderId());
			}
			orderClient.downMsgPush(reqos.getOrderId(),reqos.getStallId()); 
		}
	}
	
	class DownThread extends Thread{
		private ReqOrderStall reqos;
		public DownThread(ReqOrderStall reqos) {
			this.reqos = reqos;
		}
		public void run() {
			downing(reqos);
		}
	}
	
	@Override 
	public void downlock(ReqOrderStall reqos) { 
		Thread thread = new DownThread(reqos);
		thread.start();
	}

	@Override
	public boolean uplock(Long stallId) {
		boolean flag = true;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("uping... name:{},sn:{}",stall.getStallName(),stall.getLockSn()); 
			ResponseMessage<LockBean> res = lockFactory.lockUp(stall.getLockSn());
			log.info("res:{}", JsonUtil.toJson(res));
			int code = res.getMsgCode();
			if (code != 200) {
				// 此处为升锁操作
				flag = false;
				throw new BusinessException(StatusEnum.ORDER_LOCKUP_FAIL);
			}
			stall.setLockStatus(LockStatus.UP.status);
			stallMasterMapper.lockdown(stall);
		}
		return flag;
	}

	@Override
	public List<ResStall> findStallsByPreId(Long preId) {
		List<ResStall> resStall = stallClusterMapper.findStallsByPreId(preId);
		return resStall;
	}

	@Override
	public ResStallEntity findById(Long stallId) {
		ResStallEntity detail = new ResStallEntity();
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null) {
			return ObjectUtils.copyObject(stall, detail);
		}
		return null;
	}

	@Override
	public ResStallEntity findByLock(String sn) {
		ResStallEntity detail = new ResStallEntity();
		Stall stall = this.stallClusterMapper.findByLockSn(sn);
		if (stall != null) {
			return ObjectUtils.copyObject(stall, detail);
		}
		return null;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.stallClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		param.put("property", "stall_name");
		param.put("direction", "ASC");
		List<Stall> list = this.stallClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public int save(ReqStall reqStall) {
		Date now = new Date();
		reqStall.setSellCount(0);
		reqStall.setGatewayId(0l);
		reqStall.setStatus(4);
		reqStall.setBindOrderStatus((short) 0);
		reqStall.setCreateTime(now);
		reqStall.setUpdateTime(now);
		Stall stall = new Stall();
		stall = ObjectUtils.copyObject(reqStall, stall);
		log.info("--------save--------interface------stall = {}", JSON.toJSON(stall));
		return this.stallMasterMapper.save(stall);
	}

	@Override
	public int update(ReqStall reqStall) {
		Date now = new Date();
		reqStall.setUpdateTime(now);
		Stall stall = new Stall();
		stall = ObjectUtils.copyObject(reqStall, stall);
		return stallMasterMapper.update(stall);
	}

	@Override
	public int check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<>();
		param.put("stallName", reqCheck.getProperty());
		param.put("preId", reqCheck.getValue());
		if (null != reqCheck.getId()) {
			param.put("id", reqCheck.getId());
		}
		return this.stallClusterMapper.check(param);
	}

	@Override
	public int bind(ReqStall stall) {
		Date now = new Date();
		ResStallLock lock = stallLockClusterMapper.findById(stall.getLockId());
		String sn = lock.getSn();
		stall.setLockStatus(null);
		stall.setLockSn(sn);
		stall.setStatus(4);
		stall.setLockBattery(0);
		lock.setPrefectureId(stall.getPreId());
		lock.setBindTime(now);
		lock.setStallId(stall.getId());
		stall.setUpdateTime(now);
		StallLock stallLock = new StallLock();
		stallLock = ObjectUtils.copyObject(lock, stallLock);
		stallLockMasterMapper.updateBind(stallLock);
		
		Stall sta = new Stall();
		sta = ObjectUtils.copyObject(stall, sta);
		log.info("{}:{}>>{},返回结果{}", "绑定车位锁", "车位(" + stall.getStallName() + "),车位锁(" + sn + ")", "绑定成功", 200);
		return stallMasterMapper.update(sta);
	}

	@Override
	public int updateStatus(ReqStall reqStall) {
		Date now = new Date();
		Integer status = reqStall.getStatus();
		String sn = reqStall.getLockSn();
//		Long preId = reqStall.getPreId();
		reqStall.setUpdateTime(now);
		if (status.intValue() == 4) {
			Stall stall = new Stall();
			stall = ObjectUtils.copyObject(reqStall, stall);
			stallMasterMapper.update(stall);
			log.info("下线成功，车位：" + stall.getStallName() + " 序列号：{}", sn);
		} else {
			try {
				ResponseMessage<LockBean> res= lockFactory.getLockInfo(sn);
				LockBean lockBean = res.getData();
				if(lockBean == null) {
					throw new RuntimeException("车位锁通讯失败");
				}
				/*if (lockBean.getOpenState() != 1) {
					lockFactory.lockUp(sn);
				}
				int openState = lockBean.getOpenState();
				reqStall.setLockStatus(openState);
				reqStall.setLockBattery((int) lockBean.getVoltage());*/
				
				
				Stall stall = new Stall();
				stall = ObjectUtils.copyObject(reqStall, stall);
				
				stallMasterMapper.update(stall);
				log.info("上线成功，车位：" + reqStall.getStallName() + " 序列号：{}", sn);
			} catch (Exception e) {
				log.info("lock open err:{}", e.toString());
				throw new RuntimeException("车位锁通讯失败");
			}
		}
		return 0;
	}

	@Override
	public List<ResStall> findList(Map<String, Object> param) {
		List<ResStall> list = this.stallClusterMapper.findList(param);
		return list;
	}

	@Override
	public void saveAndBind(Long preId, String stallName, String sn) {
		Date now = new Date();
		StallLock lock = new StallLock();
		lock.setSn(sn);
		lock.setCreateTime(now);
		this.stallLockMasterMapper.save(lock);
		ReqStall stall = new ReqStall();
		stall.setStallName(stallName);
		stall.setLockStatus(null);
		stall.setLockSn(sn);
		stall.setStatus(4);
		stall.setLockBattery(0);
		stall.setLockId(lock.getId());
		stall.setPreId(preId);
		this.save(stall);
		lock.setPrefectureId(stall.getPreId());
		lock.setBindTime(now);
		lock.setStallId(stall.getId());
	    stallLockMasterMapper.updateBind(lock);
	}

	@Override
	public List<ResStallOps> findListByParam(Map<String, Object> param) {
		return this.stallClusterMapper.findListByParam(param);
	}

	@Override
	public void close(Long id) {
		Stall stall = stallClusterMapper.findById(id);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) { 
			Integer count = (Integer)this.redisService.get(RedisKey.STALL_ORDER_CLOSED.key+id);
			if(count==null) {
				count = 0;
			}
			if(count<3) {
				count = count+1; 
				stall.setStatus(StallStatus.FREE.status);
				stall.setLockStatus(LockStatus.UP.status);
				stall.setBindOrderStatus((short)BindOrderStatus.FREE.status); 
				stall.setUpdateTime(new Date()); 
				this.stallMasterMapper.checkout(stall);
				this.redisService.set(RedisKey.STALL_ORDER_CLOSED.key+id, count,ExpiredTime.STALL_ORDER_CLOSED_TIME.time );  
				//this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
			}else {
				this.redisService.remove(RedisKey.STALL_ORDER_CLOSED.key+id);
				stall.setStatus(StallStatus.OUTLINE.status);
				stall.setBindOrderStatus((short)BindOrderStatus.FREE.status);
				this.redisService.remove(RedisKey.STALL_ORDER_CLOSED.key+id);
				this.stallMasterMapper.offline(stall);
			}
		}
	}

	@Override
	public List<ResStall> findStallList(List<Long> stallIds) {
		List<ResStall> list = this.stallClusterMapper.findTreeList(stallIds);
		return list;
	}

	@Override
	public List<ResStall> findPreStallList(Map<String, Object> param) {
		List<ResStall> list = this.stallClusterMapper.findPreStallList(param);
		return list;
	}

	@Override
	public int updateBrand(Map<String, Object> param) {
		log.info("param = {}",param);
		String brand = (String) param.get("brand");
		List<Long> ids = (List<Long>) param.get("list");
		if("0".equals(brand)) {
			return this.stallMasterMapper.updateBrand(ids);
		}else {
			return this.stallMasterMapper.updateBrandStall(ids);
		}
	}
	
	@Override
	public void controling(ReqControlLock  reqc) {  //控制锁
		new Thread(new Runnable() {
	        @Override
	        public void run() { 
	        	String uid =	String.valueOf(redisService.get(reqc.getKey()));
	        	Stall stall = stallClusterMapper.findById(reqc.getStallId());
				log.info("stall:{}", JsonUtil.toJson(stall));
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("downing... name:{},sn:{}",stall.getStallName(),stall.getLockSn()); 
					ResponseMessage<LockBean> res = null;
		        	//1 降下 2 升起
					if(reqc.getStatus()==1){
					 res = lockFactory.lockDown(stall.getLockSn());
					}else if( reqc.getStatus()==2){
				     res = lockFactory.lockUp(stall.getLockSn());
					}
					log.info("res:{}", JsonUtil.toJson(res));
					int code = res.getMsgCode();
					if (code == 200) {
						stall.setLockStatus(reqc.getStatus());
						stallMasterMapper.lockdown(stall);
						redisService.remove(reqc.getKey());
						sendMsg(uid);
					}
				}
	        }
	    }).start();	
	}
	
	@Override
	public Map<String,Object> lockStatus(List<String> parkcodes) {
		Map<String,Object> map = new HashMap<>();
		ResponseMessage<LockBean>  lb = lockFactory.findAvaiLocks(parkcodes);
		if(lb!=null) {
			map.put("data", lb);
		}
		return map;
	}
	
	public  void sendMsg(String uid) {
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	send(uid);
	        }
	    }).start();	
	}
	
	private void send(String uid) {
		log.info("个人降锁通知-----------------------------"+uid);
		String title = "个人降锁通知";
		String content = "车位锁降下成功";
		PushType type =PushType.LOCK_DOWN_NOTICE ;
		Boolean status = true;
		Token token = (Token) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + uid.toString());
		log.info("send:{}", JsonUtil.toJson(token));
		ReqPush rp = new ReqPush();
		rp.setAlias(uid);
		rp.setTitle(title);
		rp.setContent(content);
		rp.setClient(token.getClient());
		rp.setType(type);
		rp.setData(status.toString());
		sendClient.send(rp);
	}
	
}
