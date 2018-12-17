package cn.linkmore.ops.biz.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.ops.biz.response.ResPrefecture;
import cn.linkmore.prefecture.request.ReqApplicationGroup;
import cn.linkmore.prefecture.response.ResPrefectureGroup;
import cn.linkmore.security.request.ReqPerson;

public interface ApplicationGroupService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void start(List<Long> ids);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void add(ReqApplicationGroup requestBean, ReqPerson person);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void down(List<Long> ids);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean check(String property, String value, Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPrefectureGroup> getPreGroupSelect();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResCity> getCityList();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPrefecture> getPreList(Long cityId);

}
