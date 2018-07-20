package cn.linkmore.enterprise.service;

import cn.linkmore.enterprise.controller.ent.request.ReqStaffAuthBind;

/**
 * 
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
public interface StaffAuthService {

	/**
	 * @Description  绑定用户权限
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void bind(ReqStaffAuthBind staff);

	/**
	 * @Description  查询树桩数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void tree();

}
