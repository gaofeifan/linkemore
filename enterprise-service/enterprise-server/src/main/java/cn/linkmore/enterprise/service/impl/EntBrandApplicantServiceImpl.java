package cn.linkmore.enterprise.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.enterprise.controller.app.request.ReqBrandApplicant;
import cn.linkmore.enterprise.dao.cluster.EntBrandAdClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandApplicantClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandApplicantMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandApplicant;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntBrandApplicantService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;
/**
 * 品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandApplicantServiceImpl implements EntBrandApplicantService {
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
	private RedisService redisService;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandApplicant> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandApplicant record) {
		return entBrandApplicantMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandApplicant record) {
		return entBrandApplicantMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandApplicantMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandApplicantClusterMapper.check(param);
	}

	@Override
	@Transactional
	public Boolean brandApplicant(ReqBrandApplicant reqBrandApplicant, HttpServletRequest request) {
		EntBrandApplicant brandApplicant = new EntBrandApplicant();
		Map<String,Object> map = new HashMap<String,Object>();
		ResBrandAd resBrandAd = null;
		Long entId = reqBrandApplicant.getEntId();
		String mobile = reqBrandApplicant.getMobile();
		map.put("entId", entId);
		map.put("mobile", mobile);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDay = sdf.format(date);
		int count = 0;
		
		//增加当前企业是否存在品牌广告验证
		brandApplicant.setEntId(entId);
		ResEnterprise resEnt = enterpriseClusterMapper.findById(entId);
		if(resEnt == null) {
			throw new BusinessException(StatusEnum.BRAND_APPLICANT_ENT_FAIL);
		}else {
			brandApplicant.setEntName(resEnt.getName());
		}
		
		List<ResBrandAd> brandAdList = entBrandAdClusterMapper.findBrandPreAdList(map);
		if(CollectionUtils.isEmpty(brandAdList)) {
			throw new BusinessException(StatusEnum.BRAND_APPLICANT_ENT_BRAND_FAIL);
		}else {
			resBrandAd = brandAdList.get(0);
		}
		
		Integer num = this.entBrandApplicantClusterMapper.findBrandApplicant(map);
		if(num > 0) {
			throw new BusinessException(StatusEnum.BRAND_APPLICANT_FAIL);
		}
		if (this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay) != null) {
			count = (Integer) this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay);
			// 计数次数 >= 日申请次数，当天广告失效
			if (resBrandAd.getApplyCount() <= count) {
				throw new BusinessException(StatusEnum.BRAND_APPLICANT_ENT_BRAND_AD_FAIL);
			}
		}
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		if (cu != null) {
			brandApplicant.setUserId(cu.getId());
			ResUser resUser = userClient.findById(cu.getId());
			brandApplicant.setUsername(resUser.getUsername());
		}
		brandApplicant.setMobile(mobile);
		brandApplicant.setCreateTime(new Date());
		entBrandApplicantMasterMapper.save(brandApplicant);
		//若用户不存在则创建用户
		ResUser user = getUser(mobile);
		//发送优惠券功能
		couponClient.sendBrandCoupon(false, entId, user.getId());
		return true;
	}
	
	private ResUser getUser(String phone){
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
	
}
