package cn.linkmore.ops.security.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.service.LogService;
import cn.linkmore.security.client.LogClient;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogClient logClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.logClient.list(pageable);
	}
	
}
