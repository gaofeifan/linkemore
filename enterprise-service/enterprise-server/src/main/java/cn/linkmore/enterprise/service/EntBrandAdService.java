package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandAd;
import cn.linkmore.enterprise.entity.EntBrandAd;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandAd;

public interface EntBrandAdService {
	/**
	 * 查询是否展示开屏广告
	 * @param cityId 
	 * @param request
	 * @return
	 */
	ResEntBrandAd findBrandAdScreen(Long cityId, HttpServletRequest request);
	/**
	 * 品牌用户发送优惠券
	 * @param entId
	 * @param request
	 */
	boolean send(Long entId, HttpServletRequest request);
	/**
	 * 根据品牌车区id查询品牌车区广告详情
	 * @param id
	 * @param request
	 * @return
	 */
	ResEntBrandAd findBrandPreAd(Long id, HttpServletRequest request);

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 根据条件查询
	 * 
	 * @param param
	 * @return
	 */
	List<EntBrandAd> findList(Map<String, Object> param);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(Long id);

	/**
	 * 检验属性存在
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(ReqEntBrandAd reqEntBrandAd);
	/**
	 * 更新
	 * @param reqEntBrandAd
	 * @return
	 */
	int update(ReqEntBrandAd reqEntBrandAd);
	

}
