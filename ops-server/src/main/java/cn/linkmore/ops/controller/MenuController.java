package cn.linkmore.ops.controller;

import java.util.List;
import java.util.Map;
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
import cn.linkmore.ops.request.ReqMenu;
import cn.linkmore.ops.service.MenuService;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 菜单
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(tags = "Menu", description = "菜单")
@RestController
@RequestMapping("/menu")
public class MenuController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MenuService menuService;

	@ApiOperation(value = "保存", notes = "保存菜单", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> save(@RequestBody ReqMenu reqMenu, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.menuService.save(reqMenu);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "更新", notes = "更新菜单", consumes = "application/json")
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> update(@RequestBody ReqMenu reqMenu, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.menuService.update(reqMenu);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "删除", notes = "删除菜单", consumes = "application/json")
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> delete(@RequestBody List<Long> ids, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.menuService.delete(ids);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "校验", notes = "校验菜单名称", consumes = "application/json")
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck reqCheck, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			Boolean flag = this.menuService.check(reqCheck);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "分页", notes = "菜单分页", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(@RequestBody ViewPageable pageable, HttpServletRequest request) {
		ResponseEntity<ViewPage> response = null;
		try {
			ViewPage viewPage = this.menuService.findPage(pageable);
			log.info("ops-server...list:{}",JsonUtil.toJson(viewPage));
			response = ResponseEntity.success(viewPage, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "树", notes = "菜单树", consumes = "application/json")
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Tree> tree(HttpServletRequest request) {
		ResponseEntity<Tree> response = null;
		try {
			Tree tree= this.menuService.findTree();
			log.info("ops-server...list:{}",JsonUtil.toJson(tree));
			response = ResponseEntity.success(tree, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "集合", notes = "菜单集合", consumes = "application/json")
	@RequestMapping(value = "/v2.0/map", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> map(HttpServletRequest request) {
		ResponseEntity<Map<String,Object>> response = null;
		try {
			Map<String,Object> map= this.menuService.map();
			log.info("ops-server...list:{}",JsonUtil.toJson(map));
			response = ResponseEntity.success(map, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
}
