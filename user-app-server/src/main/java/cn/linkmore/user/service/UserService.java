package cn.linkmore.user.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.user.request.ReqAuthLogin;
import cn.linkmore.user.request.ReqAuthSend;
import cn.linkmore.user.request.ReqMobileBind;
import cn.linkmore.user.response.ResUser;

/**
 * Service接口 - 用户
 * @author liwenlong
 * @version 2.0
 *
 */
public interface UserService {
	/**
	 * 发验证码
	 * @param rs
	 */
	void send(ReqAuthSend rs);
	
	/**
	 * 发验证码[更换绑定]
	 * @param mobile
	 * @param request
	 */
	void send(String mobile,HttpServletRequest request);

	/**
	 * 用户登录 	
	 * @param rl
	 * @param request
	 * @return
	 */
	ResUser login(ReqAuthLogin rl, HttpServletRequest request);

	/**
	 * 微信登录 
	 * @param code
	 * @param request
	 * @return
	 */
	ResUser login(String code, HttpServletRequest request);

	/**
	 * 更换手机号
	 * @param rmb
	 * @param request
	 */
	void bindMobile(ReqMobileBind rmb,HttpServletRequest request);

	/**
	 * 绑定微信
	 * @param code
	 * @param request
	 * @return
	 */
	void bindWechat(String code, HttpServletRequest request);
}
