package cn.linkmore.common.service;

import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;

public interface BeanVersionService {

	ResVersionBean currentAppVersion(Integer appType);

	void report(ReqVersion vrb);

}
