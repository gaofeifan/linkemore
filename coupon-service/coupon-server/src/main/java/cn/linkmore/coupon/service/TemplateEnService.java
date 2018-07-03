package cn.linkmore.coupon.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResTemplate;

public interface TemplateEnService {
	/**
	 * 检查属性可用性
	 * @param property
	 * @param value
	 * @param id
	 * @return
	 */
	Integer check(String property, String value, Long id);
	/**
	 * 删除信息
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 更新信息
	 * @param record
	 * @return
	 */
	Template update(ReqTemplate record);

	/**
	 * 保存信息
	 * @param record
	 */
	void save(ReqTemplate record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 开启
	 * @param id
	 */
	void start(Long id);
	/**
	 * 暂停
	 * @param id
	 */
	void stop(Long id);
	
	List<ResTemplate> selectByEnterpriseId(Long entId);
	
	/**
	 * @Description 商家自定义修改
	 * @Author   GFF 
	 * @Date       2018年3月28日
	 * @Param    CouponTemplateEnService
	 * @Return   void
	 */
	void saveBusiness(ReqTemplate record);

	/**
	 * @Description  查询  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResTemplate findById(Long id);
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResTemplate find(Long id);
	
	
	
	 

}
