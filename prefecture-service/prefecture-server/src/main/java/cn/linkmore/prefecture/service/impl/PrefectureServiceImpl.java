package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.UserStaffStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.controller.app.request.ReqBooking;
import cn.linkmore.prefecture.controller.app.request.ReqPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPreCity;
import cn.linkmore.prefecture.controller.app.response.ResPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureList;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.controller.app.response.ResStall;
import cn.linkmore.prefecture.controller.app.response.ResStallInfo;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureMasterMapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.MapUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * Service实现类 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper;
	@Autowired
	private StrategyBaseClusterMapper strategyBaseClusterMapper;
	@Autowired
	private PrefectureMasterMapper prefectureMasterMapper;
	@Autowired
	private UserStaffClient userStaffClient;
	
	@Autowired
	private VehicleMarkClient vehicleMarkClient;
	
	@Autowired
	private OrderClient orderClient;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public ResPrefectureDetail findById(Long preId) {
		ResPrefectureDetail detail = prefectureClusterMapper.findById(preId);
		return detail;
	}
	
	@Override
	public ResPrefectureStrategy findPreStrategy(Long preId) {
		String mins = "分钟";
		String freetime = "免费时长";
		ResPrefectureStrategy bean = null;
		ResPrefectureDetail prefecture = prefectureClusterMapper.findById(preId);
		if(prefecture != null){
			bean = new ResPrefectureStrategy();
			StrategyBase strategyBase = strategyBaseClusterMapper.findById(prefecture.getStrategyId());
			bean.setFreeMins(freetime+strategyBase.getFreeMins()+" "+mins);
			bean.setContent(prefecture.getStrategyDescription());
		}
		return bean;
	}
	@Override
	public List<ResPre> findList(List<Long> ids) {
		List<ResPre> list = this.prefectureClusterMapper.findByIds(ids);
		return list;
	}
	
	@Override
	public List<ResPre> findPreByIds(Map<String,Object> map) {
		List<ResPre> list = this.prefectureClusterMapper.findPreByIds(map);
		return list;
	}
	
	@Override
	public List<ResPrefectureList> getStallCount(HttpServletRequest request) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+TokenUtil.getKey(request)); 
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", 0);
		//此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		
		if(cu!=null && cu.getId()!=null){ 
			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if(us!=null&&us.getStatus().intValue() == UserStaffStatus.ON.status){
				List<ResPrefecture> preList1 = prefectureClusterMapper.findPreByStatusAndGPS1(paramMap);
				if(preList1!=null){
					if(preList==null){
						preList = preList1;
					}else{
						preList.addAll(preList1);
					}
				}
			} 
		}
		
		log.info("get_stall_count pre size :{}", preList.size());
		List<ResPrefectureList> list = new ArrayList<ResPrefectureList>();
		ResPrefectureList pre = null;
		Long count = 0L;
		if(CollectionUtils.isNotEmpty(preList)) {
			for(ResPrefecture resPre:preList) {
				pre = new ResPrefectureList();
				pre.setId(resPre.getId());
				count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + resPre.getId());
				log.info("preId {} free stall count {}",resPre.getId(), count);
				if(count==null) {
					count = 0L;
				}
				pre.setLeisureStall(count.intValue());
				list.add(pre);
			}
		}
		return list;
	}
	/**
	 * 根据车区id查询所有空闲车位
	 * @param preId
	 * @return
	 */
//	public Integer getFreeStall(Long preId) {
//		List<ResStall> stallList = this.stallClusterMapper.findStallsByPreId(preId);
//		int count = 0;
//		if(stallList!=null) {
//			count = stallList.size();
//		}
//		return count;
//	}
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.prefectureClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResPreExcel> list = this.prefectureClusterMapper.findPage(param);
		
		for (ResPreExcel resPre : list) {
			StrategyBase strategy = this.clonePrefecture(resPre);
			resPre.setFirstHourDisplay(strategy.getFirstFeePrice());
			resPre.setBasePriceDisplay(strategy.getBaseFeePrice());
			resPre.setNightPriceDisplay(strategy.getNightFeePrice());
		}
		
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	
	// 计费策略和专区信息的clone
	private StrategyBase clonePrefecture(ResPreExcel bean) {
		if (bean == null) {
			return null;
		}
		StrategyBase fee = new StrategyBase();
		BeanUtils.copyProperties(bean, fee);
		return fee;
	}
	
	@Override
	public List<Map<String, Object>> findByCity(Long cityId) {
		List<Map<String, Object>> res = new ArrayList<>();
		Map<String, Object> re = null;
		List<ResPre> list = prefectureClusterMapper.findListByCityId(cityId);
		re = new HashMap<>();
		re.put("id", -1);
		re.put("name", "停车场名称");
		res.add(re);
		for (ResPre p : list) {
			re = new HashMap<>();
			re.put("id", p.getId());
			re.put("name", p.getName());
			res.add(re);
		}
		return res;
	}
	@Override
	public List<ResPreExcel> exportList(ReqPreExcel reqPreExcel) {
		List<ResPreExcel> list = this.prefectureClusterMapper.findExportList();
		for (ResPreExcel responseBean : list) {
			StrategyBase strategy = this.clonePrefecture(responseBean);
			responseBean.setFirstHourDisplay(strategy.getFirstFeePrice());
			responseBean.setBasePriceDisplay(strategy.getBaseFeePrice());
			responseBean.setNightPriceDisplay(strategy.getNightFeePrice());
		}
		return list;
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.prefectureClusterMapper.check(param);
	}
	
	@Override
	public List<ResPreList> findSelectList() {
		return this.prefectureClusterMapper.findSelectList();
	}
	
	@Override
	public int delete(List<Long> ids) {
		return this.prefectureMasterMapper.delete(ids);
	}
	
	@Override
	public int save(ReqPrefectureEntity reqPre) {
		Prefecture pre = new Prefecture();
		pre = ObjectUtils.copyObject(reqPre, pre);
		pre.setCreateTime(new Date());
		return this.prefectureMasterMapper.save(pre);
	}
	
	@Override
	public int update(ReqPrefectureEntity reqPre) {
		Prefecture pre = new Prefecture();
		pre = ObjectUtils.copyObject(reqPre, pre);
		pre.setCreateTime(new Date());
		return this.prefectureMasterMapper.update(pre);
	}
	@Override
	public Tree findTree() {
		Map<String, Object> param = new HashMap<>();
		param.put("status", "0");
		List<ResPre> preList = prefectureClusterMapper.findTreeList(param);
		Tree tree = null;
		List<Tree> rtrees = new ArrayList<Tree>();
		Map<Long, Tree> ttreeMap = new HashMap<>();
		for (ResPre pre : preList) {
			tree = new Tree();
			tree.setId("" + pre.getId());
			tree.setName(pre.getName());
			tree.setIsParent(false);
			tree.setCode(pre.getId().toString());
			tree.setmId(pre.getId().toString());
			tree.setOpen(true);
			tree.setChildren(new ArrayList<Tree>());
			rtrees.add(tree);
			ttreeMap.put(pre.getId(), tree);
		}
		Tree root = new Tree();
		root.setName("车区树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0");
		root.setChildren(rtrees);
		return root;
	}
	@Override
	public ResPrefectureDetail checkName(Map<String, Object> param) {
		return this.prefectureClusterMapper.checkName(param);
	}
	@Override
	public List<ResPreCity> list(ReqPrefecture rp, HttpServletRequest request) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+TokenUtil.getKey(request)); 
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", 0);
		//此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap); 
		Long plateId = null;
		String plateNumber = null;
		if(cu!=null && cu.getId()!=null){
			ResUserOrder ro = this.orderClient.last(cu.getId());
			List<ResVechicleMark> plates = this.vehicleMarkClient.list(cu.getId());
			if(ro!=null) {
				Map<String,Long> plateMap = new HashMap<String,Long>();
				for(ResVechicleMark rvm:plates) {
					plateMap.put(rvm.getVehMark(), rvm.getId());
				}
				plateNumber = ro.getPlateNo();
				plateId = plateMap.get(plateNumber);
				if(plateId==null) {
					plateNumber = null; 
				}
			}

			if(plateNumber==null&&CollectionUtils.isNotEmpty(plates)){
				plateId = plates.get(0).getId();
				plateNumber = plates.get(0).getVehMark();
			}
			
			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if(us!=null&&us.getStatus().intValue() == UserStaffStatus.ON.status){
				List<ResPrefecture> preList1 = prefectureClusterMapper.findPreByStatusAndGPS1(paramMap);
				if(preList1!=null){
					if(preList==null){
						preList = preList1;
					}else{
						preList.addAll(preList1);
					}
				}
			} 
		}
		Long count = 0L;
		for(ResPrefecture prb: preList){ 
			prb.setPlateId(plateId);
			prb.setPlateNumber(plateNumber);
			prb.setChargeTime(prb.getChargeTime() + "分钟");
			prb.setChargePrice(prb.getChargePrice() + "元");
			count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prb.getId());
			if(count==null) {
				count = 0L;
			}
			prb.setLeisureStall(count.intValue());
			prb.setDistance(MapUtil.getDistance(prb.getLatitude(), prb.getLongitude(), new Double(rp.getLatitude()), new Double(rp.getLongitude())));
		}
		
		
		Map<Long, List<ResPrefecture>> map = preList.stream().collect(Collectors.groupingBy(ResPrefecture::getCityId));
		
		List<ResPreCity> resPreCityList = new ArrayList<ResPreCity>();
		ResPreCity resPreCity = null;
		for(Long cityId : map.keySet()){
			resPreCity = new ResPreCity();
			resPreCity.setCityId(cityId);
			List<ResPrefecture> prefecturelist = map.get(cityId);
			Collections.sort(prefecturelist,new Comparator<ResPrefecture>(){  
	            public int compare(ResPrefecture pre1, ResPrefecture pre2) {
	                return Double.valueOf(pre1.getDistance()).compareTo(Double.valueOf(pre2.getDistance()));  
	            }  
	        });
			
			resPreCity.setPrefectures(prefecturelist);
			resPreCityList.add(resPreCity);
		}
		return resPreCityList;
	}

	@Override
	public List<cn.linkmore.prefecture.response.ResPrefecture> findPreList() {
		return this.prefectureClusterMapper.findPreList();
	}

	@Override
	public List<ResPrefectureDetail> findList(Map<String, Object> param) {
		return this.prefectureClusterMapper.findList(param);
	}
	
	public boolean checkCarFree(String carno) {
		boolean flag = true;
		try {
			Integer status = this.orderClient.getPlateLastOrderStatus(carno);
			if (status != null && status.intValue() == 1) {
				flag = false;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	@Override
	public ResStallInfo findStallList(ReqBooking reqBooking) {
		ResStallInfo stallInfo = new ResStallInfo();
		List<ResStall> stallList = new ArrayList<ResStall>();
		ResVechicleMark vehicleMark = vehicleMarkClient.findById(reqBooking.getPlateId());
		if(vehicleMark == null) {
			throw new BusinessException(StatusEnum.VALID_EXCEPTION);
		}
		String vehMark = vehicleMark.getVehMark();    //车牌号
		log.info("vehicleMark = {}",JSON.toJSON(vehicleMark));
		if (!this.checkCarFree(vehicleMark.getVehMark())) {
			throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY);  //当前车牌号已在预约中，请更换车牌号重新预约
		}
		boolean assign = false;
		Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key);  //集合中所有成员元素
		for (Object obj : set) {
			JSONObject json = JSON.parseObject(obj.toString());
			String vm = json.get("plate").toString();    //车牌
			Long pid = Long.parseLong(json.get("preId").toString());  //车区id
			if (pid.longValue() == reqBooking.getPrefectureId().longValue() && vehMark.equals(vm)) {   //找到车区
				String lockSn = json.get("lockSn").toString();
				assign = true;
				log.info("use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
				break;
			}
		}
		Set<Object> lockSnList = this.redisService.members(RedisKey.PREFECTURE_FREE_STALL.key + reqBooking.getPrefectureId());  //集合中所有成员元素
		if(CollectionUtils.isNotEmpty(lockSnList)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("list", lockSnList);
			ResStall resStall = null;
			List<cn.linkmore.prefecture.response.ResStall> freeStallList = stallClusterMapper.findFreeStallList(params);
			log.info("---------freeStallList = {}",JSON.toJSON(freeStallList));
			for(cn.linkmore.prefecture.response.ResStall stall: freeStallList) {
				resStall = new ResStall();
				resStall.setStallId(stall.getId());
				resStall.setLockSn(stall.getLockSn());
				resStall.setStallName(stall.getStallName());
				stallList.add(resStall);
			}
		}
		ResPrefectureDetail pre = this.prefectureClusterMapper.findById(reqBooking.getPrefectureId());
		if(pre != null) {
			stallInfo.setPreName(pre.getName());
		}
		stallInfo.setAssignFlag(assign);
		stallInfo.setStalls(stallList);
		log.info("stallInfo = {}",JSON.toJSON(stallInfo));
		if(!assign) {
			if(CollectionUtils.isEmpty(stallList)) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE);  //无空闲车位可用
			}
		}
		return stallInfo;
	}
	
}
