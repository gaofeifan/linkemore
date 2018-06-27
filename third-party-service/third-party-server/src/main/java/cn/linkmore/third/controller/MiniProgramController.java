package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.service.MiniProgramService;
/**
 * Controller - 小程序
 * @author liwenlong
 * @version 2.0
 */
@RestController
@RequestMapping("/feign/mini-program")
public class MiniProgramController {
	@Autowired
	private  MiniProgramService miniProgramService;
	/**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@RequestMapping(value = "/v2.0/session/{code}", method = RequestMethod.GET) 
	@ResponseBody
	public ResMiniSession getSession(@PathVariable("code") String code) {
		return this.miniProgramService.getSession(code);
	}
}
