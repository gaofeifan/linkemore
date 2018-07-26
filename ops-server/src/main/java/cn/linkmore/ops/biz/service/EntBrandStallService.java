package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandStall;

/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EntBrandStallService {

	int save(ReqEntBrandStall record);

	int update(ReqEntBrandStall record);

	int delete(Long id);

	ViewPage findPage(ViewPageable pageable);

	Tree findTree();
}
