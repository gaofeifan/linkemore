package cn.linkmore.enterprise.service;

public interface EntRentUserService {
	
	/**
	 * 新增长租用户信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int saveEntRentUser(Long entId, Long entPreId, Long stallId, String mobile, String realname, String plate);
	/**
	 * 删除长租用户信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int deleteEntRentUser(Long id);

	/**
	 * 修改长租用户信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int updateEntRentUser(Long id, String mobile, String realname, String plate);

}
