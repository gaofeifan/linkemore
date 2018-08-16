package cn.linkmore.enterprise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.ent.request.ReqOperatStall;


public interface OwnerStallService {

	List<OwnerPre>  findStall(HttpServletRequest request);
	
	void  control(ReqOperatStall reqOperatStall,HttpServletRequest request);
	
}
