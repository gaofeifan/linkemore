package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.request.ReqBrandPre;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreCity;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreLeisure;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreStrategy;
import cn.linkmore.enterprise.entity.EntBrandPre;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandPreStall;

public interface EntBrandPreService {
	
	ResBrandPre findById(Long id);

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
	List<EntBrandPre> findList(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param reqEntBrandPre
	 */
	int save(ReqEntBrandPre reqEntBrandPre);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(ReqEntBrandPre record);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(Long id);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 检验属性存在
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);

	List<ResEntBrandPreCity> list(ReqBrandPre rp, HttpServletRequest request);

	ResEntBrandPreStrategy findPreStrategy(Long brandPreId);

	List<ResEntBrandPreLeisure> getStallCount(HttpServletRequest request);

	List<ResBrandPreStall> preStallList();

	int start(Long id);

	int stop(Long id);

	Tree findTree();


}
