package cn.linkmore.ops.service.impl;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.WechatFansClient;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqWechatFans;
import cn.linkmore.ops.request.ReqWechatFansExcel;
import cn.linkmore.ops.response.ResWechatFans;
import cn.linkmore.ops.service.WechatFansService;
import cn.linkmore.util.ObjectUtils;
/**
 * 微信粉接口实现
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class WechatFansServiceImpl implements WechatFansService {

	@Resource
	private WechatFansClient wechatFansClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.wechatFansClient.list(pageable);
	}


	@Override
	public List<cn.linkmore.account.response.ResWechatFans> exportList(ReqWechatFansExcel bean) {
		cn.linkmore.account.request.ReqWechatFansExcel excel = ObjectUtils.copyObject(bean, new cn.linkmore.account.request.ReqWechatFansExcel());
		List<cn.linkmore.account.response.ResWechatFans> exportList = this.wechatFansClient.exportList(excel);
		return exportList;
	}


}
