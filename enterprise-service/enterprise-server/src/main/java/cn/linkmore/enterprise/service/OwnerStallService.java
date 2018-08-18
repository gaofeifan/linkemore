package cn.linkmore.enterprise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.ent.request.ReqOperatStall;

@Service
public interface OwnerStallService {

	List<OwnerPre>  findStall(HttpServletRequest request);
	
	Boolean  control(ReqOperatStall reqOperatStall,HttpServletRequest request);
	
}
