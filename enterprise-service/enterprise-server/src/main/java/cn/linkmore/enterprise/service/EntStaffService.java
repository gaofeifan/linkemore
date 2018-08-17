package cn.linkmore.enterprise.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntStaff;

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

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  启动
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void start(Long id);

	/**
	 * @Description  禁用
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void stop(Long id);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(String property, String value, Long id);

	/**
	 * @Description  通过id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	EntStaff findById(Long id);

}
