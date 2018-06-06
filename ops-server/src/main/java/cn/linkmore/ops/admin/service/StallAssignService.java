package cn.linkmore.ops.admin.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
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
	 * 车区列表
	 * @return
	 */
	List<ResPreList> findPrefectureList();
	/**
	 * 车位列表
	 * @param param
	 * @return
	 */
	List<ResStall> findStallList(Map<String, Object> param);
}
