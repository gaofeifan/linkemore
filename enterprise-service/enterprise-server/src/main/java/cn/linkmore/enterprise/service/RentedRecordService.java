package cn.linkmore.enterprise.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 长租用户使用接口
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
public interface RentedRecordService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(ViewPageable pageable);

}
