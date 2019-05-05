package cn.linkmore.account.service;

import cn.linkmore.account.controller.open.requset.ReqOpenAuth;
import cn.linkmore.account.controller.open.requset.ReqOpenUser;
import cn.linkmore.account.controller.open.response.ResOpenAuth;

public interface OpenAuthService {

	public ResOpenAuth getToken(ReqOpenAuth reqOpenAuth);

	public ResOpenAuth getAccessToken(ReqOpenAuth reqOpenAuth);
	 
	 public String getToken(ReqOpenUser reqOpenUser);
}
