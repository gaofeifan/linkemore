package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.app.response.OwnerStall;
import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentedRecordMasterMapper;
import cn.linkmore.enterprise.entity.EntOwnerPre;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.StringUtil;
import cn.linkmore.util.TokenUtil;

@Service
public class OwnerStallServiceImpl implements OwnerStallService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;

	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;
	
	@Autowired
	private  EntRentedRecordMasterMapper entRentedRecordMasterMapper;
	
	@Autowired
	private  EntRentedRecordClusterMapper entRentedRecordClusterMapper;

	@Autowired
	private StallClient stallClient;

	@Override
	public OwnerRes findStall(HttpServletRequest request) {
		OwnerRes  res = new OwnerRes();
		Boolean isHave =false;
		int  num = 0;
		try {
			// 鉴权
			String key = TokenUtil.getKey(request);
			CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + key);
			if (user == null) {
				throw new RuntimeException(StatusEnum.USER_APP_NO_LOGIN.label);
			}
			Long userId = user.getId();

			//查询是否有未完成进程
			EntRentedRecord record = entRentedRecordClusterMapper.findByUser(userId);
		
			List<EntOwnerPre> prelist = ownerStallClusterMapper.findPre(userId);

			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(userId);
			log.info("车位>>>" + stalllist.size()+"车区>>>" + prelist.size()+"用户>>>" + JSON.toJSONString(user));

			List<OwnerPre> list = new ArrayList<>();

			if( record!=null ) { //未完成进程
				for (EntOwnerPre pre : prelist) {
					if(pre.getPreId().equals( record.getPreId()) ) {
						OwnerPre ownerpre = new OwnerPre();
						ownerpre.setPreId(pre.getPreId());
						ownerpre.setPreName(pre.getPreName());
						ownerpre.setAddress(pre.getAddress());
						ownerpre.setLatitude(pre.getLatitude());
						ownerpre.setLongitude(pre.getLongitude());
						List<OwnerStall> ownerstalllist = new ArrayList<>();
						for (EntOwnerStall enttall : stalllist) {
							if(enttall.getStallId().equals(record.getStallId()) ) {
								OwnerStall OwnerStall = new OwnerStall();
								OwnerStall.setStallId(enttall.getStallId());
								OwnerStall.setMobile(enttall.getMobile());
								OwnerStall.setPlate(enttall.getPlate());
								OwnerStall.setStallName(enttall.getStallName());
								OwnerStall.setStartTime(handleTime(enttall.getStartTime()));
								OwnerStall.setEndTime(handleTime(enttall.getEndTime()));
								OwnerStall.setImageUrl(enttall.getImageUrl());
								OwnerStall.setRouteGuidance(enttall.getRouteGuidance());
								OwnerStall.setStallLocal(enttall.getStallLocal());
								ownerstalllist.add(OwnerStall);
								num++;
								isHave = true;
								break;
							}
						}
						ownerpre.setStalls(ownerstalllist);
						list.add(ownerpre);
					 break;	
					}
				}
			}else {
				for (EntOwnerPre pre : prelist) {
					OwnerPre ownerpre = new OwnerPre();
					ownerpre.setPreId(pre.getPreId());
					ownerpre.setPreName(pre.getPreName());
					ownerpre.setAddress(pre.getAddress());
					ownerpre.setLatitude(pre.getLatitude());
					ownerpre.setLongitude(pre.getLongitude());
					List<OwnerStall> ownerstalllist = new ArrayList<>();

					for (EntOwnerStall enttall : stalllist) {
						if (pre.getPreId().equals(enttall.getPreId())) {
							OwnerStall OwnerStall = new OwnerStall();
							OwnerStall.setStallId(enttall.getStallId());
							OwnerStall.setMobile(enttall.getMobile());
							OwnerStall.setPlate(enttall.getPlate());
							OwnerStall.setStallName(enttall.getStallName());
							OwnerStall.setStartTime(handleTime(enttall.getStartTime()));
							OwnerStall.setEndTime(handleTime(enttall.getEndTime()));
							OwnerStall.setImageUrl(enttall.getImageUrl());
							OwnerStall.setRouteGuidance(enttall.getRouteGuidance());
							OwnerStall.setStallLocal(enttall.getStallLocal());
							// 插入锁状态
							try {
								ResStallEntity ress = stallClient.findById(enttall.getStallId());
								OwnerStall.setStatus(ress.getStatus());
								OwnerStall.setLockStatus(ress.getLockStatus());
							} catch (Exception e) {
								
							}
							num++;
							ownerstalllist.add(OwnerStall);
						}
					}
					ownerpre.setStalls(ownerstalllist);
					list.add(ownerpre);
				}
			}
			
			res.setRes(list);
			res.setIsHave(isHave);
			res.setNum(num);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer control(ReqConStall reqOperatStall, HttpServletRequest request) {
		// 鉴权
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key +  TokenUtil.getKey(request));
		
		/*user = new CacheUser();
		user.setId(2820L);*/
		
		if (user == null) {
			log.info("--------未登录--------" );
			return -1;
		}
		log.info("用户>>>" + JSON.toJSONString(user));
		String rediskey = RedisKey.ACTION_STALL_DOING.key + reqOperatStall.getStallId();
		//他人使用中
		String val = String.valueOf(this.redisService.get(rediskey));
		if(StringUtil.isNotBlank(val)) {
			if(!val.equals(String.valueOf(user.getId()))) {
				log.info("--------他人使用中-----"+user.getId());
				return -2;
			}
		}
		//未完成记录
		EntRentedRecord record = entRentedRecordClusterMapper.findByUser(user.getId());
		if(reqOperatStall.getState()==2) {//升起
			log.info(user.getId() +"---------升起--------"+reqOperatStall.getStallId());
			if(Objects.nonNull(record)) {
				EntRentedRecord up = new EntRentedRecord();
				up.setLeaveTime(new Date());
				up.setStatus(1L);
				up.setId(record.getId());
				entRentedRecordMasterMapper.updateByIdSelective(up);
			}
		}else if(reqOperatStall.getState()==1) {//降下
			log.info(user.getId() +"---------降下--------"+reqOperatStall.getStallId());
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(user.getId());
			if(record==null) {
				EntRentedRecord	 newrecord = new EntRentedRecord();
				for (EntOwnerStall entOwnerStall : stalllist) {
					if (reqOperatStall.getStallId().equals(entOwnerStall.getStallId())) {
						newrecord.setStallId(entOwnerStall.getStallId());
						newrecord.setUserId(user.getId());
						newrecord.setStatus(0L);
						newrecord.setDownTime(new Date());
						newrecord.setPreId(entOwnerStall.getPreId());
						newrecord.setStallName(entOwnerStall.getStallName());
						break;
					}
				}
				try {
					entRentedRecordMasterMapper.saveSelective(newrecord);
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.info(user.getId() +"---------插入record--------"+reqOperatStall.getStallId());
			}
		}
		// 放入缓存
		this.redisService.set(rediskey,user.getId(),ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
		log.info(user.getId() +"---------放入缓存--------"+rediskey);
		// 调用
		ReqControlLock reqc = new ReqControlLock();
		reqc.setKey(rediskey);
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		stallClient.controllock(reqc);
		log.info(user.getId() +"---------异步调用--------"+reqOperatStall.getStallId());
		return 0;
	}
	
	@Override
	public Integer watch(Long stallId, HttpServletRequest request) {
		try {
			CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
			/*user = new CacheUser();
			user.setId(2820L);*/
			String rediskey = RedisKey.ACTION_STALL_DOING.key + stallId;
			if (user == null) {
				return -1;
			}
			String  val=  String.valueOf(this.redisService.get(rediskey));
			log.info(user.getId() +"---------查看缓存--------"+rediskey);
			if(StringUtil.isNotBlank(val)) {
				if(val.equals( String.valueOf(user.getId()))) {
					return 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static String handleTime(String time) {
		time = time.replace(" ", "");
		time = time.replace("-", "");
		time = time.replace(":", "");
		String newtime = null;
		if (time != null) {
			newtime = time.substring(0, 4) + "年" + time.substring(4, 6) + "月" + time.substring(6, 8) + "日"
					+ time.substring(8, 10) + "时" + time.substring(10, 12) + "分";
		}
		return newtime;
	}

	@Override
	public Boolean owner(HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Boolean  is =false;
		if(user!=null) {
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(user.getId());
			if(stalllist.size()>0) {
				is =true;
			}
		}
		log.info("用户>>>" + JSON.toJSONString(user));
		return is;
	}

}
