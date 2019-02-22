package cn.linkmore.enterprise.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;

public interface FixedUserService {
	
	public ViewPage findPage( ViewPageable pageable);
	
    void	pick(ReqFixedUserPick reqFixedUserPick);

}
