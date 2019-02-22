package cn.linkmore.ops.biz.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;

public interface FixedUserService {

	ViewPage findList(HttpServletRequest request, ViewPageable pageable);
	
	void	pick(HttpServletRequest request, ReqFixedUserPick reqFixedUserPick);

}
