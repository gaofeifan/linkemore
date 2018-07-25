package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.UserStaffStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.request.ReqBrandPre;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPre;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreCity;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreLeisure;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreStrategy;
import cn.linkmore.enterprise.dao.cluster.EntBrandPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandPreMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandPre;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandPreStall;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.enterprise.service.EntBrandPreService;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.response.ResStrategyBase;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.MapUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * 品牌车区
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandPreServiceImpl implements EntBrandPreService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private EntBrandPreMasterMapper entBrandPreMasterMapper;

	@Resource
	private EntBrandPreClusterMapper entBrandPreClusterMapper;
	
	@Resource
	private EntBrandStallClusterMapper entBrandStallClusterMapper;

	@Resource
	private EntBrandUserClusterMapper entBrandUserClusterMapper;

	@Resource
	private StrategyBaseClient strategyBaseClient;

	@Resource
	private OrderClient orderClient;

	@Autowired
	private VehicleMarkClient vehicleMarkClient;

	@Autowired
	private UserStaffClient userStaffClient;

	@Resource
	private RedisService redisService;

	@Override
	public List<ResEntBrandPreCity> list(ReqBrandPre rp, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", 0);
		// 此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResEntBrandPre> preList = entBrandPreClusterMapper.findBrandPre(paramMap);
		Long plateId = null;
		String plateNumber = null;
		List<Long> entIds  = null;
		if (cu != null && cu.getId() != null) {  
			entIds = entBrandUserClusterMapper.findUserEntList(cu.getId()); 
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

			// 员工用户车区列表
			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if (us != null && us.getStatus().intValue() == UserStaffStatus.ON.status) {
				List<ResEntBrandPre> preList1 = entBrandPreClusterMapper.findStaffBrandPre(paramMap);
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
		Long linkmoreCount = 0L;
		for (ResEntBrandPre ebp : preList) {
			ebp.setPlateId(plateId);
			ebp.setPlateNumber(plateNumber);
			ebp.setChargeTime(ebp.getChargeTime() + "分钟");
			ebp.setChargePrice(ebp.getChargePrice() + "元");
			count = this.redisService.size(RedisKey.PREFECTURE_BRAND_FREE_STALL.key +ebp.getId());
			linkmoreCount = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + ebp.getPreId());
			log.info("count {} linkmoreCount {}", count, linkmoreCount);
			if (count == null) {
				count = 0L;
			}
			if (linkmoreCount == null) {
				linkmoreCount = 0L;
			}
			if (entIds!=null&&entIds.contains(ebp.getEntId())) {
				ebp.setBrandUserFlag(true);
			}else {
				ebp.setBrandUserFlag(false);
			}

			ebp.setLeisureStall(count.intValue());
			ebp.setLinkmoreLeisureStall(linkmoreCount.intValue());
			ebp.setDistance(MapUtil.getDistance(ebp.getLatitude(), ebp.getLongitude(), new Double(rp.getLatitude()),
					new Double(rp.getLongitude())));
		}

		Map<Long, List<ResEntBrandPre>> map = preList.stream()
				.collect(Collectors.groupingBy(ResEntBrandPre::getCityId));

		List<ResEntBrandPreCity> resPreCityList = new ArrayList<ResEntBrandPreCity>();
		ResEntBrandPreCity resPreCity = null;
		for (Long cityId : map.keySet()) {
			resPreCity = new ResEntBrandPreCity();
			resPreCity.setCityId(cityId);
			List<ResEntBrandPre> prefecturelist = map.get(cityId);
			resPreCity.setPrefectures(prefecturelist);
			resPreCityList.add(resPreCity);
			log.info("cityId:{},city pre list size:{}", cityId, prefecturelist.size());
		}
		return resPreCityList;
	}

	@Override
	public ResEntBrandPreStrategy findPreStrategy(Long brandPreId) {
		String mins = "分钟";
		String freetime = "免费时长";
		ResEntBrandPreStrategy bean = null;
		ResBrandPre brandPre = entBrandPreClusterMapper.findById(brandPreId);
		if (brandPre != null) {
			bean = new ResEntBrandPreStrategy();
			ResStrategyBase strategyBase = strategyBaseClient.findById(brandPre.getStrategyId());
			bean.setFreeMins(freetime + strategyBase.getFreeMins() + " " + mins);
			bean.setContent(brandPre.getStrategyDescription());
		}
		return bean;
	}

	@Override
	public List<ResEntBrandPreLeisure> getStallCount(HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", 0);
		// 此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResEntBrandPre> preList = entBrandPreClusterMapper.findBrandPre(paramMap);
		List<Long> entIds  = null;
		if (cu != null && cu.getId() != null) {  
			entIds = entBrandUserClusterMapper.findUserEntList(cu.getId()); 
			
			ResUserStaff us = this.userStaffClient.findById(cu.getId());
			if (us != null && us.getStatus().intValue() == UserStaffStatus.ON.status) {
				List<ResEntBrandPre> preList1 = entBrandPreClusterMapper.findStaffBrandPre(paramMap);
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
		List<ResEntBrandPreLeisure> list = new ArrayList<ResEntBrandPreLeisure>();
		ResEntBrandPreLeisure pre = null;
		Long count = 0L;
		Long linkmoreCount = 0L;
		if (CollectionUtils.isNotEmpty(preList)) {
			for (ResEntBrandPre resPre : preList) {
				pre = new ResEntBrandPreLeisure();
				pre.setId(resPre.getId());
				count = this.redisService
						.size(RedisKey.PREFECTURE_BRAND_FREE_STALL.key  + pre.getId());
				log.info("brandId {} free stall count {}", resPre.getId(), count);
				linkmoreCount = this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + resPre.getPreId());
				log.info("preId {} free stall count {}", resPre.getId(), linkmoreCount); 
				pre.setLeisureStall(count.intValue());
				pre.setLinkmoreLeisureStall(linkmoreCount.intValue()); 
				if(entIds!=null&&entIds.contains(resPre.getEntId())) {
					pre.setBrandUserFlag(true);
				}else {
					pre.setBrandUserFlag(false);
				}
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
		Integer count = this.entBrandPreClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBrandPre> list = this.entBrandPreClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<EntBrandPre> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(ReqEntBrandPre record) {
		EntBrandPre entBrandPre = null;
		entBrandPre = ObjectUtils.copyObject(record, new EntBrandPre());
		entBrandPre.setCreateTime(new Date());
		entBrandPre.setUpdateTime(new Date());
		return entBrandPreMasterMapper.save(entBrandPre);
	}

	@Override
	public int update(ReqEntBrandPre record) {
		EntBrandPre entBrandPre = null;
		entBrandPre = ObjectUtils.copyObject(record, new EntBrandPre());
		return entBrandPreMasterMapper.update(entBrandPre);
	}

	@Override
	public int delete(Long id) {
		return entBrandPreMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandPreClusterMapper.check(param);
	}

	@Override
	public List<ResBrandPreStall> preStallList() {
		List<ResBrandPreStall> preStallList = this.entBrandPreClusterMapper.findList();
		for(ResBrandPreStall preStall : preStallList) {
			List<ResBrandStall> brandStalls = this.entBrandStallClusterMapper.findByBrandPreId(preStall.getId());
			preStall.setStallList(brandStalls);
		}
		return preStallList;
	}

	@Override
	public ResBrandPre findById(Long id) {
		return this.entBrandPreClusterMapper.findById(id);
	}

	@Override
	public int delete(List<Long> ids) {
		return entBrandPreMasterMapper.deleteByIds(ids);
	}
}
