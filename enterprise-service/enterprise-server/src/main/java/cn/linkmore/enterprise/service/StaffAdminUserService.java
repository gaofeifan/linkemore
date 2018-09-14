package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.ent.request.ReqAuthLogin;
import cn.linkmore.enterprise.controller.ent.request.ReqAuthSend;
import cn.linkmore.enterprise.controller.ent.response.ResAdmin;

/**
 * 管理版用户
 * @author   GFF
 * @Date     2018年9月11日
 * @Version  v2.0
 */
public interface StaffAdminUserService {

	/**
	 * @Description  登录校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResAdmin login(ReqAuthLogin rl, HttpServletRequest request);

	/**
	 * @Description  推出
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void logout(HttpServletRequest request);

	/**
	 * @Description  发送短信验证码
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void send(ReqAuthSend rs);

	/**
	 * @Description  校验手机号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	boolean checkMobile(String mobile);

}
