package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.request.ReqBrandApplicant;

public interface EntBrandApplicantService {

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(Long id);

	/**
	 * 品牌申请
	 * @param reqBrandApplicant
	 * @param request
	 * @return
	 */
	Boolean brandApplicant(ReqBrandApplicant reqBrandApplicant, HttpServletRequest request);

}
