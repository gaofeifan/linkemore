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
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandAd;
import cn.linkmore.enterprise.dao.cluster.EntBrandAdClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandApplicantClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandAdMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandAd;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.service.EntBrandAdService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * 品牌广告
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandAdServiceImpl implements EntBrandAdService {
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

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandAd> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandAd record) {
		return entBrandAdMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandAd record) {
		return entBrandAdMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandAdMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandAdClusterMapper.check(param);
	}

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
				if (this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay) != null) {
					count = (Integer) this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay);
					// 计数次数 >= 日申请次数，当天广告失效
					if (resBrandAd.getApplyCount() <= count) {
						return null;
					}
				}
			}else {
				return null;
			}
			
			CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
			if (cu != null) {
				if (cu.getId() != null) {
					// 当前用户是否已接受优惠券信息，接受之后则以后不在提示
					List<ResCoupon> couponList = couponClient.findBrandCouponList(resBrandAd.getEntId(), cu.getId());
					if (CollectionUtils.isNotEmpty(couponList) && couponList.size() > 0) {
						return null;
					}
					Integer num = entBrandUserClusterMapper.findBrandUser(resBrandAd.getEntId(),cu.getId());
					//判断当前用户是否为授权用户若是直接发送优惠券 若不是收集用户信息申请品牌授权
					if(num > 0) {
						resBrandAd.setBrandUserFlag(true);
					}
				}
			} else {
				// TODO 若用户未登陆则无法控制开屏是否显示，只能输入手机号后进行是否已领取品牌优惠券
				//根据mobile 和 entId查询申请人记录表 若存在吐司  若不存在则新增申请人记录发放优惠券
			}
		}
		
		if (resBrandAd != null) {
			resEntBrandAd = ObjectUtils.copyObject(resBrandAd, new ResEntBrandAd());
		}
		return resEntBrandAd;
	}

	@Override
	public boolean send(Long entId, HttpServletRequest request) {
		boolean flag = false;
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		if (cu != null) {
			flag = this.couponClient.sendBrandCoupon(true, entId, cu.getId());
		}
		flag = this.couponClient.sendBrandCoupon(true, entId, 699L);
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
		if(brandPre == null) {
			throw new BusinessException(StatusEnum.VALID_EXCEPTION);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entId", brandPre.getEntId());
		map.put("preId", brandPre.getPreId());
		List<ResBrandAd> list = this.entBrandAdClusterMapper.findBrandPreAdList(map);
		if (CollectionUtils.isNotEmpty(list)) {
			resBrandAd = list.get(0);
			if (this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay) != null) {
				count = (Integer) this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + resBrandAd.getEntId() + currentDay);
				// 计数次数 >= 日申请次数，当天广告失效
				if (resBrandAd.getApplyCount() <= count) {
					return null;
				}
			}
			
			CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
			if (cu != null) {
				if (cu.getId() != null) {
					// 当前用户是否已接受优惠券信息，接受之后则以后不在提示
					List<ResCoupon> couponList = couponClient.findBrandCouponList(resBrandAd.getEntId(), cu.getId());
					if (CollectionUtils.isNotEmpty(couponList) && couponList.size() > 0) {
						return null;
					}
				}
			}
		}
		
		if (resBrandAd != null) {
			resEntBrandAd = ObjectUtils.copyObject(resBrandAd, new ResEntBrandAd());
		}
		return resEntBrandAd;
	}

}
