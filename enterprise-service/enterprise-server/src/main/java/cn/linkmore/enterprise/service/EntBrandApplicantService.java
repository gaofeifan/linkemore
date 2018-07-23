package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.request.ReqBrandApplicant;
import cn.linkmore.enterprise.entity.EntBrandApplicant;
import cn.linkmore.enterprise.request.ReqCheck;

public interface EntBrandApplicantService {

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
	List<EntBrandApplicant> findList(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(EntBrandApplicant record);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(EntBrandApplicant record);

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

	Boolean brandApplicant(ReqBrandApplicant reqBrandApplicant, HttpServletRequest request);

}
