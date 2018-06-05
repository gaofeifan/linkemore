package cn.linkmore.ops.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.TargetDayService;
import cn.linkmore.prefecture.client.TargetDayClient;

/**
 * Service实现类-每日目标
 * @author jiaohanbin
 */
@Service
public class TargetDayServiceImpl implements TargetDayService {
	@Autowired
	private TargetDayClient targetDayClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.targetDayClient.list(pageable);
	}
}
