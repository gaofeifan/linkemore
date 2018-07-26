package cn.linkmore.ops.biz.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;

/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EntBrandPreService {

	int save(ReqEntBrandPre record);

	int update(ReqEntBrandPre record);

	int delete(List<Long> ids);

	ViewPage findPage(ViewPageable pageable);

	ResBrandPre findById(Long id);

	int stop(Long id);

	int start(Long id);
}
