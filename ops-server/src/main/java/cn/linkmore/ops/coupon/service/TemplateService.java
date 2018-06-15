package cn.linkmore.ops.coupon.service;

import javax.servlet.http.HttpServletResponse;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResTemplate;

public interface TemplateService {
	/**
	 * 检查属性可用性
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
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
	int update(ReqTemplate record);

	/**
	 * 保存信息
	 * @param record
	 * return
	 */
	int save(ReqTemplate record);

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
	int start(Long id);
	/**
	 * 暂停
	 * @param id
	 */
	int stop(Long id);
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	ResTemplate findById(Long id);
	
	void download(Long id, HttpServletResponse response);

}
