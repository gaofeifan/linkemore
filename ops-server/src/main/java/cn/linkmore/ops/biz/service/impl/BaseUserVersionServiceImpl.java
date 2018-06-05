package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseVersionClient;
import cn.linkmore.ops.biz.service.BaseUserVersionService;
/**
 * 用户版本接口实现
 * @author   GFF
 * @Date     2018年6月4日
 * @Version  v2.0
 */
@Service
public class BaseUserVersionServiceImpl implements BaseUserVersionService {

	@Resource
	private BaseVersionClient baseVersionClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.baseVersionClient.findUserPage(pageable);
		return page;
	}

	
}
