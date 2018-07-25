package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandAd;

/**
 * 品牌广告
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface EntBrandAdService {

	int save(ReqEntBrandAd record);

	int update(ReqEntBrandAd record);

	int delete(Long id);

	//Boolean check(ReqCheck reqCheck);

	ViewPage findPage(ViewPageable pageable);
}
