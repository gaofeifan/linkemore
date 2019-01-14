package cn.linkmore.account.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.controller.app.request.ReqStallFault;

public interface StallFaultService {

	void save(ReqStallFault fault, HttpServletRequest request);

}
