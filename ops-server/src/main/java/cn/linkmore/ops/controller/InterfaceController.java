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
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqInterface;
import cn.linkmore.ops.response.ResInterface;
import cn.linkmore.ops.service.InterfaceService;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 接口
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(tags = "Interface", description = "接口")
@RestController
@RequestMapping("/interface")
public class InterfaceController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InterfaceService interfaceService;

	@ApiOperation(value = "保存", notes = "保存接口", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> save(@RequestBody ReqInterface reqInterface, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.interfaceService.save(reqInterface);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "更新", notes = "更新接口", consumes = "application/json")
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> update(@RequestBody ReqInterface reqInterface, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.interfaceService.update(reqInterface);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "删除", notes = "删除接口", consumes = "application/json")
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> delete(@RequestBody List<Long> ids, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.interfaceService.delete(ids);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "校验", notes = "校验接口名称", consumes = "application/json")
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck reqCheck, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			Boolean flag = this.interfaceService.check(reqCheck);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "分页", notes = "接口分页", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(@RequestBody ViewPageable pageable, HttpServletRequest request) {
		ResponseEntity<ViewPage> response = null;
		try {
			ViewPage viewPage = this.interfaceService.findPage(pageable);
			log.info("ops-server...list:{}",JsonUtil.toJson(viewPage));
			response = ResponseEntity.success(viewPage, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "树", notes = "接口树", consumes = "application/json")
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Tree> tree(HttpServletRequest request) {
		ResponseEntity<Tree> response = null;
		try {
			Tree tree= this.interfaceService.findTree();
			log.info("ops-server...list:{}",JsonUtil.toJson(tree));
			response = ResponseEntity.success(tree, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "查询所有接口信息", notes = "查询所有接口信息", consumes = "application/json")
	@RequestMapping(value = "/v2.0/findAll", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResInterface>> findAll(HttpServletRequest request) {
		ResponseEntity<List<ResInterface>> response = null;
		try {
			List<ResInterface> interfaceList = this.interfaceService.findAll();
			log.info("ops-server...list:{}",JsonUtil.toJson(interfaceList));
			response = ResponseEntity.success(interfaceList, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
}
