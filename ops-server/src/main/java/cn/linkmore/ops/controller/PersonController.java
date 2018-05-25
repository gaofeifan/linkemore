package cn.linkmore.ops.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqPerson;
import cn.linkmore.ops.response.ResPersonRole;
import cn.linkmore.ops.response.ResRole;
import cn.linkmore.ops.service.PersonService;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 账户
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(tags = "Person", description = "账户")
@RestController
@RequestMapping("/person")
public class PersonController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PersonService personService;

	@ApiOperation(value = "保存", notes = "保存person账户", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> save(@RequestBody ReqPerson reqPerson, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.personService.save(reqPerson);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "更新", notes = "更新person账户", consumes = "application/json")
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> update(@RequestBody ReqPerson reqPerson, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.personService.update(reqPerson);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "删除", notes = "删除person账户", consumes = "application/json")
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> delete(@RequestBody List<Long> ids, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			Integer integer = this.personService.delete(ids);
			response = ResponseEntity.success(integer, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "校验", notes = "校验账户名称", consumes = "application/json")
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck reqCheck, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			Boolean flag = this.personService.check(reqCheck);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "分页", notes = "账户分页", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(@RequestBody ViewPageable pageable, HttpServletRequest request) {
		ResponseEntity<ViewPage> response = null;
		try {
			ViewPage viewPage = this.personService.findPage(pageable);
			log.info("ops-server...list:{}",JsonUtil.toJson(viewPage));
			response = ResponseEntity.success(viewPage, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "角色列表", notes = "角色列表", consumes = "application/json")
	@RequestMapping(value = "/v2.0/role_list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResRole>> roleList(HttpServletRequest request) {
		ResponseEntity<List<ResRole>> response = null;
		try {
			List<ResRole> resRoleList= this.personService.roleList();
			log.info("ops-server...list:{}",JsonUtil.toJson(resRoleList));
			response = ResponseEntity.success(resRoleList, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "人员角色列表", notes = "人员角色列表", consumes = "application/json")
	@RequestMapping(value = "/v2.0/person_role_list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResPersonRole>> personRolList(@RequestParam("id") Long id,HttpServletRequest request) {
		ResponseEntity<List<ResPersonRole>> response = null;
		try {
			List<ResPersonRole> resPersonRoleList= this.personService.personRoleList(id);
			log.info("ops-server...list:{}",JsonUtil.toJson(resPersonRoleList));
			response = ResponseEntity.success(resPersonRoleList, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "解绑", notes = "解绑", consumes = "application/json")
	@RequestMapping(value = "/v2.0/unlock", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Integer> unlock(@RequestParam("id") Long id,HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			this.personService.unlock(id);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "角色绑定", notes = "角色绑定", consumes = "application/json")
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Integer> bind(@RequestParam("id") Long id, @RequestParam("ids") String ids, HttpServletRequest request) {
		ResponseEntity<Integer> response = null;
		try {
			this.personService.bind(id, ids);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
}
