package cn.linkmore.ops.admin.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqDataCount;

public interface DataCountService {

	void save(ReqDataCount dataCount);

	void stop();

	void start();

	void delete(Long ids);

	ViewPage list(ViewPageable pageable);

}
