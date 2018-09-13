package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.linkmore.lock.response.ResponseMessage;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.OrderStatusEnum;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.StallEnum;
import cn.linkmore.bean.common.StatusFinal;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.staff.request.AssignStallRequestBean;
import cn.linkmore.enterprise.controller.staff.request.OrderOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.controller.staff.request.StallOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.response.PrefectureResponseBean;
import cn.linkmore.enterprise.dao.cluster.StaffPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.master.StaffPrefectureMasterMapper;
import cn.linkmore.enterprise.entity.OperateSource;
import cn.linkmore.enterprise.entity.OperationEnum;
import cn.linkmore.enterprise.entity.Orders;
import cn.linkmore.enterprise.entity.ResultEnum;
import cn.linkmore.enterprise.entity.Stall;
import cn.linkmore.enterprise.entity.StallOperateLog;
import cn.linkmore.enterprise.service.StaffPrefectureService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.TokenUtil;

@Service
public class StaffPrefectureServiceImpl implements StaffPrefectureService{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisLock redisLock;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private StallClient stallClient;
	
	@Autowired
	private StaffPrefectureClusterMapper staffPrefectureClusterMapper;
	
	@Autowired
	private StaffPrefectureMasterMapper staffPrefectureMasterMapper;
	

	private static final int TIMEOUT= 30*1000;
	
	@Override
	public void control(SraffReqConStall reqOperatStall, HttpServletRequest request) {
		String userid = null;
		long time = System.currentTimeMillis()+TIMEOUT;
		String robkey = RedisKey.MANAGER_STALL.key + reqOperatStall.getStallId();
		if(!redisLock.lock( String.valueOf(robkey),String.valueOf(time))) {
			log.info("no get it,wait a moment");
			throw new BusinessException(StatusEnum.STALL_HIVING_DO);
		}
		String reskey = (reqOperatStall.getState()==1?RedisKey.MANAGER_STALL_DOWN.key:RedisKey.MANAGER_STALL_UP.key);
		StringBuilder sb = new StringBuilder(reskey).append(userid);
		redisService.set(String.valueOf(sb),reqOperatStall.getStallId());
		ReqControlLock reqc = new ReqControlLock();
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		reqc.setKey(reskey);
		stallClient.controllock(reqc);
	}

	@Override
	public ResponseEntity<PrefectureResponseBean> releaseStall(Long stallId, HttpServletRequest request) {
		boolean flag = false;
		String msg = "";
		// 通过车位编号查询该车位是否处于空闲
		Stall stall = selectStallById(stallId);
		if (stall != null) { 
			Orders orders = staffPrefectureClusterMapper.findStallLatest(stallId);
			if(orders!=null&&orders.getStatus().intValue()==Orders.ORDERS_STATUS_UNPAY){
				return null;
			}
			// 如果该车位属于非空闲状态
			if (!StallEnum.valueOf(stall.getStatus()).getValue().equals(StatusFinal.STATUS_FREE)) {
				flag = true;
				msg = "该车位状态处于" + StallEnum.valueOf(stall.getStatus()).getMsg() + "状态"; 
			}
			// 如果该车位绑定的订单状态是挂起
			if (OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getValue().equals(StatusFinal.ORDER_STATUS_HANGUP)) {
				flag = true;
				if (!StringUtils.isEmpty(msg)) {
					msg += ";车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} else {
					msg = "该车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} 
			}

			// 如果该车位绑定的订单状态是关闭
			if (OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getValue().equals(StatusFinal.ORDER_STATUS_CLOSE)) {
				flag = true;
				if (!StringUtils.isEmpty(msg)) {
					msg += ";车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} else {
					msg = "该车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} 
			}
			// 通过车位查询该车位当前订单状态，如果是挂起/关闭，则需要将该车位释放 
			// 对锁执行上升操作
			if (flag) {  
				ResponseMessage<com.linkmore.lock.bean.LockBean> res = null;
				//res = lockFactory.lockUp(stall.getLockSn()); 
				if (res.getMsgCode().intValue() == 200) { 
					// 插入上升成功记录
					this.saveStallOperateRecord(stallId, ResultEnum.SUCCESS.getCode(),
							stall.getLockSn(), OperationEnum.FREE, request); 
					// 需要将该车位设置为空闲
					 updateStallByInfo(stall.getId(), StallEnum.STATUS_FREE,OrderStatusEnum.ORDER_STATUS_NORMAL);
					PrefectureResponseBean prefectureResponseBean=this.findResponseBeanByPreId(stall.getPreId());
					
					return ResponseEntity.success(prefectureResponseBean, request);
				} else {
					// 插入失败的记录
					int i = this.saveStallOperateRecord(stallId, ResultEnum.FAIL.getCode(),
							stall.getLockSn(), OperationEnum.FREE, request);
					log.info("插入下降失败记录的返回值：" + i + "(i>0为成功)");
					log.error("车位锁上升失败");
					return null;
				}
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<PrefectureResponseBean> forceReleaseStall(Long stallId, HttpServletRequest request) {
		boolean flag = false;
		String msg = "";
		// 通过车位编号查询该车位是否处于空闲 
		Stall stall = selectStallById(stallId);
		if (stall != null) { 
			Orders orders = staffPrefectureClusterMapper.findStallLatest(stallId);
			if(orders!=null&&orders.getStatus().intValue()==Orders.ORDERS_STATUS_UNPAY){
				return null;
			}
			// 如果该车位属于非空闲状态
			if (!StallEnum.valueOf(stall.getStatus()).getValue().equals(StatusFinal.STATUS_FREE)) {
				flag = true;
				msg = "该车位状态处于" + StallEnum.valueOf(stall.getStatus()).getMsg() + "状态"; 
			}
			// 如果该车位绑定的订单状态是挂起
			if (OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getValue().equals(StatusFinal.ORDER_STATUS_HANGUP)) {
				flag = true;
				if (StringUtils.isEmpty(msg)) {
					msg += ";车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} else {
					msg = "该车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} 
			}

			// 如果该车位绑定的订单状态是关闭
			if (OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getValue().equals(StatusFinal.ORDER_STATUS_CLOSE)) {
				flag = true;
				if (StringUtils.isEmpty(msg)) {
					msg += ";车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} else {
					msg = "该车位订单处于" + OrderStatusEnum.valueOf(stall.getBindOrderStatus()).getMsg() + "状态";
				} 
			}
			// 通过车位查询该车位当前订单状态，如果是挂起/关闭，则需要将该车位释放 
			// 对锁执行上升操作
			if (flag) {  
				ResponseMessage<com.linkmore.lock.bean.LockBean> res = null;
				//res = lockFactory.lockUp(stall.getLockSn()); 
				if (res.getMsgCode().intValue() == 200) { 
					// 插入上升成功记录
					this.saveStallOperateRecord(stallId, ResultEnum.SUCCESS.getCode(),
							stall.getLockSn(), OperationEnum.FORCE, request);  
					// 需要将该车位设置为空闲
				} else {
					// 插入失败的记录
					int i = this.saveStallOperateRecord(stallId, ResultEnum.FAIL.getCode(),
							stall.getLockSn(), OperationEnum.FORCE, request);
					log.info("插入下降失败记录的返回值：" + i + "(i>0为成功)");
					log.error("车位锁上升失败");
				}
				updateStallByInfo(stall.getId(), StallEnum.STATUS_FREE,OrderStatusEnum.ORDER_STATUS_NORMAL); 
				PrefectureResponseBean prefectureResponseBean = this.findResponseBeanByPreId(stall.getPreId());
				return null;
			}else{
				return null;
			}
		}
		return null;
	}
	
	private Stall selectStallById(long id) {
		return staffPrefectureClusterMapper.selectByPrimaryKey(id);
	}

	private int updateStallByInfo(long id, StallEnum stallEnum,OrderStatusEnum orderStatusEnum) {
		Stall record = new Stall();
		record.setId(id);
		record.setStatus(stallEnum.getCode());
		record.setBindOrderStatus((short) orderStatusEnum.getCode());
		int i = staffPrefectureClusterMapper.updateStallByInfo(record);
		return i;
	} 
	
	public int saveStallOperateRecord(Long stallId,int code,String salveCode, OperationEnum operationEnum,HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		
		StallOperateLog record = new StallOperateLog();
		log.info("获取到用户信息："+JSON.toJSONString(user));
		record.setOperatorId(user.getId());
		record.setSource(OperateSource.ADMIN.getCode());
		record.setStatus(code);
		record.setStallId(stallId);
		record.setCreateTime(DateUtils.getCurrentDateTime());
		record.setOperation((short) operationEnum.getCode());
		record.setRemark("车位编号："+stallId+"车位锁编号："+salveCode+"；上升操作"+(code>0?"成功":"失败"));
		int result = staffPrefectureMasterMapper.insert(record);
		return result;
	} 
	
	public PrefectureResponseBean findResponseBeanByPreId(Long preId) {
		Map<String,Object> param = new HashMap<>();
		param.put("preId", preId);
		return staffPrefectureClusterMapper.findResponseBeanByPreId(param);
	}

	@Override
	public String assign(AssignStallRequestBean bean, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignDel(AssignStallRequestBean bean, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PrefectureResponseBean offline(StallOperateRequestBean bean, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrefectureResponseBean online(StallOperateRequestBean bean, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void suspend(OrderOperateRequestBean oorb, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(OrderOperateRequestBean oorb, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
}
