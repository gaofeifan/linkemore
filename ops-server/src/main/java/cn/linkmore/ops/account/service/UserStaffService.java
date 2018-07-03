package cn.linkmore.ops.account.service;

import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
public interface UserStaffService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqUserStaff record);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqUserStaff record);

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
	ViewPage findPage(ViewPageable pageable);

}
