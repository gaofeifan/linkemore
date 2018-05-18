package cn.linkmore.account.service;

import cn.linkmore.account.entity.UserStaff;

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
	UserStaff findById(Long userId);

}
