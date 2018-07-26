package cn.linkmore.ops.ent.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 员工接口
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
public interface StaffService {

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
