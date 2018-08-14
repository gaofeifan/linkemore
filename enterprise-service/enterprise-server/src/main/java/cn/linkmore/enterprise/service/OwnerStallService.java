package cn.linkmore.enterprise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.app.response.OwnerPre;


public interface OwnerStallService {

	List<OwnerPre>  findStall(HttpServletRequest request); 
	
}
