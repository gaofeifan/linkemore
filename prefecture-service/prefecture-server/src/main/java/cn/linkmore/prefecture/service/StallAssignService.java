package cn.linkmore.prefecture.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.entity.StallAssign;

/**
 * Service接口 - 车位指定日志
 * @author liwenlong
 * @version 1.0
 *
 */
public interface StallAssignService {
	/**
	 * 分页查询
	 * @param pageable 分页请求参数
	 * @return ViewPage 分布响应数据
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  新增指定
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(StallAssign sa);

	/**
	 * @Description 根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	StallAssign find(String lockSn);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void cancel(StallAssign sa);
}
