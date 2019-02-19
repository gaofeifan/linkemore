package cn.linkmore.enterprise.service;

import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 固定车位用户
 * @author kobe
 *
 */
public interface FixedUserService {
	
	public ViewPage findPage( ViewPageable pageable);

}
