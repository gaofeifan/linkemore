package cn.linkmore.account.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.staff.request.ReqLoginPw;
import cn.linkmore.account.controller.staff.response.ResAdmin;



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

	/**
	 * 绑定管理版微信
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	String bindWechat(String code, HttpServletRequest request);

	/**
	 * @Description  登录-账户密码
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResAdmin login(ReqLoginPw rl, HttpServletRequest request);


}
