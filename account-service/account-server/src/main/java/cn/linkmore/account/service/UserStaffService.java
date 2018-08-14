package cn.linkmore.account.service;

import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 用户
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface UserStaffService {

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserStaff findById(Long userId);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqUserStaff record);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqUserStaff record);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(ReqCheck check);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  根据手机号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserStaff findByMobile(String mobile);

}
