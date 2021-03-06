package cn.linkmore.enterprise.service.impl;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandAd;
import cn.linkmore.enterprise.dao.cluster.EntBrandAdClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandApplicantClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandAdMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandAd;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.service.EntBrandAdService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * 品牌广告
 * 
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class EntBrandAdServiceImpl implements EntBrandAdService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserFactory appUserFactory = AppUserFactory.getInstance();
	
	@Resource
	private EntBrandAdMasterMapper entBrandAdMasterMapper;

	@Resource
	private EntBrandAdClusterMapper entBrandAdClusterMapper;

	@Resource
	private EntBrandApplicantClusterMapper entBrandApplicantClusterMapper;

	@Resource
	private EntBrandUserClusterMapper entBrandUserClusterMapper;

	@Resource
	private EntBrandPreClusterMapper entBrandPreClusterMapper;

	@Autowired
	private RedisService redisService;

	@Autowired
	private CouponClient couponClient;

	@Resource
	private CityClient cityClient;

	@Override
	public ResEntBrandAd findBrandAdScreen(Long cityId, HttpServletRequest request) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDay = sdf.format(date);
		int count = 0;

		ResEntBrandAd resEntBrandAd = null;
		ResBrandAd resBrandAd = null;
		List<ResBrandAd> list = this.entBrandAdClusterMapper.findScreenList();
		if (CollectionUtils.isNotEmpty(list)) {
			resBrandAd = list.get(0);
			if (resBrandAd.getCityIds().contains(cityId.toString())) {
				if (this.redisService
						.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay) != null) {
					count = (Integer) this.redisService
							.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay);
					log.info("opening brand pre send coupon ent-day{}{} count {}", resBrandAd.getEntId(), currentDay,
							count);
					// 计数次数 >= 日申请次数，当天广告失效
					if (resBrandAd.getApplyCount() <= count) {
						return null;
					}
				}
			} else {
				return null;
			}

			CacheUser cu = (CacheUser) this.redisService
					.get(appUserFactory.createTokenRedisKey( TokenUtil.getKey(request), request.getHeader("os")) );

			if (cu != null && cu.getId() != null) {
				// 当前用户是否已接受优惠券信息，接受之后则以后不在提示
				/*
				 * List<ResCoupon> couponList =
				 * couponClient.findBrandCouponList(resBrandAd.getEntId(), cu.getId()); if
				 * (CollectionUtils.isNotEmpty(couponList)) { return null; }
				 */
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entId", resBrandAd.getEntId());
				map.put("userId", cu.getId());
				log.info("entId={},userId={}", resBrandAd.getEntId(), cu.getId());
				int applyCount = entBrandApplicantClusterMapper.count(map);
				log.info("----------------applyCount = {}", applyCount);
				if (applyCount > 0) {
					return null;
				}
				Integer num = entBrandUserClusterMapper.findBrandUser(map);
				// 判断当前用户是否为授权用户若是直接发送优惠券 若不是收集用户信息申请品牌授权
				if (num > 0) {
					resBrandAd.setBrandUserFlag(true);
				}
			} else {
				// TODO 若用户未登陆则无法控制开屏是否显示，只能输入手机号后进行是否已领取品牌优惠券
				// 根据mobile 和 entId查询申请人记录表 若存在吐司 若不存在则新增申请人记录发放优惠券
			}
		}

		if (resBrandAd != null) {
			resEntBrandAd = ObjectUtils.copyObject(resBrandAd, new ResEntBrandAd());
			resEntBrandAd.setViewUrl(resBrandAd.getViewUrl() + "?companyId=" + resBrandAd.getEntId());
		}
		return resEntBrandAd;
	}

	@Override
	public boolean send(Long entId, HttpServletRequest request) {
		boolean flag = false;
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey( TokenUtil.getKey(request), request.getHeader("os")));
		if (cu != null && cu.getId() != null) {
			// 判断是否已经发送了品牌优惠
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("entId", entId);
			map.put("userId", cu.getId());
			log.info("-----send-----entId={},userId={}", entId, cu.getId());
			int applyCount = entBrandApplicantClusterMapper.count(map);
			if (applyCount == 0) {
				flag = this.couponClient.sendBrandCoupon(true, entId, cu.getId());
			}
		}
		return flag;
	}

	@Override
	public ResEntBrandAd findBrandPreAd(Long id, HttpServletRequest request) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDay = sdf.format(date);
		int count = 0;

		ResEntBrandAd resEntBrandAd = null;
		ResBrandAd resBrandAd = null;
		ResBrandPre brandPre = this.entBrandPreClusterMapper.findById(id);
		log.info("---------brandPre = {}", JSON.toJSON(brandPre));
		if (brandPre == null) {
			throw new BusinessException(StatusEnum.VALID_EXCEPTION);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("entId", brandPre.getEntId());
		param.put("screen", 0);
		List<ResBrandAd> list = this.entBrandAdClusterMapper.findBrandPreAdList(param);
		if (CollectionUtils.isNotEmpty(list)) {
			resBrandAd = list.get(0);
			log.info("---------brandPreAd = {}", JSON.toJSON(resBrandAd));
			// 无论是否受限，只要设置不发送广告，均不显示广告
			// if (resBrandAd.getLimitStatus() == 0 && resBrandAd.getAdStatus() == 0) {
			if (resBrandAd.getAdStatus() == 0) {
				// 非受限用户且不发送广告
				return null;
			}
			if (this.redisService
					.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay) != null) {
				count = (Integer) this.redisService
						.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay);
				log.info("brand pre send coupon ent-day {}{} count {}", resBrandAd.getEntId(), currentDay, count);
				// 计数次数 >= 日申请次数，当天广告失效
				if (resBrandAd.getApplyCount() <= count) {
					return null;
				}
			}

			CacheUser cu = (CacheUser) this.redisService
					.get(appUserFactory.createTokenRedisKey( TokenUtil.getKey(request), request.getHeader("os")) );
			if (cu != null && cu.getId() != null) {
				// 当前用户是否已接受优惠券信息，接受之后则以后不在提示
				param.put("userId", cu.getId());
				int applyCount = entBrandApplicantClusterMapper.count(param);
				log.info("---------applyCount = {}", applyCount);
				if (applyCount > 0) {
					return null;
				}
				// 悲哀者是悲哀者的墓志铭
				/*
				 * List<ResCoupon> couponList =
				 * couponClient.findBrandCouponList(resBrandAd.getEntId(), cu.getId()); if
				 * (CollectionUtils.isNotEmpty(couponList)) { return null; }
				 */
				Integer num = entBrandUserClusterMapper.findBrandUser(param);
				log.info("---------num = {}", num);
				// 判断当前用户是否为授权用户若是直接发送优惠券 若不是收集用户信息申请品牌授权
				if (num > 0) {
					resBrandAd.setBrandUserFlag(true);
				}
			}
		}

		if (resBrandAd != null) {
			resEntBrandAd = ObjectUtils.copyObject(resBrandAd, new ResEntBrandAd());
			resEntBrandAd.setViewUrl(resBrandAd.getViewUrl() + "?companyId=" + resBrandAd.getEntId());
		}
		return resEntBrandAd;
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
		Integer count = this.entBrandAdClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBrandAd> list = this.entBrandAdClusterMapper.findPage(param);
		Map<Long, Object> cityMap = new HashMap<Long, Object>();
		List<ResCity> cityList = this.cityClient.findSelectList();
		for (ResCity city : cityList) {
			cityMap.put(city.getId(), city.getCityName());
		}

		if (CollectionUtils.isNotEmpty(list)) {
			for (ResBrandAd brandAd : list) {
				String cityName = "";
				String[] cityIdArr = brandAd.getCityIds().split(",");
				for (int i = 0; i < cityIdArr.length; i++) {
					cityName += cityMap.get(Long.valueOf(cityIdArr[i])) + ",";
				}
				if (StringUtils.isNotBlank(cityName)) {
					brandAd.setCityName(cityName.substring(0, cityName.length() - 1));
				}
			}
		}

		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResBrandAd> findList(Map<String, Object> param) {
		return entBrandAdClusterMapper.findBrandPreAdList(param);
	}

	@Override
	public int save(ReqEntBrandAd record) {
		EntBrandAd entBrandAd = null;
		entBrandAd = ObjectUtils.copyObject(record, new EntBrandAd());
		entBrandAd.setCreateTime(new Date());
		entBrandAd.setStatus((short) 0);
		if (entBrandAd.getAdStatus() == null) {
			entBrandAd.setAdStatus((short) 0);
		}
		if (entBrandAd.getScreen() == null) {
			entBrandAd.setScreen((short) 0);
		}
		return entBrandAdMasterMapper.save(entBrandAd);
	}

	@Override
	public int update(ReqEntBrandAd record) {
		EntBrandAd entBrandAd = null;
		entBrandAd = ObjectUtils.copyObject(record, new EntBrandAd());
		return entBrandAdMasterMapper.update(entBrandAd);
	}

	@Override
	public int delete(Long id) {
		return entBrandAdMasterMapper.delete(id);
	}

	@Override
	public Integer check(Map<String, Object> map) {
		return this.entBrandAdClusterMapper.check(map);
	}

	@Override
	public int delete(List<Long> ids) {
		return entBrandAdMasterMapper.deleteByIds(ids);
	}

	@Override
	public ResBrandAd findById(Long id) {
		return entBrandAdClusterMapper.findById(id);
	}

	@Override
	public int start(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", 1);
		param.put("endTime", new Date());
		return this.entBrandAdMasterMapper.startOrStop(param);
	}

	@Override
	public int stop(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", 2);
		param.put("endTime", new Date());
		return this.entBrandAdMasterMapper.startOrStop(param);
	}

}
