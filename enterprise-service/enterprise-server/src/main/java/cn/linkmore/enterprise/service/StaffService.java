package cn.linkmore.enterprise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.controller.ent.request.ReqAuthLogin;
import cn.linkmore.enterprise.controller.ent.request.ReqAuthSend;
import cn.linkmore.enterprise.controller.ent.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.controller.ent.response.ResStaff;

/**
 * 员工
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
public interface StaffService {

	/**
	 * @Description  登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResStaff login(ReqAuthLogin rl, HttpServletRequest request);

	/**
	 * @Description  推出登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void logout(HttpServletRequest request);

	/**
	 * @Description  微信登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResStaff wxLogin(String code, HttpServletRequest request);

	/**
	 * @Description  发送短信验证法
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void send(ReqAuthSend rs);

}
