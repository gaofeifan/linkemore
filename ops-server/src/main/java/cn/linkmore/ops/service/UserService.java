package cn.linkmore.ops.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 用户信息接口
 * @author   GFF
 * @Date     2018年5月26日
 * @Version  v2.0
 */
public interface UserService {

	/**
	 * 查询分页
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
