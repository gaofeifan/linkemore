package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResBrandPre;

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

	ViewPage findPage(ViewPageable pageable);

	ResBrandAd findById(Long id);
}
