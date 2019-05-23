package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;

import cn.linkmore.bean.common.Constants;
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
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.enterprise.response.ResEntExcStallStatus;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.enterprise.response.ResFixedPlate;
import cn.linkmore.notice.client.EntSocketClient;
import cn.linkmore.notice.client.UserSocketClient;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.client.FeignUnusualOrderClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResUnusualOrder;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
import cn.linkmore.prefecture.client.EntStaffClient;
import cn.linkmore.prefecture.client.FeignEnterpriseClient;
import cn.linkmore.prefecture.client.FeignStallExcStatusClient;
import cn.linkmore.prefecture.client.FixedPlateClient;
import cn.linkmore.prefecture.client.OpsRentUserClient;
import cn.linkmore.prefecture.config.LockTools;
import cn.linkmore.prefecture.controller.staff.request.ReqAssignStall;
import cn.linkmore.prefecture.controller.staff.request.ReqLockIntall;
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResEntTypeStalls;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.controller.staff.response.ResStaffNewAuth;
import cn.linkmore.prefecture.controller.staff.response.ResStaffNewAuthPre;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallDetail;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallSn;
import cn.linkmore.prefecture.core.lock.LockFactory;
import cn.linkmore.prefecture.core.lock.LockService;
import cn.linkmore.prefecture.dao.cluster.AdminAuthCityClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthPreClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthStallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminUserAuthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.EntRentRecordClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallLockClusterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthCityMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthPreMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthStallMasterMapper;
import cn.linkmore.prefecture.dao.master.EntRentRecordMasterMapper;
import cn.linkmore.prefecture.dao.master.StallLockMasterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.AdminAuthCity;
import cn.linkmore.prefecture.entity.AdminAuthPre;
import cn.linkmore.prefecture.entity.AdminAuthStall;
import cn.linkmore.prefecture.entity.EntRentRecord;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.entity.StallAssign;
import cn.linkmore.prefecture.entity.StallLock;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResAdminAuthPre;
import cn.linkmore.prefecture.response.ResAdminAuthStall;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.prefecture.response.ResAdminUserAuth;
import cn.linkmore.prefecture.response.ResLockGatewayList;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockMessage;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStaffCity;
import cn.linkmore.prefecture.response.ResStaffPreDetails;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallAssign;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOperateLog;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.prefecture.service.AdminUserService;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallAssignService;
import cn.linkmore.prefecture.service.StallOperateLogService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.task.TaskPool;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.StaffUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

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
	private AdminAuthStallMasterMapper AdminAuthStallMasterMapper;
	@Autowired
	private AdminAuthPreMasterMapper adminAuthPreMasterMapper;
	@Autowired
	private OpsRentUserClient opsRentUserClient;
	@Autowired
	private AdminAuthCityMasterMapper adminAuthCityMasterMapper;
	@Autowired
	private LockTools lockTools;
	@Autowired
	private RedisLock redisLock;
	@Autowired
	private StallAssignService assignService;
	@Autowired
	private StallMasterMapper stallMasterMapper;
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private OrderClient orderClient;
	@Autowired
	private EntOrderClient entOrderClient;
	@Autowired
	private AdminAuthCityClusterMapper adminAuthCityClusterMapper;
	@Autowired
	private StallLockMasterMapper stallLockMasterMapper;
	@Autowired
	private StallLockClusterMapper stallLockClusterMapper;
	@Autowired
	private PushClient pushClient;
	@Autowired
	private AdminUserAuthClusterMapper adminUserAuthClusterMapper;
	@Autowired
	private SendClient sendClient;
	@Autowired
	private EntRentedRecordClient entRentedRecordClient;
	@Autowired
	private EntRentRecordMasterMapper entRentedRecordMasterMapper;
	@Autowired
	private EntRentRecordClusterMapper entRentedRecordClusterMapper;
	@Autowired
	private EntSocketClient entSocketClient;
	@Autowired
	private UserSocketClient userSocketClient;
	@Autowired
	private EntStaffClient entStaffClient;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private PrefectureService prefectureService;
	@Autowired
	private FeignEnterpriseClient enterpriseClient;
	@Autowired
	private AdminAuthStallClusterMapper adminAuthStallClusterMapper;
	@Autowired
	private FeignUnusualOrderClient feignUnusualOrderClient;
	@Autowired
	private AdminAuthPreClusterMapper adminAuthPreClusterMapper;
	@Autowired
	private CityClient cityClient;
	@Autowired
	private FeignStallExcStatusClient feignStallExcStatusClient;
	@Autowired
	private BaseDictClient baseDictClient;
	@Autowired
	private FixedPlateClient fixedPlateClient;
	private ConcurrentHashMap<Long, String> osMap = new ConcurrentHashMap<>();
	@Autowired
	private StallOperateLogService stallOperateLogService;
	private static final String DOWN_CAUSE = "cause_down";
	private static final String BATTERY = "battery-change";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private UserFactory appUserFactory = AppUserFactory.getInstance();
	private UserFactory staffUserFactory = StaffUserFactory.getInstance();
	private LockFactory lockFactory = LockFactory.getInstance();
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
			stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
			stallMasterMapper.cancel(stall);
			redisService.remove(RedisKey.PREFECTURE_BUSY_STALL.key + stall.getLockSn());
			redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
			flag = true;
		}
		return flag;
	}

	class StallUpThread extends Thread {
		private Stall stall;

		public StallUpThread(Stall stall) {
			this.stall = stall;
		}

		public void run() {
			ResLockMessage res = lockTools.upLockMes(stall.getLockSn());
			int code = res.getCode();
			log.info("checkout.....................lock msg:{}", JsonUtil.toJson(res));
			if (code == 200) {
				redisService.remove(RedisKey.PREFECTURE_BUSY_STALL.key + stall.getLockSn());
				redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
			}
		}
	}

	@Override
	public boolean checkout(Long stallId) {
		log.info("checkout..................... stallId :{}", stallId);
		boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			flag = true;
			stall.setUpdateTime(new Date());
			stall.setStatus(StallStatus.FREE.status);
			stall.setLockStatus(LockStatus.UP.status);
			stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
			this.stallMasterMapper.checkout(stall);
			new StallUpThread(stall).start();
			log.info("checkout..................... stall-up-thread start");
		}
		return flag;
	}

	private void downing(ReqOrderStall reqos) {
		Stall stall = stallClusterMapper.findById(reqos.getStallId());
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("downing.....................stall:{},lockSn:{}", JsonUtil.toJson(stall), stall.getLockSn());
			long startTime = System.currentTimeMillis();
			ResLockMessage res = lockTools.downLockMes(stall.getLockSn());
			long endTime = System.currentTimeMillis();
			log.info("downing.....................{}降锁成功时间:{}秒", stall.getStallName(), (endTime - startTime) / 1000);
			int code = res.getCode();
			if (code == 200) {
				log.info("downing.....................success");
				stall.setLockStatus(LockStatus.DOWN.status);
				stallMasterMapper.lockdown(stall);
				this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key + reqos.getOrderId());
			}
			orderClient.downMsgPush(reqos.getOrderId(), reqos.getStallId());
		}
	}

	class DownThread extends Thread {
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
			log.info("uping.....................name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResLockMessage res = lockTools.upLockMes(stall.getLockSn());
			log.info("uping.....................res:{}", JsonUtil.toJson(res));
			int code = res.getCode();
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
	@Transactional()
	public void install(ReqLockIntall reqLockIntall,HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));

		ResAdminUser adminUser = adminUserService.find(cu.getId());
		Date now = new Date();
	    StallLock stallLock = new StallLock();
	    Stall stall = new Stall();
	    stallLock =	stallLockClusterMapper.findBySn(reqLockIntall.getLockSn());
	    stall = stallClusterMapper.findByLockSn(reqLockIntall.getLockSn());
//		Stall stallName = stallClusterMapper.findByLockName(reqLockIntall.getStallName());
		Stall stallName = stallClusterMapper.findByLockNameAndPreId(reqLockIntall.getStallName(),reqLockIntall.getPreId());
		if(stall != null) {
			if(!checkStaffStallAuth(cu.getId(), stall.getId())) {
				throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
			}
			if(stall.getStatus() != 4) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_UNOFFLINE);
			}
		}
		if(stallName != null) {
			if(!checkStaffStallAuth(cu.getId(), stallName.getId())) {
				throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
			}
			if(stallName.getStatus() != 4) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_UNOFFLINE);
			}
			//	判断绑定的车位还是为原车位时操作
			if(stallLock != null && stallLock.getStallId() != null && stallLock.getStallId().equals(stallName.getId()) ) {
				throw new BusinessException(StatusEnum.STAFF_STALL_BIN_EXISTS);
			}
		}
		
		//	判断如果是两个车位之间的互换操作
		if(stallLock != null && stallName != null && stallLock.getStallId() != null && stallName.getLockId() != null && stallName.getId() != stallLock.getStallId()) {
			// 更新根据车位名称查询的车位编号为新安装的车位编号
			stallName.setLockSn(reqLockIntall.getLockSn());
			// 更新原来车位编号的车位将车位编号设置为null
			StallLock lock = this.stallLockClusterMapper.findByStallId(stall.getId());
			lock.setSn(null);
			lock.setStallId(null);
			lock.setPrefectureId(null);
			this.stallLockMasterMapper.update(lock);
			this.stallLockMasterMapper.updateBind(lock);
			stall.setLockSn(null);
			stall.setLockId(null);
			this.stallMasterMapper.update(stall);
			StallLock oStallLock = this.stallLockClusterMapper.findByStallId(stallName.getId());
			// 更新更改后的车位锁关系
			oStallLock.setSn(reqLockIntall.getLockSn());
//			oStallLock.setStallId(stallName.getId());
			oStallLock.setPrefectureId(stallName.getPreId());
			this.stallLockMasterMapper.update(oStallLock);
//			stallName.setLockId(stallLock.getId());
			this.stallMasterMapper.update(stallName);
		}else if(stallLock != null|| stallName != null ) {
			if(stallLock != null) {
				if(stallName != null) {
					stallLock.setStallId(stallName.getId());
					stallLock.setPrefectureId(stallName.getPreId());
					stallLock.setBindTime(new Date());
					this.stallLockMasterMapper.updateBind(stallLock);
					stallName.setLockId(stallLock.getId());
					stallName.setLockSn(stallLock.getSn());
					this.stallMasterMapper.update(stallName);
					if(stall != null) {
						stall.setLockSn(null);
						stall.setLockId(null);
						this.stallMasterMapper.update(stall);
//						this.stallMasterMapper.delete(stall.getId());
					}
				}else {
					if(stall != null) {
						stall.setLockSn(null);
						stall.setLockId(null);
						this.stallMasterMapper.update(stall);
//						this.stallMasterMapper.delete(stall.getId());
					}
					// 插入新车位并绑定
					stallName = new Stall();
					stallName.setStallName(reqLockIntall.getStallName());
					stallName.setSellCount(0);
					stallName.setPreId(reqLockIntall.getPreId());
					stallName.setGatewayId(0l);
					stallName.setBindOrderStatus((short) 0);
					stallName.setStatus(4);
					stallName.setLockBattery(0);
					stallName.setCreateTime(now);
					stallName.setUpdateTime(now);
					stallName.setLockSn(reqLockIntall.getLockSn());
					stallName.setLockId(stallLock.getId());
					stallName.setLockStatus(0);
					stallName.setLockBattery(0);
					stallName.setType((short)0);
					stallName.setAreaName(reqLockIntall.getAreaName());
					stallName.setStallLocal(reqLockIntall.getStallName());
					// 插入车位
					this.stallMasterMapper.save(stallName);
					Map<String, Object> param = new HashMap<>();
					param.put("userId", cu.getId());
					List<ResAdminUserAuth> userAuth = this.adminUserAuthClusterMapper.findList(param );
					if(userAuth == null || userAuth.size() == 0) {
						throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
					}
					param.put("stallId", stallName.getId());
					List<ResAdminAuthStall> list = this.adminAuthStallClusterMapper.findStallList(param);
					if(list == null || list.size() == 0) {
						AdminAuthStall record = new AdminAuthStall();
						record.setAuthId(userAuth.get(0).getAuthId());
						record.setStallId(stallName.getId());
						this.AdminAuthStallMasterMapper.save(record );
					}
					stallLock.setStallId(stallName.getId());
					stallLock.setPrefectureId(stallName.getPreId());
					stallLock.setBindTime(new Date());
					this.stallLockMasterMapper.updateBind(stallLock);
				}
			}else {
				StallLock lock = this.stallLockClusterMapper.findByStallId(stallName.getId());
				if(lock != null) {
					lock.setSn(null);
					lock.setStallId(null);
					lock.setPrefectureId(null);
					this.stallLockMasterMapper.update(lock);
					this.stallLockMasterMapper.updateBind(lock);
				}
//				this.stallLockMasterMapper.deleteByStallId(stallName.getId());
				stallLock = new StallLock();
				stallLock.setCreateTime(now);
				stallLock.setSn(reqLockIntall.getLockSn());
				stallLock.setCreateUserName(adminUser.getRealname());
				stallLock.setCreateUserId(adminUser.getId());
				stallLock.setStallId(stallName.getId());
				stallLock.setPrefectureId(stallName.getPreId());
				stallLock.setBindTime(new Date());
				stallLockMasterMapper.save(stallLock);
				stallLockMasterMapper.updateBind(stallLock);
				stallName.setLockSn(reqLockIntall.getLockSn());
				stallName.setLockId(stallLock.getId());
				this.stallMasterMapper.update(stallName);
			}
		//	安装数else {
	    //验证
//		if(stallLock!=null|| stall!=null ) {
//			throw new BusinessException(StatusEnum.LOCK_SN_AlREADY_BAND);
//		}
//		if(stallName!= null) {
//			throw new BusinessException(StatusEnum.STALL_NAME_USER);
//		}
	}else {
		 stallLock = new StallLock();
	     stall = new Stall();
		// 插入锁
		stallLock.setCreateTime(now);
		stallLock.setSn(reqLockIntall.getLockSn());
		stallLock.setCreateUserName(adminUser.getRealname());
		stallLock.setCreateUserId(adminUser.getId());
		stallLockMasterMapper.save(stallLock);
//		stallLock = stallLockClusterMapper.findBySn(reqLockIntall.getLockSn());

		// 插入新车位并绑定
		stall.setStallName(reqLockIntall.getStallName());
		stall.setSellCount(0);
		stall.setPreId(reqLockIntall.getPreId());
		stall.setGatewayId(0l);
		stall.setBindOrderStatus((short) 0);
		stall.setStatus(4);
		stall.setLockBattery(0);
		stall.setCreateTime(now);
		stall.setUpdateTime(now);
		stall.setLockSn(reqLockIntall.getLockSn());
		stall.setLockId(stallLock.getId());
		stall.setLockStatus(0);
		stall.setLockBattery(0);
		stall.setType((short)0);
		stall.setAreaName(reqLockIntall.getAreaName());
		stall.setStallLocal(reqLockIntall.getStallName());
		
		// 插入车位
		this.stallMasterMapper.save(stall);
//		stall = stallClusterMapper.findByLockSn(reqLockIntall.getLockSn());
//		ResLockInfo info = this.lockTools.lockInfo(reqLockIntall.getLockSn());
//		if(info != null) {
//			stallLock.setBattery(info.getElectricity());
//			stallLock.setModel(info.getModel());
//			stallLock.setVersion(info.getVersion());
//		}
		//更新锁
		stallLock.setBindTime(now);
		stallLock.setStallId(stall.getId());
		stallLock.setPrefectureId(reqLockIntall.getPreId());
		stallLockMasterMapper.updateBind(stallLock);
		
		//更新用户权限
		Map<String, Object> param = new HashMap<>();
		param.put("userId", cu.getId());
		List<ResAdminUserAuth> userAuth = this.adminUserAuthClusterMapper.findList(param );
		if(userAuth == null || userAuth.size() == 0) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		param.put("stallId", stall.getId());
		List<ResAdminAuthStall> list = this.adminAuthStallClusterMapper.findStallList(param);
		if(list == null || list.size() == 0) {
			AdminAuthStall record = new AdminAuthStall();
			record.setAuthId(userAuth.get(0).getAuthId());
			record.setStallId(stall.getId());
			this.AdminAuthStallMasterMapper.save(record );
		}
		stallLock.setPrefectureId(reqLockIntall.getPreId());
		stallLockMasterMapper.updateBind(stallLock);
	}
		//通知锁平台
		Map<String, Object> map = new TreeMap<>();
		map.put("serialNumber", reqLockIntall.getLockSn());
		map.put("name", reqLockIntall.getStallName());
		lockTools.setLockName(map);
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
	public int unBind(List<Long> ids) {
		stallLockMasterMapper.unBind(ids);
		return stallMasterMapper.unBind(ids);
	}
	
	@Override
	public int updateStatus(ReqStall reqStall) {
		Date now = new Date();
		Integer status = reqStall.getStatus();
		String sn = reqStall.getLockSn();
		// Long preId = reqStall.getPreId();
		reqStall.setUpdateTime(now);
		if (status.intValue() == 4) {
			Stall stall = new Stall();
			stall = ObjectUtils.copyObject(reqStall, stall);
			stallMasterMapper.update(stall);
			log.info("下线成功，车位：" + stall.getStallName() + " 序列号：{}", sn);
		} else {
			try {
				ResLockInfo lockBean = lockTools.lockInfo(sn);
				if (lockBean == null) {
					throw new RuntimeException("车位锁通讯失败");
				}
				/*
				 * if (lockBean.getOpenState() != 1) { lockFactory.lockUp(sn); } int openState =
				 * lockBean.getOpenState(); reqStall.setLockStatus(openState);
				 * reqStall.setLockBattery((int) lockBean.getVoltage());
				 */

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
	public void saveAndBind(ReqStall reqStall) {
		Date now = new Date();
		StallLock lock = new StallLock();
		lock.setSn(reqStall.getLockSn());
		lock.setCreateTime(now);
		lock.setCreateEntId(reqStall.getCreateEntId());
		lock.setCreateEntName(reqStall.getCreateEntName());
		lock.setCreateUserId(reqStall.getCreateUserId());
		lock.setCreateUserName(reqStall.getCreateUserName());
		this.stallLockMasterMapper.save(lock);
		ReqStall stall = new ReqStall();
		stall.setStallName(reqStall.getStallName());
		stall.setLockStatus(null);
		stall.setLockSn(reqStall.getLockSn());
		stall.setStatus(4);
		stall.setLockBattery(0);
		stall.setLockId(lock.getId());
		stall.setPreId(reqStall.getPreId());
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
			Integer count = (Integer) this.redisService.get(RedisKey.STALL_ORDER_CLOSED.key + id);
			if (count == null) {
				count = 0;
			}
			if (count < 3) {
				count = count + 1;
				stall.setStatus(StallStatus.FREE.status);
				stall.setLockStatus(LockStatus.UP.status);
				stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
				stall.setUpdateTime(new Date());
				this.stallMasterMapper.checkout(stall);
				this.redisService.set(RedisKey.STALL_ORDER_CLOSED.key + id, count,
						ExpiredTime.STALL_ORDER_CLOSED_TIME.time);
				// this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(),
				// stall.getLockSn());
			} else {
				this.redisService.remove(RedisKey.STALL_ORDER_CLOSED.key + id);
				stall.setStatus(StallStatus.OUTLINE.status);
				stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
				this.redisService.remove(RedisKey.STALL_ORDER_CLOSED.key + id);
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
		log.info("param = {}", param);
		String brand = (String) param.get("brand");
		List<Long> ids = (List<Long>) param.get("list");
		if ("0".equals(brand)) {
			return this.stallMasterMapper.updateBrand(ids);
		} else {
			return this.stallMasterMapper.updateBrandStall(ids);
		}
	}

	@Override
	public Map<String, Object> lockStatus(List<String> parkcodes) {
		Map<String, Object> map = new HashMap<>();
		List<ResLockInfo> infos = new ArrayList<>();
		for (String string : parkcodes) {
			ResLockInfo resLockInfo = this.lockTools.lockInfo(string);
			infos.add(resLockInfo);
		}
		if (infos.size() == 0) {
			map.put("data", infos);
		}
		return map;
	}

	@Override
	public void controling(ReqControlLock reqc) { // 控制锁
		new Thread(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				Stall stall = stallClusterMapper.findById(reqc.getStallId());
				log.info("stall:{}", JsonUtil.toJson(stall));
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("<<<<<<<<<controling>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
					ResLockMessage res = null;
					// 1 降下 2 升起
					Stopwatch stopwatch = Stopwatch.createStarted();
					if (reqc.getStatus() == 1) {
						res = lockTools.downLockMes(stall.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockTools.upLockMes(stall.getLockSn());
					}
					log.info("<<<<<<<<<respose>>>>>>>>>" + res.getMessage() + "<<<code>>>" + res.getCode());
					int code = res.getCode();
					stopwatch.stop();
					log.info("<<<<<<<<<using time>>>>>>>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.SECONDS)));
					osMap.put(Long.decode(uid), reqc.getOs());
					sendMsg(uid, reqc.getStatus(), code);
					String robkey = RedisKey.ROB_STALL_ISHAVE.key + reqc.getStallId();
					EntRentRecord record = entRentedRecordClusterMapper.findByUser(Long.valueOf(uid));
					if (code == 200) {
						redisService.remove(reqc.getKey());
						if (reqc.getStatus() == 2) {
							log.info("<<<<<<<<<up success>>>>>>>>>");
							// 未完成记录同一用户只有一单
							if (Objects.nonNull(record)) {
								EntRentRecord up = new EntRentRecord();
								up.setLeaveTime(new Date());
								up.setStatus(1L);
								up.setId(record.getId());
								entRentedRecordMasterMapper.updateByIdSelective(up);
							}
						} else {
							log.info("<<<<<<<<<down success>>>>>>>>>");
						}
						stall.setLockStatus(reqc.getStatus() == 1 ? 2 : 1);
						stall.setStatus(reqc.getStatus() == 1 ? 2 : 1);
						stallMasterMapper.lockdown(stall);
					} else {
						if (reqc.getStatus() == 1) {
							log.info("<<<<<<<<<down fail>>>>>>>>>>");
							// 降锁失败 取消绑定
							if (Objects.nonNull(record)) {
								EntRentRecord up = new EntRentRecord();
								up.setLeaveTime(new Date());
								up.setStatus(2L);
								up.setId(record.getId());
								entRentedRecordMasterMapper.updateByIdSelective(up);
							}
						} else {
							log.info("<<<<<<<<<up fail>>>>>>>>>>");
						}
					}
					redisService.remove(robkey);
				}
			}
		}).start();
	}

	@Override
	public void operLockWY(ReqControlLock reqc) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				Stall stall = stallClusterMapper.findById(reqc.getStallId());
				log.info("stall:{}", JsonUtil.toJson(stall));
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("downing>>>>>> name:{},sn:{}", stall.getStallName(), stall.getLockSn());
					ResLockMessage res = null;
					// 1 降下 2 升起
					Stopwatch stopwatch = Stopwatch.createStarted();
					if (reqc.getStatus() == 1) {
						res = lockTools.downLockMes(stall.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockTools.upLockMes(stall.getLockSn());
					}
					log.info("res>>>>>>>" + res.getMessage());
					int code = res.getCode();
					stopwatch.stop();
					log.info("usingtime>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
					sendWYMsg(uid, reqc.getStatus(), code);
					if (code == 200) {
						if (reqc.getStatus() == 1) {
							stall.setLockStatus(2);
							if (stall.getType() == 2) {
								if (stall.getStatus() != 4) {
									stall.setStatus(2);
								}
							}
						} else if (reqc.getStatus() == 2) {
							if (stall.getType() == 2) {
								if (stall.getStatus() != 4) {
									stall.setStatus(1);
								}
							}
							stall.setLockStatus(1);
						}
						stallMasterMapper.lockdown(stall);
						if (reqc.getStatus() == 1) {
//							downLock(reqc.getStallId(), 1,reqc.getType());
							redisService.remove(reqc.getKey());
						}

					} else {
						if (reqc.getStatus() == 1) {
//							downLock(reqc.getStallId(), 0,reqc.getType());
						}
					}
				}
			}
		}).start();
	}

	@Override
	public Map<String, Object> watch2(Long stallId) {
		log.info("stall:=====================");
		Map<String, Object> map = new HashMap<>();
		Stall stall = stallClusterMapper.findById(stallId);
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResLockInfo Lockbean = lockTools.lockInfo(stall.getLockSn());
			if (Lockbean!=null) {
				map.put("code", 200);
				map.put("status", Lockbean.getLockState());
//				map.put("onlineState", Lockbean.getOnlineState());
				map.put("parkingState", Lockbean.getParkingState());
			}
		}
		return map;
	}
	
	@Override
	public Map<String, Object> watch(Long stallId) {
		log.info("stall:=====================");
		Map<String, Object> map = new HashMap<>();
		Stall stall = stallClusterMapper.findById(stallId);
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResLockInfo res = lockTools.lockInfo(stall.getLockSn());
			if (res != null) {
				map.put("code", 200);
				map.put("status", res.getLockState());
//				map.put("onlineState", res.getOnlineState());
				map.put("parkingState", res.getParkingState());
			}else {
				map.put("code", 400);
			}
		}
		return map;
	}

	public void sendMsg(String uid, Integer status, int code) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					send(uid, status, code);
				} catch (Exception e) {
					log.info("sendMsg>>>" + uid);
				}
			}
		}).start();
	}

	public void sendWYMsg(String uid, Integer status, int code) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendWY(uid, status, code);
				} catch (Exception e) {
					log.info("sendWYMsg>>>" + uid);
				}
			}
		}).start();
	}

	public void downLock(Long stallId, int status,int type) {

		switch (type) {
		case 0:
			ResUserOrder latest = this.entOrderClient.findStallLatest(stallId);
			if(latest != null) {
				Map<String,Object> param = new HashMap<>();
				param.put( "lockDownStatus",status);
				param.put("lockDownTime", new Date());
				param.put("orderId", latest.getId());
			}
			break;
		case 2:
			this.entRentedRecordClient.updateDownTime(stallId);
			break;
		}
	}

	private void send(String uid, Integer lockstatus, int code) {
		String title = "车位锁操作通知";
		String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
		PushType type = PushType.LOCK_CONTROL_NOTICE;
		String bool = (code == 200 ? "true" : "false");
		Token token = (Token) redisService.get(appUserFactory.createUserIdRedisKey(Long.decode(uid), osMap.get(uid)));
		log.info("send>>>" + JsonUtil.toJson(token));
		if (token != null) {
			if (token.getClient().intValue() == ClientSource.WXAPP.source) {
				log.info("=============================socket start===========================");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", title);
				map.put("type", type);
				map.put("content", content);
				map.put("code", bool);
				CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(token.getAccessToken(),null) );
				//userSocketClient.push(content, cu.getOpenId());
				log.info("openid>>>" + cu.getOpenId());
				System.out.println(JsonUtil.toJson(map));
			} else {
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setTitle(title);
				rp.setContent(content);
				rp.setClient(token.getClient());
				rp.setType(type);
				rp.setData(bool);
				pushClient.push(rp);
			}
		}
	}

	private void sendWY(String uid, Integer lockstatus, int code) {
		String title = "车位锁操作通知";
		String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
		String bool = (code == 200 ? "true" : "false");
		Token token = (Token) redisService.get(RedisKey.STAFF_ENT_AUTH_TOKEN.key + uid.toString());
		log.info("send>>>", JsonUtil.toJson(token));
		if (token != null) {
			if (token.getClient() == Constants.ClientSource.WXAPP.source) {
				CacheUser cu = (CacheUser) redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", title);
				map.put("type", PushType.LOCK_CONTROL_NOTICE);
				map.put("content", content);
				map.put("data", token.getAccessToken());
				map.put("alias", cu.getId());
				ResEntStaff staff = this.entStaffClient.findById(cu.getId());
				entSocketClient.push(JsonUtil.toJson(map), staff.getOpenId());
			} else {
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setTitle(title);
				rp.setContent(content);
				rp.setClient(token.getClient());
				rp.setType(PushType.LOCK_CONTROL_NOTICE);
				rp.setData(token.getAccessToken());
				sendClient.send(rp);
			}
		}
	}

	@Override
	public List<ResStaffPreList> findPreList(HttpServletRequest request, Long cityId) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (!checkStaffCityAuth(cu.getId(), cityId)) {
			throw new BusinessException(StatusEnum.STAFF_CITY_EXISTS);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<AdminAuthPre> pres = this.adminAuthPreClusterMapper.findList(map);
		List<Long> list = pres.stream().map(pre -> pre.getPreId()).collect(Collectors.toList());
		map.put("preIds", list);
		map.put("cityId", cityId);
//		map.put("categorys", Arrays.asList(0, 1));
		List<ResPre> pre = this.prefectureService.findPreByIds(map);
		List<ResStaffPreList> resPres = new ArrayList<>();
		if (pre == null || pre.size() == 0) {
			return resPres;
		}
		List<ResAdminAuthStall> adminStalls = this.adminAuthStallClusterMapper.findStallList(map);
		List<Long> collect = adminStalls.stream().map(au -> au.getStallId()).collect(Collectors.toList());
		List<Stall> stalls = this.stallClusterMapper.findAll();
		map = new HashMap<>();
		List<ResUnusualOrder> unusualOrders = feignUnusualOrderClient.findList(map);
		ResStaffPreList preList = null;
		ResEntTypeStalls entType = null;
		for (ResPre resPre : pre) {
			preList = new ResStaffPreList();
			int preTypeStalls = 0;
			int preUseTypeStalls = 0;
			int preLeisureTypeStalls = 0;
			int preFaultTypeStalls = 0;
			int orderNum = 0;
			
			int preTempUseTypeStalls = 0;
			int preTempTypeStalls = 0;
			
			int preRentUseTypeStalls = 0;
			int preRentTypeStalls = 0;
			
			int preVipUseTypeStalls = 0;
			int preVipTypeStalls = 0;
			
			
			for (Stall stall : stalls) {
				/*if (stall.getType() != 0) {
					continue;
				}
				*/
				if(!stall.getPreId().equals(resPre.getId())) {
					continue;
				}
				if (collect.contains(stall.getId())) {
					preTypeStalls++;
					switch (stall.getStatus() ) {
					case 2:
						preUseTypeStalls++;
						break;
					case 1:
						preLeisureTypeStalls++;
						break;
					case 4:
						preFaultTypeStalls++;
						break;
					}
					if(stall.getType() == 0) {
						preTempTypeStalls++;
						preTempUseTypeStalls += stall.getStatus() == 2 ? 1 : 0;
					}else if (stall.getType() == 2) {
						preRentTypeStalls++;
						preRentUseTypeStalls += stall.getStatus() == 2 ? 1 : 0; 
						
					}else if(stall.getType() == 3) {
						preVipTypeStalls++;
						preVipUseTypeStalls += stall.getStatus() == 2 ? 1 : 0;
					}
				}
			}
			if (unusualOrders != null) {
				for (ResUnusualOrder resUnusualOrder : unusualOrders) {
					if (resUnusualOrder.getPrefectureId().equals(resPre.getId())) {
						orderNum++;
					}
				}
			}
			preList.setPreLeisureTypeStalls(preLeisureTypeStalls);
			preList.setPreFaultTypeStalls(preFaultTypeStalls);
			preList.setPreId(resPre.getId());
			preList.setPreName(resPre.getName());
			preList.setUnusualOrder(orderNum);
			preList.setPreTypeStalls(preTypeStalls);
			preList.setPreUseTypeStalls(preUseTypeStalls);
			//	临停
			entType = new ResEntTypeStalls();
			entType.setType((short)0);
			entType.setTypeName("临停");
			entType.setPreTypeStalls(preTempTypeStalls);
			entType.setPreUseTypeStalls(preTempUseTypeStalls);
			preList.getTypeStalls().put("temp", entType);
			// 固定
			entType = new ResEntTypeStalls();
			entType.setTypeName("固定");
			entType.setType((short)2);
			entType.setPreTypeStalls(preRentTypeStalls);
			entType.setPreUseTypeStalls(preRentUseTypeStalls);
			preList.getTypeStalls().put("rent", entType);
			//vip
			entType = new ResEntTypeStalls();
			entType.setTypeName("VIP");
			entType.setType((short)3);
			entType.setPreTypeStalls(preVipTypeStalls);
			entType.setPreUseTypeStalls(preVipUseTypeStalls);
			preList.getTypeStalls().put("vip", entType);
			resPres.add(preList);
		}
		return resPres;
	}

	@Override
	public List<ResStaffStallList> findStallList(HttpServletRequest request, ReqStaffStallList staffList) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (!checkStaffPreAuth(cu.getId(), staffList.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<ResAdminAuthStall> stallAuthList = this.adminAuthStallClusterMapper.findStallList(map);
		map.put("preId", staffList.getPreId());
		map.put("type", staffList.getType());
		if (StringUtils.isNotBlank(staffList.getStallName())) {
			map.put("stallNameLike", "%" + staffList.getStallName() + "%");
		}
		// 预留功能根据状态查询
		if (staffList.getStatus() != null) {
			int status = 0;
			switch (staffList.getStatus()) {
			case 0:
				status = 1;
				break;
			case 1:
				status = 2;
				break;
			case 2: 
				status = 4;
				break;
			default:
				throw new BusinessException(StatusEnum.STALL_UNKNOW_TYPE);
			}
			map.put("status", status);
		}
		List<ResStall> stallList = this.stallClusterMapper.findPreStallList(map);
		log.info("【 ResStall list 】 " + JsonUtil.toJson(stallList));
		List<ResStaffStallList> staffStallLists = new ArrayList<>();
		List<Long> stallAuthIds = stallAuthList.stream().map(stall -> stall.getStallId()).collect(Collectors.toList());
		ResPrefectureDetail detail = this.prefectureService.findById(staffList.getPreId());
		log.info("【 ResPrefectureDetail 】 " + JsonUtil.toJson(detail));
		List<ResLockInfo> bockBeans = lockTools.lockListByGroupCode(detail.getGateway());
		log.info("【lockBean list 】 " + JsonUtil.toJson(bockBeans));
		List<ResEntExcStallStatus> excStallList = feignStallExcStatusClient.findAll();
//			staffStallLists = getStaffTempStall(stallList,staffList.getPreId(),bockBeans, stallAuthIds,excStallList);
		List<ResOrderPlate> plates = this.entOrderClient.findPlateByPreId(staffList.getPreId());
		ResStaffStallList resStaffStallList = null;
		log.info("【 ResOrderPlate 】 " + JsonUtil.toJson(plates));
		for (ResStall resStall : stallList) {
			if (!stallAuthIds.contains(resStall.getId())) {
				continue;
			}
			resStaffStallList = new ResStaffStallList();
			if(resStall.getType() == 0) {
				if (resStall.getStatus() == 2) {
					for (ResOrderPlate resOrderPlate : plates) {
						if (resOrderPlate.getStallId().equals(resStall.getId())) {
							resStaffStallList.setPlateNo(resOrderPlate.getPlateNo());
						}
					}
				} else if (resStall.getStatus() == 1) {
					// 指定车位锁
					int assignStatus = 1;
					String lockSn = resStall.getLockSn();
					Set<Object> set = redisService.members(Constants.RedisKey.ORDER_ASSIGN_STALL.key);
					log.info("指定锁池个数: {}", set.size());
					log.info("指定锁池: {}", set.toString());
					for (Object obj : set) {
						JSONObject json = JSON.parseObject(obj.toString());
						String sn = json.get("lockSn").toString();
						Long pid = Long.parseLong(json.get("preId").toString());
						if (pid.longValue() == staffList.getPreId().longValue() && lockSn.equals(sn)) {
							assignStatus = 0;
							resStaffStallList.setPlateNo(json.get("plate").toString());
							break;
						}
					}
					resStaffStallList.setAssignStatus(assignStatus);
				}
			}else if(resStall.getType() == 2) {
			}
			resStaffStallList = getStaffStall(bockBeans,resStaffStallList,resStall,excStallList);
			resStaffStallList.setType(staffList.getType());
			staffStallLists.add(resStaffStallList);
		}
		return staffStallLists;
	}
	/**
	 * @param excStallList 
	 * @param stallAuthIds 
	 * @param preId 
	 * @Description  查询管理版固定车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	private List<ResStaffStallList> getStaffRentStall(List<ResStall> stallList, Long preId, List<ResLockInfo> bockBeans, List<Long> stallAuthIds, List<ResEntExcStallStatus> excStallList) {
		List<ResStaffStallList> staffStallLists = new ArrayList<>();
		ResStaffStallList resStaffStallList = null;
		for (ResStall resStall : stallList) {
			if (!stallAuthIds.contains(resStall.getId())) {
				continue;
			}
			resStaffStallList = new ResStaffStallList();
			resStaffStallList = getStaffStall(bockBeans, resStaffStallList, resStall, excStallList);
			staffStallLists.add(resStaffStallList);
		}
		return staffStallLists;
	}

	/**
	 * @param bockBeans 
	 * @param stallAuthIds 
	 * @param excStallList 
	 * @Description  查询管理版临停车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	private List<ResStaffStallList> getStaffTempStall(List<ResStall> stallList, Long preId, List<ResLockInfo> bockBeans, List<Long> stallAuthIds, List<ResEntExcStallStatus> excStallList) {
		List<ResStaffStallList> staffStallLists = new ArrayList<>();
		List<ResOrderPlate> plates = this.entOrderClient.findPlateByPreId(preId);
		ResStaffStallList resStaffStallList = null;
		log.info("【 ResOrderPlate 】 " + JsonUtil.toJson(plates));
		for (ResStall resStall : stallList) {
			if (!stallAuthIds.contains(resStall.getId())) {
				continue;
			}
			resStaffStallList = new ResStaffStallList();
			if (resStall.getStatus() == 2) {
				for (ResOrderPlate resOrderPlate : plates) {
					if (resOrderPlate.getStallId().equals(resStall.getId())) {
						resStaffStallList.setPlateNo(resOrderPlate.getPlateNo());
					}
				}
			} else if (resStall.getStatus() == 1) {
				// 指定车位锁
				int assignStatus = 1;
				String lockSn = resStall.getLockSn();
				Set<Object> set = redisService.members(Constants.RedisKey.ORDER_ASSIGN_STALL.key);
				log.info("指定锁池个数: {}", set.size());
				log.info("指定锁池: {}", set.toString());
				for (Object obj : set) {
					JSONObject json = JSON.parseObject(obj.toString());
					String sn = json.get("lockSn").toString();
					Long pid = Long.parseLong(json.get("preId").toString());
					if (pid.longValue() == preId.longValue() && lockSn.equals(sn)) {
						assignStatus = 0;
						resStaffStallList.setPlateNo(json.get("plate").toString());
						break;
					}
				}
				resStaffStallList.setAssignStatus(assignStatus);
			}
			resStaffStallList = getStaffStall(bockBeans,resStaffStallList,resStall,excStallList);
			staffStallLists.add(resStaffStallList);
		}
		return staffStallLists; 
	}

	private ResStaffStallList getStaffStall(List<ResLockInfo> bockBeans, ResStaffStallList resStaffStallList, ResStall resStall, List<ResEntExcStallStatus> excStallList) {
		resStaffStallList = getStaffStallExc(resStaffStallList,resStall,excStallList);
		resStaffStallList = getStaffLockStatus(bockBeans,resStaffStallList,resStall.getLockSn());
		resStaffStallList.setLockStatus(resStaffStallList.getLockStatus() != null ? resStaffStallList.getLockStatus() : resStall.getLockStatus());
		resStaffStallList.setStatus(resStall.getStatus());
		resStaffStallList.setStallId(resStall.getId());
		resStaffStallList.setStallName(resStall.getStallName());
		return resStaffStallList;
	} 
	
	private ResStaffStallList getStaffLockStatus(List<ResLockInfo> bockBeans, ResStaffStallList resStaffStallList ,String lockSn) {
		if (bockBeans != null && bockBeans.size() != 0) {
			for (ResLockInfo lockBean : bockBeans) {
				if (lockBean.getLockCode().equals(lockSn)) {
					if(lockBean.getElectricity() <= 30) {
						resStaffStallList.setExcStatus(false);
					} 
					switch (lockBean.getLockState()) {
					case 0:
						resStaffStallList.setLockStatus(2);
						break;
					case 2:
						resStaffStallList.setLockStatus(1);
						break;
					case 3:
						resStaffStallList.setLockStatus(2);
						break;
					case 1:
						resStaffStallList.setLockStatus(1);
						break;
					}
				}
			}
		}
		return resStaffStallList;
	}

	private ResStaffStallList getStaffStallExc(ResStaffStallList resStaffStallList, ResStall resStall, List<ResEntExcStallStatus> excStallList) {
		if(resStall != null) {
			if (resStall.getStatus() != 4) {
				for (ResEntExcStallStatus resEntExcStallStatus : excStallList) {
					if (resEntExcStallStatus.getStallId().equals(resStall.getId())) {
						resStaffStallList.setExcStatus(false);
					}
				}
			}
			if (resStall.getBindOrderStatus() != null && resStall.getBindOrderStatus() != 0) {
				resStaffStallList.setExcStatus(false);
			}
		}
		return resStaffStallList;
	}


	@Override
	public Boolean checkStaffCityAuth(Long userId, Long cityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("cityId", cityId);
		List<AdminAuthCity> list = this.adminAuthCityClusterMapper.findList(map);
		return list != null && list.size() != 0 ? true : false;
	}

	@Override
	public Boolean checkStaffPreAuth(Long userId, Long preId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("preId", preId);
		List<AdminAuthPre> list = this.adminAuthPreClusterMapper.findList(map);
		return list != null && list.size() != 0 ? true : false;
	}

	@Override
	public Boolean checkStaffStallAuth(Long userId, Long stallId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("stallId", stallId);
		List<ResAdminAuthStall> list = this.adminAuthStallClusterMapper.findStallList(map);
		return list != null && list.size() != 0 ? true : false;
	}
 
	/**
	 * 管理版锁操作
	 */
	/*@Override
	public void operating(ReqControlLock reqc) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				Stall stall = stallClusterMapper.findById(reqc.getStallId());
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("operating············name:{},··········sn:{},··········uid:{}", stall.getStallName(),
							stall.getLockSn(), uid);
					Boolean res = null;
					// 1 降下 2 升起
					if (reqc.getStatus() == 1) {
						res = lockTools.downLock(stall.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockTools.upLock(stall.getLockSn());
					}
					int code = res == false ? 500:200;
					log.info(" operating··············" + res + " code·············" + code);
					if (code == 200) {
						if(stall.getType() == 1) {
							redisService.remove(reqc.getKey());
							stall.setLockStatus(reqc.getStatus() == 1 ? 2 : 1);
							stallMasterMapper.lockdown(stall);
						}else if(stall.getType() == 2) {
							if(reqc.getStatus() == 1) {
								stall.setLockStatus(2);
//								if (stall.getStatus() != 4) {
//									stall.setStatus(2);
//								}
							} else if (reqc.getStatus() == 2) {
								if (stall.getStatus() != 4) {
									stall.setStatus(1);
								}
								stall.setLockStatus(1);
							}
						}
						stallMasterMapper.lockdown(stall);
						if (reqc.getStatus() == 1) {
							downLock(reqc.getStallId(), 1,reqc.getType());
						}

					} else {
						if (reqc.getStatus() == 1) {
							downLock(reqc.getStallId(), 0,reqc.getType());
						}
					}
					sendMsgT(uid, reqc.getStatus(), code);
				}
					//解锁
					redisLock.unlock1(reqc.getRobkey());
				}
		
			});
		
	}*/
	/**
	 * 管理版锁操作
	 */
	@Override
	public Boolean operating(ReqControlLock reqc) {
		boolean flag = false;
		String uid = String.valueOf(redisService.get(reqc.getKey()));
		Stall stall = stallClusterMapper.findById(reqc.getStallId());
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("operating············name:{},··········sn:{},··········uid:{}", stall.getStallName(),
					stall.getLockSn(), uid);
			// 争抢
			String robkey = RedisKey.ROB_STALL_ISHAVE.key + reqc.getStallId();
			/*
			Boolean have = true;
			try {
				have = this.redisLock.getLock(robkey, reqc.getUserId());
				log.info("用户=======>" + reqc.getUserId() + (have == true ? "已抢到" : "未抢到") + "锁" + robkey);
			} catch (Exception e) {
				log.info("用户争抢锁异常信息{}",e.getMessage());
			}
			if (!have) {
				throw new BusinessException(StatusEnum.APPOINT_FAIL_CHECK);
			}
			// 放入缓存
			String rediskey = RedisKey.ACTION_STALL_DOING.key + reqc.getStallId();
			this.redisService.set(rediskey, reqc.getUserId(), ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
			log.info("用户>>>" + reqc.getUserId() + "缓存>>>" + rediskey);
			log.info("用户>>>" + reqc.getUserId() + "调用>>>" + reqc.getStallId());
			log.info("<<<<<<<<<controling>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			
			// 1 降下
			Stopwatch stopwatch = Stopwatch.createStarted();*/
			ResLockMessage res = null;
			// 1 降下 2 升起
			if (reqc.getStatus() == 1) {
				res = lockTools.downLockMes(stall.getLockSn());
			} else if (reqc.getStatus() == 2) {
				res = lockTools.upLockMes(stall.getLockSn());
			}
			int code = res.getCode();
			log.info(" operating··············" + res + " code·············" + code);
//			stopwatch.stop();
			redisService.remove(robkey);
			if (code == 200) {
				flag = true;
				//去掉空闲车位
//				redisService.remove(rediskey);
				this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key + reqc.getStallId() + reqc.getUserId());
				if(stall.getType() == 1) {
					redisService.remove(reqc.getKey());
					stall.setLockStatus(reqc.getStatus() == 1 ? 2 : 1);
					stallMasterMapper.lockdown(stall);
				}else if(stall.getType() == 2) {
					if(reqc.getStatus() == 1) {
						stall.setLockStatus(2);
//								if (stall.getStatus() != 4) {
//									stall.setStatus(2);
//								}
					} else if (reqc.getStatus() == 2) {
						if (stall.getStatus() != 4) {
							stall.setStatus(1);
						}
						stall.setLockStatus(1);
					}
				}
				flag = true;
				stallMasterMapper.lockdown(stall);
				if (reqc.getStatus() == 1) {
//					downLock(reqc.getStallId(), 1,reqc.getType());
				}
				if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus())) {
					this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus());
				}
			}else if(code == 500) {
				if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus())) {
					if(reqc.getStatus() == 1) {
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}else if(reqc.getStatus() == 2){
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}
					redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus());
				}else {
					redisService.set(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus(),"",ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					if(reqc.getStatus() == 1) {
//						throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER);
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}else if(reqc.getStatus() == 2){
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
//						throw new BusinessException(StatusEnum.UP_LOCK_FAIL_RETRY_OWNER);
					}
				}
			}else {
				if(reqc.getStatus() == 1) {
					this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}else if(reqc.getStatus() == 2){
					this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}
			}
		}
		//解锁
		redisLock.unlock1(reqc.getRobkey());
		return flag;
	}
	
	/**
	 * 管理版锁操作
	 */
	@Override
	public Boolean operatingsn(ReqControlLock reqc) {
		boolean flag = false;
		String uid = String.valueOf(redisService.get(reqc.getKey()));
		
		if (reqc != null && StringUtils.isNotBlank(reqc.getLockSn())) {
			log.info("operating··········sn:{},··········uid:{}", reqc.getLockSn(), uid);
			Boolean res = null;
			// 1 降下 2 升起
			if (reqc.getStatus() == 1) {
				res = lockTools.downLock(reqc.getLockSn());
			} else if (reqc.getStatus() == 2) {
				res = lockTools.upLock(reqc.getLockSn());
			}
			int code = res == false ? 500:200;
			if (code == 200) {
				if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getLockSn() + reqc.getUserId() +reqc.getStatus())) {
					this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getLockSn() + reqc.getUserId() +reqc.getStatus());
				}
				if(this.redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn())) {
					this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn());
				}
				redisService.remove(reqc.getKey());
				flag = true;
			}else{
					if(code == 500) {
						if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getLockSn() + reqc.getUserId() +reqc.getStatus())) {
							if(reqc.getStatus() == 1) {
								this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							}else if(reqc.getStatus() == 2){
								this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							}
							redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getLockSn() + reqc.getUserId() +reqc.getStatus());
						}else {
							redisService.set(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getLockSn() + reqc.getUserId() +reqc.getStatus(),"",ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							if(reqc.getStatus() == 1) {
//								throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER);
								this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn(), StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							}else if(reqc.getStatus() == 2){
								this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn(), StatusEnum.UP_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
//								throw new BusinessException(StatusEnum.UP_LOCK_FAIL_RETRY_OWNER);
							}
						}
					}else {
						if(reqc.getStatus() == 1) {
							this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
						}else if(reqc.getStatus() == 2){
							this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getLockSn(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
						}
					}
			}
			
//			log.info(" operating··············" + res + " code·············" + code);
//			sendMsgT(uid, reqc.getStatus(), code);
		}
		return flag;
	}
/*	@Override
	public void operatingsn(ReqControlLock reqc) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				
				if (reqc != null && StringUtils.isNotBlank(reqc.getLockSn())) {
					log.info("operating··········sn:{},··········uid:{}", reqc.getLockSn(), uid);
					Boolean res = null;
					// 1 降下 2 升起
					if (reqc.getStatus() == 1) {
						res = lockTools.downLock(reqc.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockTools.upLock(reqc.getLockSn());
					}
					int code = res == false ? 500:200;
					if (code == 200) {
						redisService.remove(reqc.getKey());
					}
					log.info(" operating··············" + res + " code·············" + code);
					sendMsgT(uid, reqc.getStatus(), code);
				}
			}
		});
	}
*/
	void sendMsgT(String uid, Integer lockstatus, int code) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				String title = "车位锁操作通知";
				String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
				PushType type = PushType.LOCK_CONTROL_NOTICE;
				String bool = (code == 200 ? "true" : "false");
				Token token = (Token) redisService.get(RedisKey.STAFF_STAFF_AUTH_TOKEN.key + uid.toString());
				log.info("sendMsgT   send>>>" + uid+"--content");
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setTitle(title);
				rp.setContent(content);
				rp.setClient(token.getClient());
				rp.setType(type);
				rp.setData(bool);
				sendClient.give(rp);
			}
		});
	}

	@Override
	public ResStaffStallDetail findStaffStallDetails(HttpServletRequest request, Long stallId) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (!checkStaffStallAuth(cu.getId(), stallId)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		ResStaffStallDetail detail = new ResStaffStallDetail();
		Stall stall = this.stallClusterMapper.findById(stallId);
		detail.setAreaName(stall.getAreaName());
		ResLockInfo lockBean = this.lockTools.lockInfo(stall.getLockSn());
		List<ResBaseDict> baseDict = this.baseDictClient.findList(DOWN_CAUSE);
		detail.setStallId(stall.getId());
		if (lockBean != null) {
			detail.setBetty(lockBean.getElectricity());
			detail.setCarStatus(lockBean.getParkingState());
			switch (lockBean.getLockState()) {
			case 0:
				detail.setLockStatus(2);
				break;
			case 2:
				detail.setLockStatus(1);
				break;
			case 3:
				detail.setLockStatus(2);
				break;
			case 1:
				detail.setLockStatus(1);
				break;
			}
		} else {
			detail.setBetty(0);
			detail.setLockStatus(stall.getLockStatus());
		}
		detail.setStallName(stall.getStallName());
		detail.setLockSn(stall.getLockSn());
		detail.setStatus(stall.getStatus());
		if (detail.getExcCode() != null || detail.getBetty() <= 30) {
			for (ResBaseDict resBaseDict : baseDict) {
				if (detail.getExcCode() != null) {
					if (detail.getExcCode().equals(resBaseDict.getId())) {
						detail.setExcName(resBaseDict.getName());
					}
				} else {
					if (BATTERY.equals(resBaseDict.getCode())) {
						detail.setResetStatus(false);
						detail.setExcCode(resBaseDict.getId());
						detail.setExcName(resBaseDict.getName());
					}
				}
			}
		}
		if (stall.getStatus() == 4) {
			ResStallOperateLog stallOperateLog = this.stallOperateLogService.findByStallId(stallId);
			if (stallOperateLog != null) {
				detail.setFaultId(stallOperateLog.getRemarkId());
				detail.setFaultName(stallOperateLog.getRemark());
			}
		} else {
			if (stall.getStatus() == 2) {
				if(stall.getType() == 0) {
					ResUserOrder resUserOrder = this.entOrderClient.findStallLatest(stallId);
					log.info("【resUserOrder】==" + JsonUtil.toJson(resUserOrder));
					detail.setStartTime(resUserOrder.getBeginTime());
					detail.setDownTime(resUserOrder.getLockDownTime());
					detail.setOrderNo(resUserOrder.getOrderNo());
					detail.setMobile(resUserOrder.getUsername());
					detail.setOrderStatus(resUserOrder.getStatus().shortValue());
					String date = DateUtils.getDurationDetail(new Date(), resUserOrder.getBeginTime());
					detail.setStartDate(date);
					detail.setOrderId(resUserOrder.getId());
					if (resUserOrder.getOrderNo().contains("WX")) {
						detail.setOrderType("微信");
					} else if (resUserOrder.getOrderNo().contains("YL")) {
						detail.setOrderType("银联");
					} else {
						detail.setOrderType("APP");
					}
					detail.setPlate(resUserOrder.getPlateNo());
				}
				}
				if(stall.getType() == 2) {/*
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("validTime", 1);
					List<ResEntRentUser> rentUsers = opsRentUserClient.findAll(map);
					List<String> paltes = new ArrayList<>();
					StringBuilder sb = new StringBuilder();
					ResEntRentUser rentUser = null;
					for (ResEntRentUser entRentUser : rentUsers) {
						if (entRentUser.getStallId().equals(stall.getId())) {
							if (new Date().getTime() >= entRentUser.getEndTime().getTime() || entRentUser.getEntId() == null) {
								continue;
							}
							rentUser = entRentUser;
							if (!paltes.contains(entRentUser.getPlate())) {
								sb.append(entRentUser.getPlate()).append("/");
								paltes.add(entRentUser.getPlate());
							}
						}
					}
					detail.setPlate(sb.length() != 0 ? sb.substring(0, sb.length() - 1) : null);
					if (rentUser != null) {
						if (rentUser.getType() != null && rentUser.getType() == 1) {
							ResEnterprise enterprise = this.enterpriseClient.findById(rentUser.getEntId());
							if (enterprise != null) {
								detail.setMobile(enterprise.getTellphone());
							}
						} else {
							detail.setMobile(rentUser.getMobile());
						}
					}
					if (stall.getStatus() == 2) {
						ResEntRentedRecord record = this.entRentedRecordClient.findLastPlateNumber(stall.getId());
						if (record != null && record.getDownTime() != null) {
							detail.setDownTime(record.getDownTime());
						}
					}
				*/
				ResFixedPlate fixedPlate = fixedPlateClient.findPlateNosByStallId(stall.getId());
				log.info("...........manage........stallName = {} , plateNos = {}", stall.getStallName(), JSON.toJSON(fixedPlate));
				if(fixedPlate != null) {
					detail.setPlate(fixedPlate.getPlates());
					if(StringUtils.isNotEmpty(fixedPlate.getMobile())) {
						detail.setMobile(fixedPlate.getMobile());
					}
				}
				if (stall.getStatus() == 2) {
					ResEntRentedRecord record = this.entRentedRecordClient.findLastPlateNumber(stall.getId());
					if (record != null && record.getDownTime() != null) {
						detail.setDownTime(record.getDownTime());
					}
				}
				
				}
			}
			// 指定车位锁
			int assignStatus = 1;
			String lockSn = stall.getLockSn();
			Set<Object> set = redisService.members(Constants.RedisKey.ORDER_ASSIGN_STALL.key);
			log.info("指定锁池个数: {}", set.size());
			log.info("指定锁池: {}", set.toString());
			Long preId = stall.getPreId();
			for (Object obj : set) {
				JSONObject json = JSON.parseObject(obj.toString());
				String sn = json.get("lockSn").toString();
				Long pid = Long.parseLong(json.get("preId").toString());
				if (pid.longValue() == preId.longValue() && lockSn.equals(sn)) {
					assignStatus = 0;
					detail.setAssignPlate(json.get("plate").toString());
					break;
				}
			}
			detail.setAssignStatus(assignStatus);
			detail.setOnoffStatus(true);
			if(stall.getBindOrderStatus() != null && stall.getBindOrderStatus() == 1) {
				detail.setResetStatus(false);
				detail.setExcCode(0L);
				detail.setExcName("订单挂起未释放");
			}else if(stall.getBindOrderStatus() != null && stall.getBindOrderStatus() == 2) {
				detail.setResetStatus(false);
				detail.setExcCode(0L);
				detail.setExcName("订单关闭未释放");
				detail.setOrderStatus((short)7);
			}
			ResEntExcStallStatus entExcStall = feignStallExcStatusClient.findByStallId(stallId);
			if (entExcStall != null) {
				detail.setExcCode(entExcStall.getExcStatus());
				detail.setExcName(entExcStall.getExcRemark());
			}
//		}
		return detail;
	}

	@Override
	public String staffAssign(ReqAssignStall bean, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		String plate = "";
		try {
			String lockSn = bean.getLockSn();
			plate = bean.getPlate().toUpperCase();
			Long preId = bean.getPreId();
			String key = "";
			String val = "";
			// 查询空闲锁
			key = Constants.RedisKey.PREFECTURE_FREE_STALL.key + preId;
			val = lockSn;
			Stall stall = this.stallClusterMapper.findByLockSn(lockSn);
			if (stall.getStatus().intValue() == Stall.STATUS_FREE) {
				ResAdminUser user = this.adminUserService.find(cu.getId());
				// 缓存指定车位
				Map<String, Object> map = new HashMap<>();
				map.put("lockSn", lockSn);
				map.put("plate", plate);
				map.put("preId", preId);
				key = Constants.RedisKey.ORDER_ASSIGN_STALL.key;
				val = JSON.toJSON(map).toString();
				log.info("assign plate key:{} ,val:{} ", key , val);
				this.redisSetOper(0, key, val);
				// 从空闲锁池中删除
				key = Constants.RedisKey.PREFECTURE_FREE_STALL.key + preId;
				val = lockSn;
				this.redisSetOper(1, key, val);
				log.info("assign success  stall:{} ,plate:{} ", stall.getStallName(), plate);

				StallAssign sa = new StallAssign();
				sa.setCreateTime(new Date());
				sa.setLockSn(lockSn);
				sa.setCarno(plate);
				sa.setStaffId(cu.getId());
				sa.setStaffName(user.getRealname());
				sa.setPreId(stall.getPreId());
				sa.setStallId(stall.getId());
				sa.setStallName(stall.getStallName());
				sa.setStatus(StallAssign.STATUS_ASSIGN);
				this.assignService.save(sa);
			} else {
				log.info("assign fail  stall:{} ,plate:{} ", stall.getStallName(), plate);
				throw new BusinessException(StatusEnum.STALL_OPERATE_ASSIGN_FREE);
			}
		} catch (Exception e) {
			log.info("assign error plate:{} ", plate);
			throw new BusinessException(StatusEnum.STALL_OPERATE_ASSIGN);
		}
		return null;
	}

	private boolean redisSetOper(int type, String key, String val) {
		boolean member = true;
		if (type == 0) {
			redisService.add(key, val);
		} else if (type == 1) {
			redisService.remove(key, val);
		} else if (type == 3) {
			Set<Object> set = redisService.members(key);
			member = set.contains(val);
		}
		return member;
	}

	@Override
	public void staffAssignDel(ReqAssignStall bean) {
		try {
			String lockSn = bean.getLockSn();
			String plate = bean.getPlate().toUpperCase();
			Long preId = bean.getPreId();
			String key = "";
			String val = "";
			// 删除指定车位
			Map<String, Object> map = new HashMap<>();
			map.put("lockSn", lockSn);
			map.put("plate", plate);
			map.put("preId", preId);
			key = Constants.RedisKey.ORDER_ASSIGN_STALL.key;
			val = JSON.toJSON(map).toString();
			log.info("cancel assign plate key:{} ,val:{} ", key , val);
			this.redisSetOper(1, key, val);
			// 归还到空闲锁池
			key = Constants.RedisKey.PREFECTURE_FREE_STALL.key + preId;
			val = lockSn;
			this.redisSetOper(0, key, val);
			ResStallAssign sa = this.assignService.find(lockSn);
			if (sa != null) {
				sa.setCancelTime(new Date());
				sa.setStatus(StallAssign.STATUS_CANCEL);
				this.assignService.cancel(ObjectUtils.copyObject(sa, new StallAssign()));
			}
		} catch (Exception e) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_ASSIGN_DELETE);
		}
	}

	@Override
	public List<ResStall> findStallsByPreIds(Map<String, Object> map) {
		return this.stallClusterMapper.findStallsByPreIds(map);
	}

	@Override
	public void reset(Long stallId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (checkStaffStallAuth(cu.getId(), stallId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("stallId", stallId);
			map.put("status", 1);
			this.feignStallExcStatusClient.updateExcStatus(map);
		} else {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
	}

	@Override
	public ResStaffStallSn findStaffStallSn(HttpServletRequest request, String sn, Long preId) {
		ResStaffStallSn stallSn = new ResStaffStallSn();
		if(sn.contains("0000")) {
			sn = sn.substring(4).toUpperCase();
		}
		stallSn.setStallSn(sn);
		ResLockInfo lock = this.lockTools.lockInfo(sn);
		Stall stall = this.stallClusterMapper.findByLockSn(sn);
		if(stall != null) {
			stallSn.setStallId(stall.getId());
			stallSn.setAreaName(stall.getAreaName());
			CacheUser cu = (CacheUser) this.redisService
					.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
			ResAdminUser user = this.adminUserService.find(cu.getId());
			if(user != null && user.getGatewayDelete() != null) {
				stallSn.setGatewayDelete(user.getGatewayDelete());
			}
		}
		if(lock != null) {
			stallSn.setBindStata(2);
			stallSn.setBindStatus(true);
			stallSn.setLockOffLine(2);
			stallSn.setBattery(lock.getElectricity());
			switch (lock.getLockState()) {
			case 0:
				stallSn.setStallLockStatus(2);
				break;
			case 2:
				stallSn.setStallLockStatus(1);
				break;
			case 3:
				stallSn.setStallLockStatus(2);
				break;
			case 1:
				stallSn.setStallLockStatus(1);
				break;
			default:
				stallSn.setStallLockStatus(3);
				break;
			}
			stallSn.setUltrasonic(lock.getParkingState());
			stallSn.setInductionState(lock.getInductionState());
			stallSn.setModel(lock.getModel());
			stallSn.setVersion(lock.getVersion());
			ResPrefectureDetail detail = null;
			if(preId != null) {
				detail = this.prefectureService.findById(preId);
				List<ResLockGatewayList> gatewayList = lockFactory.getLock().getLockGatewayList(stallSn.getStallSn(),detail.getGateway());
				cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList rgl = null;
				if(gatewayList != null) {
					for (ResLockGatewayList resLockGatewayList : gatewayList) {
						if(resLockGatewayList.getBindFlag().equals("1")) {
							rgl = new cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList(resLockGatewayList.getGatewaySerialNumber());
							rgl.setBindFlag(resLockGatewayList.getBindFlag());
							stallSn.getGatewayList().add(rgl);
						}
					}
				}
			}
			StallLock stallLock = this.stallLockClusterMapper.findBySn(sn);
			if(stallLock != null && stallLock.getStallId() != null){
				stallSn.setInstallStatus((short)1);
			}
			if(stall != null && stallLock.getStallId() != null) {
				stallSn.setStallId(stall.getId());
				stallSn.setStallStatus(stall.getStatus().shortValue());
				if(detail == null) {
					detail = this.prefectureService.findById(stall.getPreId());
				}
				ResCity resCity = this.cityClient.getById(detail.getCityId());
				if(resCity != null) {
					stallSn.setCityName(resCity.getCityName());
				}
				detail = this.prefectureService.findById(stall.getPreId());
				stallSn.setPreName(detail.getName());
				stallSn.setPreId(detail.getId());
				stallSn.setCityId(detail.getCityId());
				stallSn.setStallName(stall.getStallName());
			}
		}
		return stallSn;
	}

	@Override
	public ResSignalHistory lockSignalHistory(HttpServletRequest request, String sn) {
		if(sn.contains("0000")) {
			sn = sn.substring(4).toUpperCase();	
		}
		return this.lockTools.lockSignalHistory(sn);
	}

	@Override
	public List<ResStaffNewAuth> findNewAuth(Long cityId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		List<ResStaffCity> list = adminAuthCityClusterMapper.findStaffCitysByAdminId(cu.getId());
		List<ResCity> selectList = this.cityClient.findSelectList();
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<ResAdminAuthPre> findListRes = this.adminAuthPreClusterMapper.findListRes(map );
		List<ResPre> pres  = null;
		if(findListRes != null) {
			List<Long> collect = findListRes.stream().map(p -> p.getPreId()).collect(Collectors.toList());
			map.put("preIds", collect);
			if(cityId != null) {
				map.put("cityId", cityId);
			}
			pres = this.prefectureService.findPreByIds(map);
		}
		ResStaffNewAuth auth = null;
		ResStaffNewAuthPre pre = null;
		List<ResStaffNewAuth> auths = new ArrayList<>();
		for (ResStaffCity resStaffCity : list) {
			if(cityId != null && resStaffCity.getCityId() != cityId) {
				continue;
			}
			for (ResCity resCity : selectList) {
				if(resStaffCity.getCityId() == resCity.getId()) {
					auth = new ResStaffNewAuth();
					auth.setCityId(resCity.getId());
					auth.setCityName(resCity.getCityName());
					for (ResAdminAuthPre resAdminAuthPre : findListRes) {
						if(pres != null) {
							for (ResPre resPre : pres) {
								if(resPre.getId() == resAdminAuthPre.getPreId() && resPre.getCityId() == resCity.getId()) {
									pre = new ResStaffNewAuthPre();
									pre.setPreId(resPre.getId());
									pre.setPreName(resPre.getName());
									auth.getPres().add(pre);
									break;
								}
							}
						}
					}
					auths.add(auth);
					break;
				}
			}
		}
	
		return auths;
	}

	@Override
	public boolean control(Long stallId, HttpServletRequest request) {
		boolean flag = false;
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			Set<Object> lockSnList =  this.redisService
					.members(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId());
			if(lockSnList.contains(stall.getLockSn()) && stall.getStatus() == 1) {
				// 争抢
				String robkey = RedisKey.ROB_STALL_ISHAVE.key + stallId;
				Boolean have = true;
				try {
					have = this.redisLock.getLock(robkey, user.getId());
					log.info("用户=======>" + user.getId() + (have == true ? "已抢到" : "未抢到") + "锁" + robkey);
				} catch (Exception e) {
					log.info("用户争抢锁异常信息{}",e.getMessage());
				}
				if (!have) {
					throw new BusinessException(StatusEnum.APPOINT_FAIL_CHECK);
				}
				// 放入缓存
				String rediskey = RedisKey.ACTION_STALL_DOING.key + stallId;
				this.redisService.set(rediskey, user.getId(), ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				log.info("用户>>>" + user.getId() + "缓存>>>" + rediskey);
				log.info("用户>>>" + user.getId() + "调用>>>" + stallId);
				log.info("<<<<<<<<<controling>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
				ResLockMessage res = null;
				// 1 降下
				Stopwatch stopwatch = Stopwatch.createStarted();
				res = lockTools.downLockMes(stall.getLockSn());
				log.info("<<<<<<<<<<<<<controlling res :{}>>>>>>>>>>>>>>>", JSON.toJSON(res));
				if(res != null) {
					log.info("<<<<<<<<<controling respose>>>>>>>>>" + res.getMessage() + "<<<code>>>" + res.getCode());
					int code = res.getCode();
					stopwatch.stop();
					log.info("<<<<<<<<<controling user：{} down:{}<<<<<<<<<using time:{}s>>>>>>>>>",user.getId(), stall.getStallName(), String.valueOf(stopwatch.elapsed(TimeUnit.SECONDS)));
					if (code == 200) {
						flag = true;
						//去掉空闲车位
						redisService.remove(rediskey);
						this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
						this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key + stallId + user.getId());
					}else if(code == 500){
						redisService.remove(rediskey);
						if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + stallId + user.getId())) {
							throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHANGE);
						} else {
							this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + stallId + user.getId(), 1,
									ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
							throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY);
						}
					}else {
						throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHANGE);
					}
				}
				redisService.remove(robkey);
			}else {
				throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHECK);
			}
		}else {
			throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHECK);
		}
		log.info("<<<<<<<<<controled result flag>>>>>>>>> = {}", flag);
	    return flag;
	}

	@Override
	public boolean controlLock(Long stallId, HttpServletRequest request) {
		boolean flag = false;
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}

		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("<<<<<<<<<order control>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResLockMessage res = null;
			// 1 降下
			Stopwatch stopwatch = Stopwatch.createStarted();
			res = lockTools.downLockMes(stall.getLockSn());
			if (res != null) {
				log.info("<<<<<<<<<order control respose>>>>>>>>>" + res.getMessage() + "<<<code>>>" + res.getCode());
				int code = res.getCode();
				stopwatch.stop();
				log.info("<<<<<<<<<order control using time>>>>>>>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.SECONDS)));
				if (code == 200) {
					flag = true;
					// 去掉空闲车位
					this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				} else if (code == 500) {
					throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY);
				} else{
					throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHANGE);
				}
			}
		}else {
			throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHECK);
		}
		log.info("<<<<<<<<<order control result flag>>>>>>>>> = {}", flag);
	    return flag;
	}

	@Override
	public boolean verify(Long stallId, HttpServletRequest request) {
		boolean flag = false;
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("<<<<<<<<<bluetooth verify>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			// 0 降下
			Stopwatch stopwatch = Stopwatch.createStarted();
			ResLockInfo lockInfo = lockTools.lockInfo(stall.getLockSn());
			log.info("<<<<<<<<<bluetooth verify>>>>>>>>>>>>lockInfo:{}",JSON.toJSON(lockInfo));
			if(lockInfo != null && lockInfo.getLockState() == 1) {
				if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + stallId + user.getId())) {
					throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHANGE);
				} else {
					this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + stallId + user.getId(), 1,
							ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY);
				}
			}
			if(lockInfo == null) {
				//锁掉线,未找到车位锁
				flag = true;
			}else {
				if(lockInfo.getLockState() == 0) {
					flag = true;
				}
			}
			stopwatch.stop();
			log.info("<<<<<<<<<bluetooth verify using time>>>>>>>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.SECONDS)));
		}else {
			throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHECK);
		}
		if(flag) {
			this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
			this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key + stallId + user.getId());
		}
		return flag;
	}
	
	@Override
	public Boolean appControl(ReqControlLock reqc) {
		ResLockMessage res =  null;
		Stall stall = stallClusterMapper.findById(reqc.getStallId());
		if (reqc.getStatus() == 1) {
			res = lockTools.downLockMes(stall.getLockSn());
		} else if (reqc.getStatus() == 2) {
			res = lockTools.upLockMes(stall.getLockSn());
		}
		log.info("操作{}返回结果{}",reqc.getStatus() == 1 ? "降锁" : "升锁" , JsonUtil.toJson(res));
		int code = res.getCode();
		//EntRentRecord record = entRentedRecordClusterMapper.findByUser(reqc.getUserId());
		Map<String,Long> param = new HashMap<String,Long>();
		param.put("userId", reqc.getUserId());
		param.put("stallId", reqc.getStallId());		
		EntRentRecord record = entRentedRecordClusterMapper.findByUserIdAndStallId(param);

		boolean falg = false;
		if (code == 200) {
			if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus())) {
				this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus());
			}
			if(this.redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId())) {
				this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId());
			}
			falg = true;
			if (reqc.getStatus() == 2) {
				
				log.info("<<<<<<<<<up success>>>>>>>>>");
				// 未完成记录同一用户只有一单
				if (Objects.nonNull(record)) {
					EntRentRecord up = new EntRentRecord();
					up.setLeaveTime(new Date());
					up.setStatus(1L);
					up.setId(record.getId());
					entRentedRecordMasterMapper.updateByIdSelective(up);
				}
				
				//若为多对一标识，若当前车位有他人使用记录，升锁则结束他人记录
				//若为多对一标识，若当前车位没有他人使用记录，升锁则结束自己记录
				//若为一对多标识，升锁则结束当前自己的记录
				
				
			} else {
				log.info("<<<<<<<<<down success>>>>>>>>>");
			}
			stall.setLockStatus(reqc.getStatus() == 1 ? 2 : 1);
			stall.setStatus(reqc.getStatus() == 1 ? 2 : 1);
			stallMasterMapper.lockdown(stall);
		} else {
			if(code == 500) {
				if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus())) {
					if(reqc.getStatus() == 1) {
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}else if(reqc.getStatus() == 2){
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}
					redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus());
				}else {
					redisService.set(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus(),"",ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					if(reqc.getStatus() == 1) {
//						throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER);
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}else if(reqc.getStatus() == 2){
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
//						throw new BusinessException(StatusEnum.UP_LOCK_FAIL_RETRY_OWNER);
					}
				}
			}else {
				if(reqc.getStatus() == 1) {
					this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}else if(reqc.getStatus() == 2){
					this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}
			}
			if (reqc.getStatus() == 1) {
				log.info("<<<<<<<<<down fail>>>>>>>>>>");
				// 降锁失败 取消绑定
				if (Objects.nonNull(record)) {
					EntRentRecord up = new EntRentRecord();
					up.setLeaveTime(new Date());
					up.setStatus(2L);
					up.setId(record.getId());
					entRentedRecordMasterMapper.updateByIdSelective(up);
				}
			} else {
				log.info("<<<<<<<<<up fail>>>>>>>>>>");
			}
		}
		return falg;
	}

	@Override
	public List<cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList> findLockGateways(
			HttpServletRequest request, String lockSn, Long preId) {
		ResPrefectureDetail detail = this.prefectureService.findById(preId);
		List<ResLockGatewayList> gatewayList = lockFactory.getLock().getLockGatewayList(lockSn,detail.getGateway());
		List<cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList> list =new ArrayList<>();
		cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList gateway = null;
		for (ResLockGatewayList resLockGatewayList : gatewayList) {
			gateway = new cn.linkmore.prefecture.controller.staff.response.ResLockGatewayList(resLockGatewayList.getGatewaySerialNumber());
			gateway.setBindFlag(resLockGatewayList.getBindFlag());
			list.add(gateway);
		}
		return list;
	}
	
	public Boolean controlDown(ReqOrderStall reqOrderStall) {
		Boolean flag = false;
		Stall stall = stallClusterMapper.findById(reqOrderStall.getStallId());
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("<<<<<<<<<stall control down>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResLockMessage res = null;
			// 1 降下
			Stopwatch stopwatch = Stopwatch.createStarted();
			res = lockTools.downLockMes(stall.getLockSn());
			if (res != null) {
				log.info("<<<<<<<<<stall control down respose>>>>>>>>>" + res.getMessage() + "<<<code>>>" + res.getCode());
				int code = res.getCode();
				stopwatch.stop();
				log.info("<<<<<<<<<stall control down using time>>>>>>>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.SECONDS)));
				if (code == 200) {
					flag = true;
					log.info("downing.....................success");
					stall.setLockStatus(LockStatus.DOWN.status);
					stallMasterMapper.lockdown(stall);
					this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key + reqOrderStall.getOrderId());
					// 去掉空闲车位
					this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				} else if (code == 500) {
					if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + reqOrderStall.getOrderId())) {
						//throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHANGE);
						this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key+reqOrderStall.getOrderId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					} else {
						//throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY);
						this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + reqOrderStall.getOrderId(), StatusEnum.DOWN_LOCK_FAIL_RETRY.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					}
				} else{
					//throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHANGE);					
					this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key+reqOrderStall.getOrderId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
				}
			}
		}else {
			//throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_CHECK);
			this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key+reqOrderStall.getOrderId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
		}
		log.info("<<<<<<<<<stall control down flag>>>>>>>>> = {}", flag);
		return flag;
	}

	@Override
	public Boolean editLockBindGateway(HttpServletRequest request, String serialNumbers, String lockSn) {
		Boolean gateway = this.lockFactory.getLock().batchBindGateway(lockSn, serialNumbers);
		return gateway;
	}

	@Override
	public void delete(List<Long> ids) {
		this.stallMasterMapper.deleteIds(ids);
	}
	
	public int update(Stall stall) {
		stall.setUpdateTime(new Date());
		return stallMasterMapper.update(stall);
	}

	@Override
	public boolean upLock(Long stallId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean controlUp(ReqOrderStall reqOrderStall) {
		Boolean flag = false;
		Stall stall = stallClusterMapper.findById(reqOrderStall.getStallId());
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("<<<<<<<<<stall control up>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResLockMessage res = null;
			// 1 降下
			Stopwatch stopwatch = Stopwatch.createStarted();
			res = lockTools.upLockMes(stall.getLockSn());
			if (res != null) {
				log.info("<<<<<<<<<stall control up respose>>>>>>>>>" + res.getMessage() + "<<<code>>>" + res.getCode());
				int code = res.getCode();
				stopwatch.stop();
				log.info("<<<<<<<<<stall control up using time>>>>>>>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.SECONDS)));
				if (code == 200) {
					flag = true;
					log.info("uping.....................success");
					stall.setStatus(StallStatus.FREE.status);
					stall.setLockStatus(LockStatus.UP.status);
					stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
					stallMasterMapper.lockdown(stall);
					this.redisService.remove(RedisKey.ORDER_STALL_UP_FAILED.key + reqOrderStall.getOrderId());
					this.redisService.remove(RedisKey.PREFECTURE_BUSY_STALL.key + stall.getLockSn());
					this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				} else if (code == 500) {
					if (this.redisService.exists(RedisKey.ORDER_STALL_UP_FAILED.key + reqOrderStall.getOrderId())) {
						//升锁第二次失败，故障上报
						this.redisService.set(RedisKey.ORDER_STALL_UP_FAILED.key+reqOrderStall.getOrderId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					} else {
						//升锁第一次失败，再升一次
						this.redisService.set(RedisKey.ORDER_STALL_UP_FAILED.key + reqOrderStall.getOrderId(), StatusEnum.UP_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					}
				} else{
					//网关问题，升锁失败，故障上报
					this.redisService.set(RedisKey.ORDER_STALL_UP_FAILED.key+reqOrderStall.getOrderId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
				}
			}
		}else {
			this.redisService.set(RedisKey.ORDER_STALL_UP_FAILED.key+reqOrderStall.getOrderId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
		}
		log.info("<<<<<<<<<stall control up flag>>>>>>>>> = {}", flag);
		return flag;
	}

	@Override
	@Transactional
	public Boolean installLock(ReqLockIntall reqLockIntall, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		ResAdminUser adminUser = adminUserService.find(cu.getId());
		Stall stall = this.stallClusterMapper.findByLockNameAndPreId(reqLockIntall.getStallName(), reqLockIntall.getPreId());
		if(stall != null) {
			if(!checkStaffStallAuth(cu.getId(), stall.getId())) {
				throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
			}
			if(stall.getStatus() != 4) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_UNOFFLINE);
			}
			if(stall.getLockId() != null || StringUtils.isNotBlank(stall.getLockSn())) {
				throw new BusinessException(StatusEnum.STAFF_STALL_EXIST);
			}
			StallLock stallLock = insertLock(reqLockIntall,adminUser);
			updateLockStallId(stall.getId(), stall.getPreId(), stallLock.getId());
			updateStallLockId(reqLockIntall.getLockSn(), stallLock.getId(), stall);
		}else {
			StallLock stallLock = insertLock(reqLockIntall,adminUser);
			stall = installStall(reqLockIntall, stallLock.getId());
			updateLockStallId(stall.getId(), stall.getPreId(), stallLock.getId());
			authUserStall(cu, stall.getId());
		}
		notityLockTerrace(reqLockIntall);
		return true;
	}

	private StallLock insertLock(ReqLockIntall reqLockIntall, ResAdminUser adminUser) {
		StallLock stallLock = new StallLock();
		stallLock.setCreateTime(new Date());
		stallLock.setSn(reqLockIntall.getLockSn());
		stallLock.setCreateUserName(adminUser.getRealname());
		stallLock.setCreateUserId(adminUser.getId());
		stallLockMasterMapper.save(stallLock);	
		return stallLock;
	}
	private void updateStallLockId(String lockSn,Long lockId,Stall stall) {
		stall.setLockSn(lockSn);
		stall.setLockId(lockId);
		stallMasterMapper.update(stall);
	}
	private void notityLockTerrace(ReqLockIntall reqLockIntall) {
		Map<String, Object> map = new TreeMap<>();
		map.put("serialNumber", reqLockIntall.getLockSn());
		map.put("name", reqLockIntall.getStallName());
		lockTools.setLockName(map);
	}
	private Stall installStall(ReqLockIntall reqLockIntall,Long lockId) {
		Stall stallName = new Stall();
		stallName.setStallName(reqLockIntall.getStallName());
		stallName.setSellCount(0);
		stallName.setPreId(reqLockIntall.getPreId());
		stallName.setGatewayId(0l);
		stallName.setBindOrderStatus((short) 0);
		stallName.setStatus(4);
		stallName.setLockBattery(0);
		stallName.setCreateTime(new Date());
		stallName.setUpdateTime(new Date());
		stallName.setLockSn(reqLockIntall.getLockSn());
		stallName.setLockId(lockId);
		stallName.setLockStatus(0);
		stallName.setLockBattery(0);
		stallName.setType((short)0);
		stallName.setAreaName(reqLockIntall.getAreaName());
		stallName.setStallLocal(reqLockIntall.getStallName());
		// 插入车位
		this.stallMasterMapper.save(stallName);
		return stallName;
	}
	
	private void authUserStall(CacheUser cu,Long stallId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", cu.getId());
		List<ResAdminUserAuth> userAuth = this.adminUserAuthClusterMapper.findList(param );
		if(userAuth == null || userAuth.size() == 0) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		param.put("stallId", stallId);
		List<ResAdminAuthStall> list = this.adminAuthStallClusterMapper.findStallList(param);
		if(list == null || list.size() == 0) {
			AdminAuthStall record = new AdminAuthStall();
			record.setAuthId(userAuth.get(0).getAuthId());
			record.setStallId(stallId);
			this.AdminAuthStallMasterMapper.save(record );
		}
	}
	private void updateLockStallId(Long stallId, Long preId,Long lockId) {
		StallLock stallLock = new StallLock();
		stallLock.setBindTime(new Date());
		stallLock.setStallId(stallId);
		stallLock.setPrefectureId(preId);
		stallLock.setId(lockId);
		stallLockMasterMapper.updateBind(stallLock);
	}

	@Override
	public void removeStallLock(Long stallId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));		
		Stall stall = this.stallClusterMapper.findById(stallId);
		if(stall == null || stall.getLockSn() == null) {
			throw new BusinessException(StatusEnum.LOCK_SN_EXISTS);
		}
		if(!checkStaffStallAuth(cu.getId(), stall.getId())) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		if(stall.getStatus().intValue() != 4) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_UNOFFLINE);
		}
//		this.stallMasterMapper.delete(stall.getId());
		stallLockMasterMapper.delete(stall.getLockId());
		lockFactory.getLock().removeLock(stall.getLockSn());
		stall.setLockId(null);
		stall.setLockSn(null);
		this.stallMasterMapper.update(stall);
	}

	@Override
	public ResStaffPreDetails findPreStallDetails(Long preId, String floor) {
		return this.stallClusterMapper.findPreStallDetails(preId,floor);
	}
	
	
}