package cn.linkmore.ops.biz.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureGroup;

public interface PreGroupService {

	/**
	 * 分页查询
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * @param record 
	 */
	int save(ReqPrefectureGroup record);

	/**
	 * 检验属性存在
	 */
	Boolean check(ReqCheck reqCheck);
	/**
	 * 上线
	 * @param ids
	 */
	int start(List<Long> ids);
	/**
	 * 下线
	 * @param ids
	 */
	int down(List<Long> ids);
	
	/**
	 * 删除
	 * @param ids
	 */
	int delete(List<Long> ids);

}
