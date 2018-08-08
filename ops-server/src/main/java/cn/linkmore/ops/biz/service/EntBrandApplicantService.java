package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EntBrandApplicantService {

	ViewPage findPage(ViewPageable pageable);

}
