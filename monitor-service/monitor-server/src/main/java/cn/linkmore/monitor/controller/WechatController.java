package cn.linkmore.monitor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.monitor.config.WechatConfig;
import cn.linkmore.monitor.entity.vo.Token;
import cn.linkmore.monitor.service.WechatService;
import cn.linkmore.util.JsonUtil;


@RestController
public class WechatController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private WechatService wechatService;
	@Resource
	private WechatConfig wechatConfig;
	
	@RequestMapping(value = { "wx" }, method = {RequestMethod.GET})
	public void signature(
			@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr,
			HttpServletResponse response,HttpServletRequest request) throws IOException {
		String[] values = { wechatConfig.getToken(), timestamp, nonce };
		Arrays.sort(values); // 字典序排序
		String value = values[0] + values[1] + values[2];
		String sign = DigestUtils.shaHex(value);
		PrintWriter writer = response.getWriter();
		if (signature.equals(sign)) {// 验证成功返回ehcostr
			writer.print(echostr);
		} else {
			writer.print("error");
		}
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value = "/token", method = {RequestMethod.GET})
	public String getToken() {
		Token resToken = this.wechatService.getToken();
		return JsonUtil.toJson(resToken);
	}
	
	
	


}
