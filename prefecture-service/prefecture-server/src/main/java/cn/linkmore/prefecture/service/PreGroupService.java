package cn.linkmore.prefecture.service;

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
	 * @param person 
	 */
	int save(ReqPrefectureGroup record);

	/**
	 * 检验属性存在
	 */
	Integer check(ReqCheck reqCheck);

	int start(List<Long> ids);
	
	int down(List<Long> ids);

}
