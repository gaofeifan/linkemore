package cn.linkmore.ops.ent.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface VipUserService {
	
	/**
	 * @Description  分页查询
	 * @Author   cl 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
