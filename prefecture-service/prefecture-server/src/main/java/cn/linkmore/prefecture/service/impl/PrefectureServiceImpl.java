package cn.linkmore.prefecture.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
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
import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.OrderStatus;
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
import cn.linkmore.prefecture.controller.app.response.ResAppointGroupDetail;
import cn.linkmore.prefecture.controller.app.response.ResGroupStrategy;
import cn.linkmore.prefecture.controller.app.response.ResPreCity;
import cn.linkmore.prefecture.controller.app.response.ResPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureGroup;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureList;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.controller.app.response.ResStall;
import cn.linkmore.prefecture.controller.app.response.ResStallInfo;
import cn.linkmore.prefecture.controller.staff.response.ResGateway;
import cn.linkmore.prefecture.controller.staff.response.ResStallLock;
import cn.linkmore.prefecture.core.lock.LockFactory;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.PrefectureElementClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyGroupClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyGroupDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureMasterMapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.PrefectureElement;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.entity.StrategyGroupDetail;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResGatewayDetails;
import cn.linkmore.prefecture.response.ResGatewayGroup;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLocksGateway;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.prefecture.service.StrategyFeeService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.LocateClient;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.StaffUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.MapDistance;
import cn.linkmore.util.MapUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 - 车区信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {
	private UserFactory appUserFactory = AppUserFactory.getInstance();
	private UserFactory staffUserFactory = StaffUserFactory.getInstance();
	@Autowired
	private StallService stallService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private LockFactory lockFactory = LockFactory.getInstance();
	private static final String formatStr = "HH:mm";
	
	private static SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
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
	
	@Autowired
	private PrefectureElementClusterMapper prefectureElementClusterMapper;

	@Autowired
	private LocateClient locateClient;
	
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
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
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
		List<ResPrefectureList> list = new ArrayList<ResPrefectureList>();
		ResPrefectureList pre = null;
		Long count = 0L;
		if (CollectionUtils.isNotEmpty(preList)) {
			for (ResPrefecture resPre : preList) {
				pre = new ResPrefectureList();
				pre.setId(resPre.getId());
				count = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + resPre.getId());
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
		String string = this.lockFactory.getLock(null).saveGroup(pre.getName());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(string)) {
			pre.setGateway(string);
		}
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
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<Long, ResCity> cityMap = new HashMap<Long, ResCity>();
		paramMap.put("status", 0);
		// 此处cityFlag=0，返回所有的车区信息
		if ("1".equals(rp.getCityFlag())) {
			paramMap.put("cityId", rp.getCityId());
		}
		if(StringUtils.isNotBlank(rp.getPreName())) {
			paramMap.put("name", '%'+ rp.getPreName()+ '%');
		}
		log.info("..........pre list param = {}",JSON.toJSON(paramMap));
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		List<ResCity> cityList = cityClient.findSelectList();
		
		cn.linkmore.third.response.ResLocate info = this.locateClient.get(rp.getLongitude(),rp.getLatitude());
		log.info("prefecture list locate info = {}",JSON.toJSON(info));
		
		if(CollectionUtils.isNotEmpty(cityList)) {
			for(ResCity city: cityList) {
				if(info!=null&&info.getAdcode()!=null) {
					if(info.getAdcode().substring(0, 4).equals(city.getCode().substring(0,4))) {
						city.setStatus(1);
					}
				}
				city.setDistance(MapUtil.getDistance(Double.valueOf(city.getLatitude()), Double.valueOf(city.getLongitude()), new Double(rp.getLatitude()),
						new Double(rp.getLongitude())));
			}
			
			Collections.sort(cityList, new Comparator<ResCity>() {
				public int compare(ResCity city1, ResCity city2) {
					return Double.valueOf(city1.getDistance()).compareTo(Double.valueOf(city2.getDistance()));
				}
			});
			log.info("process city list = {}", JSON.toJSON(cityList));
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
			if(prb.getRegion()!= null && prb.getRegion().contains("地面")) {
				prb.setRegion1("地面");
			}
			if(prb.getRegion()!= null && prb.getRegion().contains("地下")) {
				prb.setRegion2("地下");
			}
			
			String freeMins = "";
			String appFreeMins = "";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prefectureId", prb.getId());
			List<ResStrategyGroup> strategyGroupList = strategyGroupClusterMapper.findPreGroupList(param);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (CollectionUtils.isNotEmpty(strategyGroupList)) {
				for (ResStrategyGroup strategyGroup : strategyGroupList) {
					Map<String, Object> paramFee = new HashMap<String, Object>();
					paramFee.put("strategGroupId", strategyGroup.getId());
					paramFee.put("searchDateTime", sdf.format(new Date()));
					String json = strategyFeeService.info(paramFee);
					log.info("..........pre detail param fee{},json{}", JSON.toJSON(paramFee), json);
					if (json != null) {
						JSONObject data = JSONObject.parseObject(json);
						if (data.getInteger("code") == 200) {
							if(data.getString("detail") != null) {
								JSONObject detailObj = JSONObject.parseObject(data.getString("detail"));
								freeMins = String.valueOf(detailObj.getInteger("free"));
							}
						}
					}
				}
			}
			if(StringUtils.isNotBlank(freeMins)) {
				if(Integer.valueOf(freeMins)>= 60) {
					appFreeMins = "免费" + div(Double.valueOf(freeMins), 60D, 2) +"小时";
				}else {
					appFreeMins = "免费" + freeMins +"分钟";
				}
			}
			prb.setFreeMins(appFreeMins);
		}

		Map<Long, List<ResPrefecture>> map = preList.stream().collect(Collectors.groupingBy(ResPrefecture::getCityId));

		List<ResPreCity> resPreCityList = new ArrayList<ResPreCity>();
		ResPreCity resPreCity = null;
		
		for(ResCity city : cityList) {
			if(CollectionUtils.isNotEmpty(map.get(city.getId()))) {
				resPreCity = new ResPreCity();
				resPreCity.setCityId(city.getId());
				resPreCity.setCityName(city.getCityName());
				resPreCity.setStatus(city.getStatus());
				List<ResPrefecture> prefecturelist = map.get(city.getId());
				Collections.sort(prefecturelist, new Comparator<ResPrefecture>() {
					public int compare(ResPrefecture pre1, ResPrefecture pre2) {
						return Double.valueOf(pre1.getDistance()).compareTo(Double.valueOf(pre2.getDistance()));
					}
				});

				resPreCity.setPrefectures(prefecturelist);
				resPreCityList.add(resPreCity);
			}
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
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
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
			if(preDetail.getRegion()!= null && preDetail.getRegion().contains("地面")) {
				detail.setRegion1("地面");
			}
			if(preDetail.getRegion()!= null && preDetail.getRegion().contains("地下")) {
				detail.setRegion2("地下");
			}
			detail.setTotalStallNum(preDetail.getTotalStallNum());
			detail.setUnderLayer(preDetail.getUnderLayer());
			detail.setPreType(preDetail.getPreType());
			
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
			log.info("..........find pre detail free map{}", JSON.toJSON(map));
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
					log.info("..........pre detail param fee{},json{}", JSON.toJSON(paramFee), json);
					if (json != null) {
						JSONObject data = JSONObject.parseObject(json);
						if (data.getInteger("code") == 200) {
							if(data.getString("detail") != null) {
								JSONObject detailObj = JSONObject.parseObject(data.getString("detail"));
								freeMins = String.valueOf(detailObj.getInteger("free"));
								topFee = String.valueOf(detailObj.getBigDecimal("limitPrice"));
								JSONArray array = detailObj.getJSONArray("data");
								for (int i = 0; i < array.size(); i++) {
									String obj = array.getString(i);
									JSONObject jsonObj = JSONObject.parseObject(obj);
									String beginTime = jsonObj.getString("beginTime");
									String endTime = jsonObj.getString("endTime");
									Double chargeFee = jsonObj.getBigDecimal("chargeFee").doubleValue();
									int chargeHourFree = 0;
									if(jsonObj.getString("chargeHourFree") != null) {
										chargeHourFree = jsonObj.getInteger("chargeHourFree");
									}
									
									int chargeUnit = jsonObj.getInteger("chargeUnit");
									String remark = jsonObj.getString("remark");
									log.info("charge hour free = {} remark ={}", chargeHourFree, remark);
									sb.append(beginTime + "-" + endTime + " ");
									if (chargeUnit == 1) {
										sb.append(chargeFee + "元/分");
									} else if (chargeUnit == 2) {
										sb.append(chargeFee + "元/时");
									} else if (chargeUnit == 3) {
										sb.append(chargeFee + "元/次");
									}
									
									sb.append("\t\r\n");
									if (StringUtils.isNotBlank(remark)) {
										sb.append(remark);
										sb.append("\t\r\n");
									}
								}
							}
						}
						log.info("..........pre detail 调用结果{} 免费时长{} 封顶计费{} 描述{}", data, freeMins, topFee, sb.toString());
					}
					if(sb.length() > 3) {
						group.setDesc(sb.toString().substring(0, sb.length()-3));
					}
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
		log.info(".........stall list  vehicle mark = {}", JSON.toJSON(vehicleMark));
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
				log.info(".........stall list share pre rm = {}", JsonUtil.toJson(lbs));
				if (lbs != null && lbs.size() != 0) {
					for (ResLockInfo lb : lbs) {
						if (lb.getLockState() == LockStatus.DOWN.status && lb.getParkingState() == 0) {
							lockSnList.add(lb.getLockCode());
						}
					}
				}
				log.info(".........stall list share pre lockSnList = {}", JsonUtil.toJson(lockSnList));
			} else {
				Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
				for (Object obj : set) {
					JSONObject json = JSON.parseObject(obj.toString());
					String vm = json.get("plate").toString(); // 车牌
					Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
					if (pid.longValue() == reqBooking.getPrefectureId().longValue() && vehMark.equals(vm)) { // 找到车区
						String lockSn = json.get("lockSn").toString();
						assign = true;
						log.info(".........stall list use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
						break;
					}
				}
				lockSnList = this.redisService
						.members(RedisKey.PREFECTURE_FREE_STALL.key + reqBooking.getPrefectureId()); // 集合中所有成员元素
				log.info(".........stall list common pre lockSnList = {}", JsonUtil.toJson(lockSnList));
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
			log.info(".........stall list pre group id = {} freeStallList = {}", reqBooking.getGroupId(),
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
		log.info(".........stall list stall info = {}", JSON.toJSON(stallInfo));
		if (!assign) {
			if (CollectionUtils.isEmpty(stallList)) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位可用
			}
		}
		return stallInfo;
	}

	@Override
	public List<ResPrefecture> nearList(ReqNearPrefecture rp, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		Double raidusMile = 15000D;
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if(rp.getCityId() == 0L) {
			paramMap = MapDistance.getAround(Double.valueOf(rp.getSearchLatitude()), Double.valueOf(rp.getSearchLongitude()), raidusMile);
		}else {
			paramMap.put("cityId", rp.getCityId());
		}
		paramMap.put("status", 0);
		log.info("..........near list param = {}",JSON.toJSON(paramMap));
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		log.info("..........near list result = {}",JSON.toJSON(preList));
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

	@Override
	public ResGroupStrategy findGroupStrategy(Long groupId,HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResGroupStrategy groupStrategy = null;
		ResStrategyGroup group = strategyGroupClusterMapper.selectByPrimaryKey(groupId);
		List<String> descList = new ArrayList<String>();
		Set<Object> lockSnList = null;
		int count = 0;
		if(group != null) {
			lockSnList = this.redisService
					.members(RedisKey.PREFECTURE_FREE_STALL.key + group.getPrefectureId());
			ResPrefectureDetail preDetail = prefectureClusterMapper.findById(group.getPrefectureId());
			groupStrategy = new ResGroupStrategy();
			if(preDetail.getGridX() !=null ) {
				groupStrategy.setGridX(preDetail.getGridX());
				groupStrategy.setGridY(preDetail.getGridY());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String freeMins = "";
			String appFreeMins = "";
			String topFee = "0.0";
			StringBuffer sb = new StringBuffer();
			Map<String, Object> paramFee = new HashMap<String, Object>();
			paramFee.put("strategGroupId", groupId);
			paramFee.put("searchDateTime", sdf.format(new Date()));
			String json = strategyFeeService.info(paramFee);
			log.info("..........group strategy param fee{},json{}", JSON.toJSON(paramFee), json);
			if (json != null) {
				JSONObject data = JSONObject.parseObject(json);
				if (data.getInteger("code") == 200) {
					if(data.getString("detail")!= null) {
						JSONObject detailObj = JSONObject.parseObject(data.getString("detail"));
						freeMins = String.valueOf(detailObj.getInteger("free"));
						topFee = String.valueOf(detailObj.getBigDecimal("limitPrice"));
						JSONArray array = detailObj.getJSONArray("data");
						for (int i = 0; i < array.size(); i++) {
							String fee = "";
							String obj = array.getString(i);
							JSONObject jsonObj = JSONObject.parseObject(obj);
							String beginTime = jsonObj.getString("beginTime");
							String endTime = jsonObj.getString("endTime");
							Double chargeFee = jsonObj.getBigDecimal("chargeFee").doubleValue();
							int chargeHourFree = 0;
							if(jsonObj.getString("chargeHourFree") != null) {
								chargeHourFree = jsonObj.getInteger("chargeHourFree");
							}
							int chargeUnit = jsonObj.getInteger("chargeUnit");
							String remark = jsonObj.getString("remark");
							log.info("charge hour free = {} remark ={}", chargeHourFree, remark);
							sb.append(beginTime + "-" + endTime + " ");
							if (chargeUnit == 1) {
								fee = chargeFee + "元/分";
							} else if (chargeUnit == 2) {
								fee = chargeFee + "元/时";
							} else if (chargeUnit == 3) {
								fee = chargeFee + "元/次";
							}
							sb.append(fee);
							descList.add(beginTime + "-" + endTime + " "+ fee);
							try {
								if(isInZone(getLong(beginTime),getLong(endTime),getCurrentTime())){
									groupStrategy.setCurrentTimePeriod(beginTime + "-" + endTime);
									groupStrategy.setCurrentFee(fee);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							sb.append("\t\r\n");
							if (StringUtils.isNotBlank(remark)) {
								sb.append(remark);
								sb.append("\t\r\n");
							}
						}
					}
				}
				log.info("..........pre detail 调用结果{} 免费时长{} 封顶计费{} 描述{}", data, freeMins, topFee, sb.toString());
			}
			if(topFee.equals("0.0")) {
				groupStrategy.setTopFee("无");
			}else {
				groupStrategy.setTopFee((Double.valueOf(topFee)).intValue() + "元");
			}
			if(StringUtils.isNotBlank(freeMins)) {
				if(Integer.valueOf(freeMins)>= 60) {
					appFreeMins = div(Double.valueOf(freeMins), 60D, 2) +"小时";
				}else {
					appFreeMins = freeMins +"分钟";
				}
			}
			groupStrategy.setFreeMins(appFreeMins);
			if(preDetail.getBusinessTime()!=null) {
				groupStrategy.setBusinessTime(preDetail.getBusinessTime());
			}else {
				groupStrategy.setBusinessTime("00:00 - 24:00");
			}
			if(sb.length() > 3) {
				groupStrategy.setDesc(sb.toString().substring(0, sb.length()-3));
			}
			groupStrategy.setDescList(descList);
			groupStrategy.setGroupId(groupId);
			groupStrategy.setGroupName(group.getName());
			//获取上次使用车牌
			if (cu != null && cu.getId() != null) {
				Long plateId = null;
				String plateNumber = null;
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
				groupStrategy.setPlateId(plateId);
				groupStrategy.setPlateNumber(plateNumber);
			}
			//根据分组id查询下面所有车位列表信息
			List<StrategyGroupDetail> groupDetailList = strategyGroupDetailClusterMapper.findList(groupId);
			List<Long> stallIds = new ArrayList<Long>();
			if(CollectionUtils.isNotEmpty(groupDetailList)) {
				Map<String, Object> params = new HashMap<String, Object>();
				for (StrategyGroupDetail detail : groupDetailList) {
					stallIds.add(detail.getStallId());
				}
				params.put("list", stallIds);
				params.put("type", 0);
				List<ResStall> stallList = new ArrayList<ResStall>();
				ResStall resStall = null;
				List<cn.linkmore.prefecture.response.ResStall> groupStallList = stallClusterMapper.findPreStallList(params);
				log.info(".........stall list pre group id = {} freeLockSnList = {} groupStallList = {}", groupId, JSON.toJSON(lockSnList), JSON.toJSON(groupStallList));
				Map<String,Object> statuMap = new HashMap<String,Object>();
				for (cn.linkmore.prefecture.response.ResStall stall : groupStallList) {
					resStall = new ResStall();
					resStall.setStallId(stall.getId());
					if(stall.getStatus() == 1) {
						if(lockSnList.contains(stall.getLockSn())) {
							//空闲车位锁
							stall.setStatus(1);
						}else {
							stall.setStatus(2);
						}
					}
					resStall.setLockSn(stall.getLockSn());
					resStall.setStallName(stall.getStallName());
					stallList.add(resStall);
					statuMap.put(stall.getStallName().toUpperCase(), stall);
				}
				
				List<PrefectureElement> eleList = prefectureElementClusterMapper.findByPreId(group.getPrefectureId());
				List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
				Map<String,Object> paramMap = null;
				if(CollectionUtils.isNotEmpty(eleList)) {
					for(PrefectureElement ele: eleList) {
						paramMap = new HashMap<String,Object>();
						if("button".equals(ele.getEleType())) {
							if(statuMap.get(ele.getEleName().toUpperCase()) != null) {
								cn.linkmore.prefecture.response.ResStall stall = (cn.linkmore.prefecture.response.ResStall) statuMap.get(ele.getEleName().toUpperCase());
								paramMap.put("name", ele.getEleName());
								//此处需要根据车位锁实际状态优化
								paramMap.put("status", stall.getStatus());
								paramMap.put("stallId", stall.getId());
								paramMap.put("index", count);
								paramMap.put("lockSn", stall.getLockSn());
							}else {
								paramMap.put("name", ele.getEleName());
								paramMap.put("status", 4);
								paramMap.put("index", count);
								paramMap.put("stallId", 0L);
								paramMap.put("lockSn", "");
							}
						}else  if("img".equals(ele.getEleType())){
							if(StringUtils.isNotBlank(ele.getEleSrc())) {
								paramMap.put("src", ele.getEleSrc());
							}
						}
						paramMap.put("type", ele.getEleType());
						paramMap.put("x", ele.getEleX());
						paramMap.put("y", ele.getEleY());
						paramMap.put("width", ele.getEleWidth());
						paramMap.put("height", ele.getEleHeight());
						mapList.add(paramMap);
						count ++;
					}
				}
				log.info(".........parking data map = {}", JSON.toJSON(mapList));
				groupStrategy.setParkingDataMap(mapList);
			}
		}
		return groupStrategy;
	}
	
	private static boolean isInZone(long timeStart,long timeEnd,long nowTime) throws ParseException {
		Boolean flag = false;
		if(timeStart < timeEnd) {
			return timeStart <= nowTime && nowTime <= timeEnd;
		}else if(timeStart > timeEnd){
			if(nowTime > timeStart || nowTime < timeEnd ) {
				flag = true;
			}
		}
		return flag;
	}
	private static long getLong(String timeStr) throws ParseException {
		return sdf.parse(timeStr).getTime();
	}
	private static long getCurrentTime() throws ParseException {
		return getLong(sdf.format(new Date()));
	}

	@Override
	public Boolean checkPlate(Long plateId, HttpServletRequest request) {
		Boolean flag = false;
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if(cu != null && cu.getId() != null) {
			ResUserOrder ruo = this.orderClient.last(cu.getId());
			log.info("..........checkPlate ruo = {}",JSON.toJSON(ruo));
			if(ruo != null && (ruo.getStatus().intValue() == OrderStatus.UNPAID.value
					|| ruo.getStatus().intValue() == OrderStatus.SUSPENDED.value)) {
				throw new BusinessException(StatusEnum.ORDER_UNPAY_ORDER);
			}
			ResVechicleMark vehicleMark = vehicleMarkClient.findById(plateId);
			log.info("..........checkPlate vehicle mark = {}", JSON.toJSON(vehicleMark));
			if (vehicleMark == null) {
				throw new BusinessException(StatusEnum.VALID_EXCEPTION);
			}
			if (vehicleMark.getUserId().longValue() != cu.getId().longValue()) {
				throw new BusinessException(StatusEnum.VALID_EXCEPTION);
			}
			if (!this.checkCarFree(vehicleMark.getVehMark())) {
				throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY); // 当前车牌号已在预约中，请更换车牌号重新预约
			}
			flag = true;
		}else {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		return flag;
	}

	@Override
	public ResAppointGroupDetail findAppointGroupDetail(ReqBooking reqBooking, HttpServletRequest request) {
		ResAppointGroupDetail groupDetail = new ResAppointGroupDetail();
		ResGroupStrategy groupStrategy = new ResGroupStrategy();
		List<String> descList = new ArrayList<String>();
		Set<Object> lockSnList = new HashSet<Object>();
		List<ResStall> stallList = new ArrayList<ResStall>();
		ResStall resStall = null;
		Integer count = 0;
		ResVechicleMark vehicleMark = vehicleMarkClient.findById(reqBooking.getPlateId());
		if (vehicleMark == null) {
			throw new BusinessException(StatusEnum.VALID_EXCEPTION);
		}
		String vehMark = vehicleMark.getVehMark(); // 车牌号
		log.info(".........stall list  vehicle mark = {}", JSON.toJSON(vehicleMark));
		if (!this.checkCarFree(vehicleMark.getVehMark())) {
			throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY); // 当前车牌号已在预约中，请更换车牌号重新预约
		}
		boolean assign = false;
		ResStrategyGroup group = strategyGroupClusterMapper.selectByPrimaryKey(reqBooking.getGroupId());
		ResPrefectureDetail preDetail = this.prefectureClusterMapper.findById(reqBooking.getPrefectureId());
		if (preDetail != null && StringUtils.isNotBlank(preDetail.getGateway())) {
			if(preDetail.getGridX() !=null ) {
				groupStrategy.setGridX(preDetail.getGridX());
				groupStrategy.setGridY(preDetail.getGridY());
			}
			groupDetail.setPreName(preDetail.getName());
			if (preDetail.getStatus() == 1) {
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位可用
			}
			Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
			for (Object obj : set) {
				JSONObject json = JSON.parseObject(obj.toString());
				String vm = json.get("plate").toString(); // 车牌
				Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
				if (pid.longValue() == reqBooking.getPrefectureId().longValue() && vehMark.equals(vm)) { // 找到车区
					String lockSn = json.get("lockSn").toString();
					assign = true;
					log.info(".........stall list use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
					break;
				}
			}
			lockSnList = this.redisService
					.members(RedisKey.PREFECTURE_FREE_STALL.key + reqBooking.getPrefectureId()); // 集合中所有成员元素
			log.info(".........stall list common pre lockSnList = {}", JsonUtil.toJson(lockSnList));
		}
		if(group != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			List<StrategyGroupDetail> findList = strategyGroupDetailClusterMapper.findList(reqBooking.getGroupId());
			List<Long> stallIds = new ArrayList<Long>();
			for (StrategyGroupDetail detail : findList) {
				stallIds.add(detail.getStallId());
			}
			params.put("stallList", stallIds);
			List<cn.linkmore.prefecture.response.ResStall> groupStallList = stallClusterMapper.findAllStallList(params);
			/*if(CollectionUtils.isNotEmpty(groupStallList)) {
				for (cn.linkmore.prefecture.response.ResStall stall : groupStallList) {
					resStall = new ResStall();
					resStall.setStallId(stall.getId());
					resStall.setLockSn(stall.getLockSn());
					resStall.setStallName(stall.getStallName());
					if(stall.getStatus()!= null && stall.getStatus() == 1) {
						if(CollectionUtils.isNotEmpty(lockSnList) && lockSnList.contains(stall.getLockSn())) {
							//空闲车位锁
							stall.setStatus(1);
						}else {
							stall.setStatus(2);
						}
					}
					resStall.setStatus(stall.getStatus());
					stallList.add(resStall);
				} 
			}*/
			
			Map<String,Object> statuMap = new HashMap<String,Object>();
			for (cn.linkmore.prefecture.response.ResStall stall : groupStallList) {
				resStall = new ResStall();
				resStall.setStallId(stall.getId());
				if(stall.getStatus() == 1) {
					if(lockSnList.contains(stall.getLockSn())) {
						//空闲车位锁
						stall.setStatus(1);
					}else {
						stall.setStatus(2);
					}
				}
				resStall.setLockSn(stall.getLockSn());
				resStall.setStallName(stall.getStallName());
				stallList.add(resStall);
				statuMap.put(stall.getStallName(), stall);
			}
			
			List<PrefectureElement> eleList = prefectureElementClusterMapper.findByPreId(group.getPrefectureId());
			List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
			Map<String,Object> paramMap = null;
			if(CollectionUtils.isNotEmpty(eleList)) {
				for(PrefectureElement ele: eleList) {
					paramMap = new HashMap<String,Object>();
					if("button".equals(ele.getEleType())) {
						if(statuMap.get(ele.getEleName()) != null) {
							cn.linkmore.prefecture.response.ResStall stall = (cn.linkmore.prefecture.response.ResStall) statuMap.get(ele.getEleName());
							paramMap.put("name", ele.getEleName());
							//此处需要根据车位锁实际状态优化
							paramMap.put("status", stall.getStatus());
							paramMap.put("stallId", stall.getId());
							paramMap.put("index", count);
							paramMap.put("lockSn", stall.getLockSn());
						}else {
							paramMap.put("name", ele.getEleName());
							paramMap.put("status", 4);
							paramMap.put("index", count);
							paramMap.put("stallId", 0L);
							paramMap.put("lockSn", "");
						}
					}else  if("img".equals(ele.getEleType())){
						if(StringUtils.isNotBlank(ele.getEleSrc())) {
							paramMap.put("src", ele.getEleSrc());
						}
					}
					paramMap.put("type", ele.getEleType());
					paramMap.put("x", ele.getEleX());
					paramMap.put("y", ele.getEleY());
					paramMap.put("width", ele.getEleWidth());
					paramMap.put("height", ele.getEleHeight());
					mapList.add(paramMap);
					count ++;
				}
			}
			log.info(".........parking data map = {}", JSON.toJSON(mapList));
			groupStrategy.setParkingDataMap(mapList);
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String freeMins = "";
			String appFreeMins = "";
			String topFee = "0.0";
			StringBuffer sb = new StringBuffer();
			Map<String, Object> paramFee = new HashMap<String, Object>();
			paramFee.put("strategGroupId", reqBooking.getGroupId());
			paramFee.put("searchDateTime", sdf.format(new Date()));
			String json = strategyFeeService.info(paramFee);
			log.info("..........group strategy param fee{},json{}", JSON.toJSON(paramFee), json);
			if (json != null) {
				JSONObject data = JSONObject.parseObject(json);
				if (data.getInteger("code") == 200) {
					if(data.getString("detail")!= null) {
						JSONObject detailObj = JSONObject.parseObject(data.getString("detail"));
						freeMins = String.valueOf(detailObj.getInteger("free"));
						topFee = String.valueOf(detailObj.getBigDecimal("limitPrice"));
						JSONArray array = detailObj.getJSONArray("data");
						for (int i = 0; i < array.size(); i++) {
							String fee = "";
							String obj = array.getString(i);
							JSONObject jsonObj = JSONObject.parseObject(obj);
							String beginTime = jsonObj.getString("beginTime");
							String endTime = jsonObj.getString("endTime");
							Double chargeFee = jsonObj.getBigDecimal("chargeFee").doubleValue();
							int chargeHourFree = 0;
							if(jsonObj.getString("chargeHourFree") != null) {
								chargeHourFree = jsonObj.getInteger("chargeHourFree");
							}
							
							int chargeUnit = jsonObj.getInteger("chargeUnit");
							String remark = jsonObj.getString("remark");
							sb.append(beginTime + "-" + endTime + " ");
							if (chargeUnit == 1) {
								fee = chargeFee + "元/分";
							} else if (chargeUnit == 2) {
								fee = chargeFee + "元/时";
							} else if (chargeUnit == 3) {
								fee = chargeFee + "元/次";
							}
							sb.append(fee);
							descList.add(beginTime + "-" + endTime + " "+ fee);
							try {
								if(isInZone(getLong(beginTime),getLong(endTime),getCurrentTime())){
									groupStrategy.setCurrentTimePeriod(beginTime + "-" + endTime);
									groupStrategy.setCurrentFee(fee);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							sb.append("\t\r\n");
							if (StringUtils.isNotBlank(remark)) {
								sb.append(remark);
								sb.append("\t\r\n");
							}
						}
					}
				}
				log.info("..........pre detail 调用结果{} 免费时长{} 封顶计费{} 描述{}", data, freeMins, topFee, sb.toString());
			}
			if(topFee.equals("0.0")) {
				groupStrategy.setTopFee("无");
			}else {
				groupStrategy.setTopFee((Double.valueOf(topFee)).intValue() + "元");
			}
			if(StringUtils.isNotBlank(freeMins)) {
				if(Integer.valueOf(freeMins)>= 60) {
					appFreeMins = div(Double.valueOf(freeMins), 60D, 2) +"小时";
				}else {
					appFreeMins = freeMins +"分钟";
				}
			}
			groupStrategy.setFreeMins(appFreeMins);
			if(preDetail.getBusinessTime()!=null) {
				groupStrategy.setBusinessTime(preDetail.getBusinessTime());
			}else {
				groupStrategy.setBusinessTime("00:00 - 24:00");
			}
			if(sb.length() > 3) {
				groupStrategy.setDesc(sb.toString().substring(0, sb.length()-3));
			}
			groupStrategy.setDescList(descList);
			groupStrategy.setGroupId(group.getId());
			groupStrategy.setGroupName(group.getName());
			groupStrategy.setStallList(stallList);
		}
		groupDetail.setGroupStrategy(groupStrategy);
		groupDetail.setAssignFlag(assign);
		return groupDetail;
	}
	
	@Override
	public Boolean bindGroup(Long preId, String serialNumber, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(staffUserFactory.createTokenRedisKey(request));
		if(!stallService.checkStaffPreAuth(user.getId(), preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		ResPrefectureDetail detail = this.findById(preId);
		return lockFactory.getLock().bindGroup(detail.getGateway(), serialNumber);
	}
	
	@Override
	public Boolean unBindGroup(String groupCode, String serialNumber, HttpServletRequest request) {
		return lockFactory.getLock().unbindGroup(groupCode, serialNumber);
	}

	@Override
	public List<ResGateway> findGatewayGroup(Long preId, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(staffUserFactory.createTokenRedisKey(request));
		if(!stallService.checkStaffPreAuth(user.getId(), preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		ResPrefectureDetail detail = this.findById(preId);
		List<ResGatewayGroup> group = lockFactory.getLock().getGatewayGroup(detail.getGateway());
		List<ResGateway> gatewayList = new ArrayList<>();
		if(group != null) {
			for (ResGatewayGroup resGatewayGroup : group) {
				gatewayList.add(new ResGateway(resGatewayGroup.getSerialNumber(),resGatewayGroup.getGatewayState()));
			}
		}
		return gatewayList;
	}

	@Override
	public cn.linkmore.prefecture.controller.staff.response.ResGatewayDetails getGatewayDetails(String serialNumber, HttpServletRequest request) {
		ResGatewayDetails gatewayDetails = lockFactory.getLock().getGatewayDetails(serialNumber);
		cn.linkmore.prefecture.controller.staff.response.ResGatewayDetails details = new cn.linkmore.prefecture.controller.staff.response.ResGatewayDetails();
		details.setAgentName(gatewayDetails.getAgentName());
		details.setGroupCode(gatewayDetails.getGroupCode());
		details.setSerialNumber(gatewayDetails.getSerialNumber());
		details.setSimType(gatewayDetails.getSimType());
		details.setVersion(gatewayDetails.getGatewayVersion());
		details.setGatewayState(gatewayDetails.getGatewayState());
		List<ResLocksGateway> gateways = this.lockFactory.getLock().getLocksGateway(serialNumber);
		if(StringUtils.isNotBlank(gatewayDetails.getGroupCode())) {
			ResPrefectureDetail detail = this.prefectureClusterMapper.findByGateway(gatewayDetails.getGroupCode());
			if(detail != null) {
				details.setPreId(detail.getId());
				details.setPreName(detail.getName());
			}
		}
		if(gateways != null && gateways.size() != 0) {
			List<cn.linkmore.prefecture.response.ResStall> list = null;
			
			if(details.getPreId() != null) {
				Map<String, Object> param = new HashMap<>();
				param.put("preId", details.getPreId());
				list = this.stallService.findStallsByPreIds(param );
			}
			ResStallLock stallLock = null;
			for (ResLocksGateway gateway : gateways) {
				stallLock = new ResStallLock();
				stallLock.setBindFlag(gateway.getBindFlag());
				stallLock.setBattery(gateway.getElectricity());
				stallLock.setLockSn(gateway.getLockSerialNumber());
				stallLock.setSignal(gateway.getSignal());
				if(list != null) {
					String lockSn = gateway.getLockSerialNumber() != null ? gateway.getLockSerialNumber().substring(4).toUpperCase() : gateway.getLockSerialNumber();
					for (cn.linkmore.prefecture.response.ResStall resStallOps : list) {
						if(lockSn != null && lockSn.equals(resStallOps.getLockSn())) {
							stallLock.setStallId(resStallOps.getId());
							stallLock.setStallName(resStallOps.getStallName());
							break;
						}
					}
				}
				details.getStallLocks().add(stallLock);
			}
		}
		return details;
	}

	@Override
	public Boolean loadLock(HttpServletRequest request, String serialNumber) {
		return lockFactory.getLock().loadAllLock(serialNumber);
	}

	@Override
	public Boolean restartGateway(HttpServletRequest request, String serialNumber) {
		return lockFactory.getLock().restart(serialNumber);
	}

	@Override
	public Boolean unBindLock(String lockSn, String serialNumber, HttpServletRequest request) {
		return lockFactory.getLock().unBindLock(serialNumber, lockSn);
	}

	@Override
	public Boolean removeLock(String serialNumber, HttpServletRequest request) {
		return lockFactory.getLock().removeLock(serialNumber);
	}
	
	

}
