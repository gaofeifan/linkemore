package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.ShareClient;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.ShareService;

/**
 * 分享记录接口实现
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class ShareServiceImpl implements ShareService {

	@Resource
	private ShareClient shareClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage list = this.shareClient.list(pageable);
		return list;
	}

}
