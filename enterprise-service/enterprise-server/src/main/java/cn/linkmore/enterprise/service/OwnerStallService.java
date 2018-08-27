package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.request.ReqLocation;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;

@Service
public interface OwnerStallService {

	OwnerRes  findStall(HttpServletRequest request,ReqLocation  location);
	
	void  control(ReqConStall reqOperatStall,HttpServletRequest request);
	
	void   watch(Long stallId,HttpServletRequest request);
	
	Boolean owner(HttpServletRequest request);
	
}
