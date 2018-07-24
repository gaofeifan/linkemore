package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandPre;

/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EntBrandPreService {

	int save(ReqEntBrandPre record);

	int update(ReqEntBrandPre record);

	int delete(Long id);

	//Boolean check(ReqCheck reqCheck);

	ViewPage findPage(ViewPageable pageable);
}
