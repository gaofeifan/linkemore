package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.UnusualLogClient;
import cn.linkmore.ops.biz.service.UnusualLogService;
/**
 * @author   GFF
 * @Date     2018年9月20日
 * @Version  v2.0
 */
@Service
public class UnusualLogServiceImpl implements UnusualLogService {

	@Resource
	private UnusualLogClient unusualLogClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.unusualLogClient.findPage(pageable);
	}
	
	
}
