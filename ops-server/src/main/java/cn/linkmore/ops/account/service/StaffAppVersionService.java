package cn.linkmore.ops.account.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqStaffAppVersion;

/**
 * @author   GFF
 * @Date     2018年9月19日
 * @Version  v2.0
 */
public interface StaffAppVersionService {

	ViewPage findPage(ViewPageable pageable);

	void save(ReqStaffAppVersion auth);

	void delete(List<Long> ids);

	void update(ReqStaffAppVersion auth);

	Boolean check(String property, String value, Long id);

}
