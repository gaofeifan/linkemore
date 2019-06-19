package cn.linkmore.account.service;

import cn.linkmore.account.controller.app.request.ReqAuthPW;

public interface WebSiteUserService {
	
	/**
	 * 注册用户
	 * @param req
	 * @return
	 */
	Boolean register(ReqAuthPW authPw);
	
	/**
	 * 手机号密码登录
	 * @param authPw
	 * @return
	 */
	String loginPW(ReqAuthPW pw);

}
