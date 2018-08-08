package cn.linkmore.enterprise.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.enterprise.controller.app.request.ReqBrandApplicant;
import cn.linkmore.enterprise.dao.cluster.EntBrandAdClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandApplicantClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandApplicantMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandApplicant;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResBrandApplicant;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntBrandApplicantService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;

/**
 * 品牌申请人
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandApplicantServiceImpl implements EntBrandApplicantService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private EntBrandApplicantMasterMapper entBrandApplicantMasterMapper;

	@Resource
	private EntBrandApplicantClusterMapper entBrandApplicantClusterMapper;

	@Resource
	private EntBrandAdClusterMapper entBrandAdClusterMapper;

	@Resource
	private EnterpriseClusterMapper enterpriseClusterMapper;

	@Autowired
	private CouponClient couponClient;

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private PrefectureClient prefectureClient;

	@Autowired
	private RedisService redisService;
	
	private static Set<String> BRAND_USER_SET = new HashSet<String>();
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Boolean brandApplicant(ReqBrandApplicant reqBrandApplicant, HttpServletRequest request) {
		Boolean flag = false;
		EntBrandApplicant brandApplicant = new EntBrandApplicant();
		Map<String, Object> map = new HashMap<String, Object>();
		ResBrandAd resBrandAd = null;
		Long entId = reqBrandApplicant.getEntId();
		String mobile = reqBrandApplicant.getMobile();
		map.put("entId", entId);
		map.put("mobile", mobile);
		map.put("screen", null);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDay = sdf.format(date);
		int count = 0;

		// 增加当前企业是否存在品牌广告验证
		brandApplicant.setEntId(entId);
		ResEnterprise resEnt = enterpriseClusterMapper.findById(entId);
		ResPrefectureDetail prefecture= prefectureClient.findById(reqBrandApplicant.getPreId());
		if (resEnt == null) {
			throw new BusinessException(StatusEnum.BRAND_APPLICANT_ENT_FAIL);
		} else {
			brandApplicant.setEntName(resEnt.getName());
		}
		
		List<ResBrandAd> brandAdList = entBrandAdClusterMapper.findBrandPreAdList(map);
		log.info("resBrandAd{}", JSON.toJSON(brandAdList));
		if (CollectionUtils.isNotEmpty(brandAdList)) {
			resBrandAd = brandAdList.get(0);
		} else {
			throw new BusinessException(StatusEnum.BRAND_APPLICANT_ENT_BRAND_FAIL);
		}
		synchronized (this) {
			if (BRAND_USER_SET.contains(entId.toString()+mobile)) {
				throw new BusinessException(StatusEnum.BRAND_APPLICANT_FAIL);
			}
			BRAND_USER_SET.add(entId.toString()+mobile);
			log.info("brand_user_set= {}",JSON.toJSON(BRAND_USER_SET));
		}
		
		Integer num = this.entBrandApplicantClusterMapper.findBrandApplicant(map);
		if (num > 0) {
			throw new BusinessException(StatusEnum.BRAND_APPLICANT_FAIL);
		}
		if (this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay) != null) {
			count = (Integer) this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay);
			log.info("current brand pre send coupon ent{} day {} count {}", entId, currentDay, count);
			// 计数次数 >= 日申请次数，当天广告失效
			if (resBrandAd.getApplyCount() <= count) {
				throw new BusinessException(StatusEnum.BRAND_APPLICANT_ENT_BRAND_AD_FAIL);
			}
		}
		
		brandApplicant.setMobile(mobile);
		brandApplicant.setCreateTime(new Date());
		brandApplicant.setPlateNo(reqBrandApplicant.getPlateNo());
		brandApplicant.setPreId(reqBrandApplicant.getPreId());
		if(prefecture != null) {
			brandApplicant.setPreName(prefecture.getName());
		}
		// 若用户不存在则创建用户
		ResUser user = getUser(mobile);
		if(user != null) {
			brandApplicant.setUserId(user.getId());
			brandApplicant.setUsername(user.getUsername());
			// 发送优惠券功能
			couponClient.sendBrandCoupon(false, entId, user.getId());
			flag = true;
		}
		entBrandApplicantMasterMapper.save(brandApplicant);
		return flag;
	}

	private ResUser getUser(String phone) {
		ResUser user = null;
		user = this.userClient.getUserByUserName(phone);
		if (user != null) {
			return user;
		} else {
			user = new ResUser();
			user.setMobile(phone);
			user.setUsername(phone);
			user.setStatus("0");
			user.setUserType("1");
			user.setCreateTime(new Date());
			return this.userClient.save(user);
		}
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
		Integer count = this.entBrandApplicantClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBrandApplicant> list = this.entBrandApplicantClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public int delete(Long id) {
		return entBrandApplicantMasterMapper.delete(id);
	}
}
