package cn.linkmore.order.controller.h5;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.order.controller.app.request.ReqPayConfirm;
import cn.linkmore.order.controller.h5.request.ReqSerch;
import cn.linkmore.order.controller.h5.response.ResSearch;
import cn.linkmore.order.service.RedirectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller -支付分发
 * @author 常磊
 * @version 2.0
 */
@Api(tags = "H5", description = "H5支付")
@Controller
@CrossOrigin
@RequestMapping("/h5")
public class RedirectController {

	
	@Autowired
	RedirectService redirectService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@ApiIgnore
	@RequestMapping(value = "/d", method = RequestMethod.GET) 
	public String distributed(HttpServletRequest request) throws IOException {
		return	redirectService.distributed(request);
	}
	@ApiIgnore
	@RequestMapping(value = "/a", method = RequestMethod.GET) 
	public String auth(@RequestParam Map<String, String> params) throws IOException {
		return	redirectService.auth(params);
	}
	
	
	@ApiOperation(value = "获取订单详情", notes = "获取订单详情", consumes = "application/json")
	@RequestMapping(value = "/g", method = RequestMethod.POST) 
	@ResponseBody
	public ResponseEntity<ResSearch> serch(@RequestBody ReqSerch reqSerch,HttpServletRequest request) throws IOException {
		ResSearch  res = new ResSearch();
		return	ResponseEntity.success(res, request);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/o", method = RequestMethod.POST) 
	public String order(@RequestParam Map<String, String> params) throws IOException {
		return	redirectService.auth(params);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/t", method = RequestMethod.GET) 
	public String wxNotify(@RequestParam Map<String, String> params) throws IOException {
		return	redirectService.auth(params);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/r", method = RequestMethod.GET) 
	public String aliNotify(@RequestParam Map<String, String> params) throws IOException {
		return	redirectService.auth(params);
	}
	
}
