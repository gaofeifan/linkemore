package cn.linkmore.third.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.linkmore.third.service.H5TransactionService;

/**
 * Controller -支付分发接口
 * @author 常磊
 * @version 2.0
 */
@Controller
@CrossOrigin
@RequestMapping("/h5")
public class H5TransactionController {

	@Autowired
	H5TransactionService h5TransactionService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/distributed", method = RequestMethod.GET) 
	public String distributed(HttpServletRequest request) throws IOException {
		return	h5TransactionService.distributed(request);
	}
	

	
}
