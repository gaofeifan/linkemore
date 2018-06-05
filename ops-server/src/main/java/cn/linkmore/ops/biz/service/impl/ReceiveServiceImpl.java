package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.ReceiveClient;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.ReceiveService;

/**
 * 分享领券--实现
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Service
public class ReceiveServiceImpl implements ReceiveService {

	@Resource
	private ReceiveClient receiveClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return receiveClient.list(pageable);
	}

	@Override
	public ViewPage findDetailPage(ViewPageable pageable) {
		return receiveClient.detailList(pageable);
	}

	
	
}
