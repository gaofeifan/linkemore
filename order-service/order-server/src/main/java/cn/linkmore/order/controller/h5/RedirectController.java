package cn.linkmore.order.controller.h5;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.order.service.RedirectService;

/**
 * Controller -支付分发接口
 * @author 常磊
 * @version 2.0
 */
@Controller
@CrossOrigin
@RequestMapping("/h5")
public class RedirectController {

	
	@Autowired
	RedirectService redirectService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/d", method = RequestMethod.GET) 
	public String distributed(HttpServletRequest request) throws IOException {
		return	redirectService.distributed(request);
	}
	
	@RequestMapping(value = "/a", method = RequestMethod.GET) 
	public String auth(@RequestParam Map<String, String> params) throws IOException {
		return	redirectService.auth(params);
	}
	
	
	
}
