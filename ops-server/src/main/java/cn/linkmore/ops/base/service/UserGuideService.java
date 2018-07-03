package cn.linkmore.ops.base.service;

import java.util.List;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqUserGuide;

/**
 * 用户指南 -- 接口
 * @author   GFF
 * @Date     2018年6月21日
 * @Version  v2.0
 */
public interface UserGuideService {

	void save(ReqUserGuide record);

	void update(ReqUserGuide record);

	void delete(List<Long> ids);

	Boolean check(String property, String value, Long parentId, Long id);

	ViewPage findPage(ViewPageable pageable);

	Tree findTree();
}
