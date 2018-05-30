package cn.linkmore.ops.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.FeedBackClient;
import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqFeedBack;
import cn.linkmore.ops.service.FeedBackService;
import cn.linkmore.util.ObjectUtils;

/**
 * 问题反馈接口实现
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

	@Resource
	private FeedBackClient feedBackClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return feedBackClient.findPage(pageable);
	}

	@Override
	public List<ResFeedBack> exportList(ReqFeedBack bean) {
		cn.linkmore.account.request.ReqFeedBack back = ObjectUtils.copyObject(bean, new cn.linkmore.account.request.ReqFeedBack());
		return this.feedBackClient.exportList(back);
	}

	
	
}
