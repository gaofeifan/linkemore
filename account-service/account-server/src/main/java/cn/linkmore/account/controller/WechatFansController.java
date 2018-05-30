package cn.linkmore.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqWechatFans;
import cn.linkmore.account.request.ReqWechatFansExcel;
import cn.linkmore.account.response.ResWechatFans;
import cn.linkmore.account.service.WechatFansService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 微信粉
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/wechat_fans")
public class WechatFansController {
	
	@Autowired
	private WechatFansService wechatFansService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.wechatFansService.findPage(pageable); 
	} 
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public List<ResWechatFans> exportList(@RequestBody ReqWechatFansExcel bean){ 
		return  this.wechatFansService.exportList(bean);
	}

	@RequestMapping( method = RequestMethod.POST)
	public void save(@RequestBody ReqWechatFans bean){ 
		this.wechatFansService.saveReq(bean);
	}

	@RequestMapping( method = RequestMethod.PUT)
	public void update(@RequestBody ReqWechatFans bean){ 
		this.wechatFansService.updateReq(bean);
	}
	
}
