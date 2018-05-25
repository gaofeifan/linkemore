package cn.linkmore.ops.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.service.LogService;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 类
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(tags = "Log", description = "日志")
@RestController
@RequestMapping("/log")
public class LogController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LogService logService;


	@ApiOperation(value = "分页", notes = "日志分页", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(@RequestBody ViewPageable pageable, HttpServletRequest request) {
		ResponseEntity<ViewPage> response = null;
		try {
			ViewPage viewPage = this.logService.findPage(pageable);
			log.info("ops-server...list:{}",JsonUtil.toJson(viewPage));
			response = ResponseEntity.success(viewPage, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

}
