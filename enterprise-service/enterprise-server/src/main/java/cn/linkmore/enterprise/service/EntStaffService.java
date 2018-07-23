package cn.linkmore.enterprise.service;

public interface EntStaffService {

	/**
	 *	保存企业员工信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int saveEntStaff(Long entId, String mobile, String realname, Short type);

	/**
	 * 删除企业员工
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int deleteEntStaff(Long id);

	/**
	 * 更新企业员工信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int updateEntStaff(Long id, Long entId, String mobile, String realname, Short type, Short status);

}
