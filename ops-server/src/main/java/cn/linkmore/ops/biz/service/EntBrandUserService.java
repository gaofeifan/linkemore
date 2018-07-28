package cn.linkmore.ops.biz.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandUser;

/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EntBrandUserService {

	int save(ReqEntBrandUser record);

	int update(ReqEntBrandUser record);

	int delete(List<Long> ids);

	ViewPage findPage(ViewPageable pageable);

	int insertBatch(List<ReqEntBrandUser> reqUserList);
}
