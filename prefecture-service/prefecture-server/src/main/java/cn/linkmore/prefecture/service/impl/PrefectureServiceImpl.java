package cn.linkmore.prefecture.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;
import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.UserStaffStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.config.LockTools;
import cn.linkmore.prefecture.controller.app.request.ReqBooking;
import cn.linkmore.prefecture.controller.app.request.ReqNearPrefecture;
import cn.linkmore.prefecture.controller.app.request.ReqPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPreCity;
import cn.linkmore.prefecture.controller.app.response.ResPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureGroup;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureList;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.controller.app.response.ResStall;
import cn.linkmore.prefecture.controller.app.response.ResStallInfo;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyGroupClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyGroupDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureMasterMapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.entity.StrategyGroupDetail;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StrategyFeeService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.MapDistance;
import cn.linkmore.util.MapUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * Service实现类 - 车区信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LockTools lockTools;
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
	private CityClient cityClient;

	@Autowired
	private RedisService redisService;

	@Autowired
	private StrategyGroupClusterMapper strategyGroupClusterMapper;

	@Autowired
	private StrategyGroupDetailClusterMapper strategyGroupDetailClusterMapper;

	@Autowired
	private StrategyFeeService strategyFeeService;

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
		if (prefecture != null) {
			bean = new ResPrefectureStrategy();
			StrategyBase strategyBase = strategyBaseClusterMapper.findById(prefecture.getStrategyId());
			bean.setFreeMins(freetime + strategyBase.getFreeMins() + " " + mins);
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
	public List<ResPre> findPreByIds(Map<String, Object> map) {
		List<ResPre> list = this.prefectureClusterMapper.findPreByIds(map);
		return list;
	}

	@Override
	public List<ResPrefectureList> getStallCount(HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", 0);
		// 此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);

		if (cu != null && cu.getId() != null) {
			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if (us != null && us.getStatus().intValue() == UserStaffStatus.ON.status) {
				List<ResPrefecture> preList1 = prefectureClusterMapper.findPreByStatusAndGPS1(paramMap);
				if (preList1 != null) {
					if (preList == null) {
						preList = preList1;
					} else {
						preList.addAll(preList1);
					}
				}
			}
		}
		log.info("get_stall_count pre size :{}", preList.size());
		List<ResPrefectureList> list = new ArrayList<ResPrefectureList>();
		ResPrefectureList pre = null;
		Long count = 0L;
		if (CollectionUtils.isNotEmpty(preList)) {
			for (ResPrefecture resPre : preList) {
				pre = new ResPrefectureList();
				pre.setId(resPre.getId());
				count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + resPre.getId());
				log.info("preId {} free stall count {}", resPre.getId(), count);
				if (count == null) {
					count = 0L;
				}
				pre.setLeisureStall(count.intValue());
				list.add(pre);
			}
		}
		return list;
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

		return new ViewPage(count, pageable.getPageSize(), list);
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
		Map<String, Object> param = new HashMap<String, Object>();
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
	public List<ResPreList> findSelectListByUser(Map<String, Object> param) {
		return this.prefectureClusterMapper.findSelectListByUser(param);
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
	public Tree findTree(Map<String, Object> param) {
		/*
		 * Map<String, Object> param = new HashMap<>(); param.put("status", "0");
		 */
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
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<Long, String> cityMap = new HashMap<Long, String>();
		paramMap.put("status", 0);
		// 此处cityFlag=0，返回所有的车区信息
		if ("1".equals(rp.getCityFlag())) {
			paramMap.put("cityId", rp.getCityId());
		}
		if(StringUtils.isNotBlank(rp.getPreName())) {
			paramMap.put("name", '%'+ rp.getPreName()+ '%');
		}
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		List<ResCity> cityList = cityClient.findSelectList();
		if(CollectionUtils.isNotEmpty(cityList)) {
			for(ResCity city: cityList) {
				cityMap.put(city.getId(), city.getCityName());
			}
		}
		Long plateId = null;
		String plateNumber = null;
		if (cu != null && cu.getId() != null) {
			ResUserOrder ro = this.orderClient.last(cu.getId());
			List<ResVechicleMark> plates = this.vehicleMarkClient.list(cu.getId());
			if (ro != null) {
				Map<String, Long> plateMap = new HashMap<String, Long>();
				for (ResVechicleMark rvm : plates) {
					plateMap.put(rvm.getVehMark(), rvm.getId());
				}
				plateNumber = ro.getPlateNo();
				plateId = plateMap.get(plateNumber);
				if (plateId == null) {
					plateNumber = null;
				}
			}
			if (plateNumber == null && CollectionUtils.isNotEmpty(plates)) {
				plateId = plates.get(0).getId();
				plateNumber = plates.get(0).getVehMark();
			}

			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if (us != null && us.getStatus().intValue() == UserStaffStatus.ON.status) {
				List<ResPrefecture> preList1 = prefectureClusterMapper.findPreByStatusAndGPS1(paramMap);
				if (preList1 != null) {
					if (preList == null) {
						preList = preList1;
					} else {
						preList.addAll(preList1);
					}
				}
			}
		}
		Long count = 0L;
		for (ResPrefecture prb : preList) {
			prb.setPlateId(plateId);
			prb.setPlateNumber(plateNumber);
			prb.setChargeTime(prb.getChargeTime() + "分钟");
			prb.setChargePrice(prb.getChargePrice() + "元");
			count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prb.getId());
			if (count == null) {
				count = 0L;
			}
			prb.setLeisureStall(count.intValue());
			prb.setDistance(MapUtil.getDistance(prb.getLatitude(), prb.getLongitude(), new Double(rp.getLatitude()),
					new Double(rp.getLongitude())));
		}

		Map<Long, List<ResPrefecture>> map = preList.stream().collect(Collectors.groupingBy(ResPrefecture::getCityId));

		List<ResPreCity> resPreCityList = new ArrayList<ResPreCity>();
		ResPreCity resPreCity = null;
		for (Long cityId : map.keySet()) {
			resPreCity = new ResPreCity();
			resPreCity.setCityId(cityId);
			resPreCity.setCityName(cityMap.get(cityId));
			List<ResPrefecture> prefecturelist = map.get(cityId);
			Collections.sort(prefecturelist, new Comparator<ResPrefecture>() {
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

	public ResStallInfo findStallList2(ReqBooking reqBooking) {
		ResStallInfo stallInfo = new ResStallInfo();
		List<ResStall> stallList = new ArrayList<ResStall>();
		ResVechicleMark vehicleMark = vehicleMarkClient.findById(reqBooking.getPlateId());
		if (vehicleMark == null) {
			throw new BusinessException(StatusEnum.VALID_EXCEPTION);
		}
		String vehMark = vehicleMark.getVehMark(); // 车牌号
		log.info("vehicleMark = {}", JSON.toJSON(vehicleMark));
		if (!this.checkCarFree(vehicleMark.getVehMark())) {
			throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY); // 当前车牌号已在预约中，请更换车牌号重新预约
		}
		boolean assign = false;
		Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
		for (Object obj : set) {
			JSONObject json = JSON.parseObject(obj.toString());
			String vm = json.get("plate").toString(); // 车牌
			Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
			if (pid.longValue() == reqBooking.getPrefectureId().longValue() && vehMark.equals(vm)) { // 找到车区
				String lockSn = json.get("lockSn").toString();
				assign = true;
				log.info("use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
				break;
			}
		}
		Set<Object> lockSnList = this.redisService
				.members(RedisKey.PREFECTURE_FREE_STALL.key + reqBooking.getPrefectureId()); // 集合中所有成员元素
		if (CollectionUtils.isNotEmpty(lockSnList)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("list", lockSnList);
			ResStall resStall = null;
			List<cn.linkmore.prefecture.response.ResStall> freeStallList = stallClusterMapper.findFreeStallList(params);
			log.info("---------freeStallList = {}", JSON.toJSON(freeStallList));
			for (cn.linkmore.prefecture.response.ResStall stall : freeStallList) {
				resStall = new ResStall();
				resStall.setStallId(stall.getId());
				resStall.setLockSn(stall.getLockSn());
				resStall.setStallName(stall.getStallName());
				stallList.add(resStall);
			}
		}
		ResPrefectureDetail pre = this.prefectureClusterMapper.findById(reqBooking.getPrefectureId());
		if (pre != null) {
			stallInfo.setPreName(pre.getName());
			if (pre.getStatus() == 1) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位可用
			}
		}
		stallInfo.setAssignFlag(assign);
		stallInfo.setStalls(stallList);
		log.info("stallInfo = {}", JSON.toJSON(stallInfo));
		if (!assign) {
			if (CollectionUtils.isEmpty(stallList)) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位可用
			}
		}
		return stallInfo;
	}

	public static double mul(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	public cn.linkmore.prefecture.controller.app.response.ResPrefectureDetail findPreDetailById(Long preId,
			HttpServletRequest request) {
		cn.linkmore.prefecture.controller.app.response.ResPrefectureDetail detail = new cn.linkmore.prefecture.controller.app.response.ResPrefectureDetail();
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		ResPrefectureDetail preDetail = prefectureClusterMapper.findById(preId);
		String freeMins = "";
		String appFreeMins = "";
		String topFee = "0.0";
		if (preDetail != null) {
			detail.setId(preDetail.getId());
			detail.setAddress(preDetail.getAddress());
			detail.setName(preDetail.getName());
			detail.setLatitude(preDetail.getLatitude().doubleValue());
			detail.setLongitude(preDetail.getLongitude().doubleValue());
			detail.setBusinessTime(preDetail.getBusinessTime());
			Long count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + preDetail.getId());
			if (count == null) {
				count = 0L;
			}
			detail.setLeisureStall(count.intValue());
			/*StrategyBase strategyBase = strategyBaseClusterMapper.findById(preDetail.getStrategyId());
			if (strategyBase != null) {
				freeMins = strategyBase.getFreeMins().toString();
				if (strategyBase.getType() == 4) {
					Double topFee1 = strategyBase.getTopDaily() / strategyBase.getTimelyLong()
							* strategyBase.getBasePrice().doubleValue();
					detail.setTopFee(topFee1.toString());
				} else {
					detail.setTopFee("无");
				}
			}*/
			List<ResPrefectureGroup> preGroup = new ArrayList<ResPrefectureGroup>();
			ResPrefectureGroup group = null;
			Set<Object> lockSnList = this.redisService.members(RedisKey.PREFECTURE_FREE_STALL.key + preId);
			Map<Long, Set<Object>> map = new HashMap<Long, Set<Object>>();
			Set<Object> sns = null;
			List<StrategyGroupDetail> groupDetailList = strategyGroupDetailClusterMapper
					.findPreGroupDetailList(preDetail.getId());
			if (CollectionUtils.isNotEmpty(groupDetailList)) {
				for (StrategyGroupDetail groupDetail : groupDetailList) {
					Long preGroupId = groupDetail.getStrategyGroupId();
					if (lockSnList.contains(groupDetail.getLockSn()) && !this.redisService
							.exists(RedisKey.PREFECTURE_BUSY_STALL.key + groupDetail.getLockSn())) {
						sns = map.get(preGroupId);
						if (sns == null) {
							sns = new HashSet<>();
							map.put(preGroupId, sns);
						}
						sns.add(groupDetail.getLockSn());
					}
				}
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prefectureId", preDetail.getId());
			List<ResStrategyGroup> strategyGroupList = strategyGroupClusterMapper.findPreGroupList(param);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (CollectionUtils.isNotEmpty(strategyGroupList)) {
				for (ResStrategyGroup strategyGroup : strategyGroupList) {
					group = new ResPrefectureGroup();
					group.setGroupName(strategyGroup.getName());
					StringBuffer sb = new StringBuffer();
					Map<String, Object> paramFee = new HashMap<String, Object>();
					paramFee.put("strategGroupId", strategyGroup.getId());
					paramFee.put("searchDateTime", sdf.format(new Date()));
					String json = strategyFeeService.info(paramFee);
					log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>paramFee{} ,json{}", JSON.toJSON(paramFee), json);
					if (json != null) {
						JSONObject data = JSONObject.parseObject(json);
						if (data.getInteger("code") == 200) {
							JSONObject detailObj = JSONObject.parseObject(data.getString("detail"));
							freeMins = String.valueOf(detailObj.getInteger("free"));
							topFee = String.valueOf(detailObj.getBigDecimal("limitPrice"));
							JSONArray array = detailObj.getJSONArray("data");
							for (int i = 0; i < array.size(); i++) {
								String obj = array.getString(i);
								log.info("-------------------detail2{}", obj);
								JSONObject jsonObj = JSONObject.parseObject(obj);
								String beginTime = jsonObj.getString("beginTime");
								String endTime = jsonObj.getString("endTime");
								Double chargeFee = jsonObj.getBigDecimal("chargeFee").doubleValue();
								int chargeHourFree = jsonObj.getInteger("chargeHourFree");
								int criticalUnit = jsonObj.getInteger("criticalUnit");
								int chargeUnit = jsonObj.getInteger("chargeUnit");
								String remark = jsonObj.getString("remark");

								sb.append(beginTime + "-" + endTime + " ");
								if (chargeUnit == 1) {
									sb.append(chargeFee + "元/分");
								} else if (chargeUnit == 2) {
									sb.append(chargeFee + "元/时");
								} else if (chargeUnit == 3) {
									sb.append(chargeFee + "元/次");
								}
								
								if (criticalUnit == 1) {
									sb.append("\t\r\n");
									sb.append("每" +chargeHourFree+ "分钟为一个计价单位");
									//sb.append(div(mul(chargeFee, chargeHourFree), 60, 2) + "/" + chargeHourFree + "分钟");
								} else if (criticalUnit == 2) {
									sb.append("\t\r\n");
									sb.append("每" +chargeHourFree+ "小时为一个计价单位");
									//sb.append(div(mul(chargeFee, chargeHourFree), 60, 2) + "/" + chargeHourFree + "小时");
								}
								sb.append("\t\r\n");
								if (StringUtils.isNotBlank(remark)) {
									sb.append(remark);
									sb.append("\t\r\n");
								}
							}
							sb.deleteCharAt(sb.length() - 3);
						}
						log.info("-------------------调用结果{} 免费时长{} 封顶计费{} 描述{}", data, freeMins, topFee, sb.toString());
					}
					group.setDesc(sb.toString());
					// group.setDesc(preDetail.getStrategyDescription());
					group.setGroupId(strategyGroup.getId());
					group.setPreId(preDetail.getId());
					if (map.get(strategyGroup.getId()) == null) {
						group.setLeisureStall(0);
					} else {
						group.setLeisureStall(map.get(strategyGroup.getId()).size());
					}
					group.setStatus(strategyGroup.getStatus().intValue());
					preGroup.add(group);
				}
			}
			if(topFee.equals("0.0")) {
				detail.setTopFee("无");
			}else {
				detail.setTopFee((Double.valueOf(topFee)).intValue() + "元");
			}
			if(StringUtils.isNotBlank(freeMins)) {
				if(Integer.valueOf(freeMins)>= 60) {
					appFreeMins = div(Double.valueOf(freeMins), 60D, 2) +"小时";
				}else {
					appFreeMins = freeMins +"分钟";
				}
			}
			detail.setFreeMins(appFreeMins);
			detail.setPreGroupList(preGroup);
			Long plateId = null;
			String plateNumber = null;
			if (cu != null && cu.getId() != null) {
				ResUserOrder ro = this.orderClient.last(cu.getId());
				List<ResVechicleMark> plates = this.vehicleMarkClient.list(cu.getId());
				if (ro != null) {
					Map<String, Long> plateMap = new HashMap<String, Long>();
					for (ResVechicleMark rvm : plates) {
						plateMap.put(rvm.getVehMark(), rvm.getId());
					}
					plateNumber = ro.getPlateNo();
					plateId = plateMap.get(plateNumber);
					if (plateId == null) {
						plateNumber = null;
					}
				}
				if (plateNumber == null && CollectionUtils.isNotEmpty(plates)) {
					plateId = plates.get(0).getId();
					plateNumber = plates.get(0).getVehMark();
				}
			}
			detail.setPlateId(plateId);
			detail.setPlateNumber(plateNumber);
		}
		return detail;
	}

	public ResStallInfo findStallList(ReqBooking reqBooking) {
		ResStallInfo stallInfo = new ResStallInfo();
		List<ResStall> stallList = new ArrayList<ResStall>();
		ResVechicleMark vehicleMark = vehicleMarkClient.findById(reqBooking.getPlateId());
		if (vehicleMark == null) {
			throw new BusinessException(StatusEnum.VALID_EXCEPTION);
		}
		String vehMark = vehicleMark.getVehMark(); // 车牌号
		log.info("vehicleMark = {}", JSON.toJSON(vehicleMark));
		if (!this.checkCarFree(vehicleMark.getVehMark())) {
			throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY); // 当前车牌号已在预约中，请更换车牌号重新预约
		}
		boolean assign = false;
		List<ResLockInfo> lbs = null;
		Set<Object> lockSnList = new HashSet<Object>();
		ResPrefectureDetail pre = this.prefectureClusterMapper.findById(reqBooking.getPrefectureId());
		if (pre != null && StringUtils.isNotBlank(pre.getGateway())) {
			stallInfo.setPreName(pre.getName());
			if (pre.getStatus() == 1) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位可用
			}
			if (pre.getCategory() == 2) {
				// 共享车位逻辑
				lbs = this.lockTools.lockListByGroupCode(pre.getGateway());
				log.info("share pre rm = {}", JsonUtil.toJson(lbs));
				if (lbs != null && lbs.size() != 0) {
					for (ResLockInfo lb : lbs) {
						if (lb.getLockState() == LockStatus.DOWN.status && lb.getParkingState() == 0) {
							lockSnList.add(lb.getLockCode());
						}
					}
				}
				log.info("share pre lockSnList = {}", JsonUtil.toJson(lockSnList));
			} else {
				Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
				for (Object obj : set) {
					JSONObject json = JSON.parseObject(obj.toString());
					String vm = json.get("plate").toString(); // 车牌
					Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
					if (pid.longValue() == reqBooking.getPrefectureId().longValue() && vehMark.equals(vm)) { // 找到车区
						String lockSn = json.get("lockSn").toString();
						assign = true;
						log.info("use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
						break;
					}
				}
				lockSnList = this.redisService
						.members(RedisKey.PREFECTURE_FREE_STALL.key + reqBooking.getPrefectureId()); // 集合中所有成员元素
				log.info("common pre lockSnList = {}", JsonUtil.toJson(lockSnList));
			}
		}
		if (CollectionUtils.isNotEmpty(lockSnList)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("list", lockSnList);
			if (reqBooking.getGroupId() != 0L) {
				List<StrategyGroupDetail> findList = strategyGroupDetailClusterMapper.findList(reqBooking.getGroupId());
				List<Long> stallIds = new ArrayList<Long>();
				for (StrategyGroupDetail detail : findList) {
					stallIds.add(detail.getStallId());
				}
				params.put("stallList", stallIds);
			}

			ResStall resStall = null;
			List<cn.linkmore.prefecture.response.ResStall> freeStallList = stallClusterMapper.findFreeStallList(params);
			log.info(">>>>>>>>>>>>preGroupId = {} freeStallList = {}", reqBooking.getGroupId(),
					JSON.toJSON(freeStallList));
			for (cn.linkmore.prefecture.response.ResStall stall : freeStallList) {
				resStall = new ResStall();
				resStall.setStallId(stall.getId());
				resStall.setLockSn(stall.getLockSn());
				resStall.setStallName(stall.getStallName());
				stallList.add(resStall);
			}
		}
		stallInfo.setAssignFlag(assign);
		stallInfo.setStalls(stallList);
		log.info("stallInfo = {}", JSON.toJSON(stallInfo));
		if (!assign) {
			if (CollectionUtils.isEmpty(stallList)) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位可用
			}
		}
		return stallInfo;
	}

	@Override
	public List<ResPrefecture> nearList(ReqNearPrefecture rp, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Double raidusMile = 15000D;
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if(rp.getCityId() == 0L) {
			paramMap = MapDistance.getAround(Double.valueOf(rp.getSearchLatitude()), Double.valueOf(rp.getSearchLongitude()), raidusMile);
			log.info("get the around = {}",JSON.toJSON(paramMap));
		}else {
			paramMap.put("cityId", rp.getCityId());
		}
		paramMap.put("status", 0);
		log.info("param = {}",JSON.toJSON(paramMap));
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		Long plateId = null;
		String plateNumber = null;
		if (cu != null && cu.getId() != null) {
			ResUserOrder ro = this.orderClient.last(cu.getId());
			List<ResVechicleMark> plates = this.vehicleMarkClient.list(cu.getId());
			if (ro != null) {
				Map<String, Long> plateMap = new HashMap<String, Long>();
				for (ResVechicleMark rvm : plates) {
					plateMap.put(rvm.getVehMark(), rvm.getId());
				}
				plateNumber = ro.getPlateNo();
				plateId = plateMap.get(plateNumber);
				if (plateId == null) {
					plateNumber = null;
				}
			}
			if (plateNumber == null && CollectionUtils.isNotEmpty(plates)) {
				plateId = plates.get(0).getId();
				plateNumber = plates.get(0).getVehMark();
			}

			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if (us != null && us.getStatus().intValue() == UserStaffStatus.ON.status) {
				List<ResPrefecture> preList1 = prefectureClusterMapper.findPreByStatusAndGPS1(paramMap);
				if (preList1 != null) {
					if (preList == null) {
						preList = preList1;
					} else {
						preList.addAll(preList1);
					}
				}
			}
		}
		Long count = 0L;
		for (ResPrefecture prb : preList) {
			prb.setPlateId(plateId);
			prb.setPlateNumber(plateNumber);
			prb.setChargeTime(prb.getChargeTime() + "分钟");
			prb.setChargePrice(prb.getChargePrice() + "元");
			count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prb.getId());
			if (count == null) {
				count = 0L;
			}
			prb.setLeisureStall(count.intValue());
			prb.setDistance(MapUtil.getDistance(prb.getLatitude(), prb.getLongitude(), new Double(rp.getCurrentLatitude()),
					new Double(rp.getCurrentLongitude())));
		}
		
		Collections.sort(preList, new Comparator<ResPrefecture>() {
			public int compare(ResPrefecture pre1, ResPrefecture pre2) {
				return Double.valueOf(pre1.getDistance()).compareTo(Double.valueOf(pre2.getDistance()));
			}
		});
		return preList;
	}

}
