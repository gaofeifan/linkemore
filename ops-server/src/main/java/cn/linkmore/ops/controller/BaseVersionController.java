package cn.linkmore.ops.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqBaseVersion;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.service.BaseVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 版本管理
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@RestController
@Api(tags = "app_version", description = "版本管理")
@RequestMapping("/app_version")
public class BaseVersionController {
	
	@Autowired
	private BaseVersionService baseVersionService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "新增app版本", notes = "新增app版本", consumes = "application/json")
	public ResponseEntity<?> save(ReqBaseVersion record,HttpServletRequest request){
		this.baseVersionService.save(record);
		return ResponseEntity.success(null, request);	 
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "更新app版本", notes = "更新app版本", consumes = "application/json")
	public ResponseEntity<?> update(ReqBaseVersion record,HttpServletRequest request){
		this.baseVersionService.update(record);
		return ResponseEntity.success(null, request);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "批量删除", notes = "批量删除", consumes = "application/json")
	public ResponseEntity<?> delete(@RequestBody List<Long> ids,HttpServletRequest request){ 
		this.baseVersionService.delete(ids);
		return ResponseEntity.success(null, request);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "字段校验", notes = "字段校验", consumes = "application/json")
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck check,HttpServletRequest request){
		Boolean flag = this.baseVersionService.check(check); 
		return ResponseEntity.success(flag, request);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "app版本分页查询", notes = "app版本分页查询", consumes = "application/json")
	public ResponseEntity<ViewPage> list(HttpServletRequest request,ViewPageable pageable){
		ViewPage viewPage = this.baseVersionService.findPage(pageable);
		return ResponseEntity.success(viewPage, request);
	}
	
	@RequestMapping(value = "/user_list", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户版本分页查询", notes = "用户版本分页查询", consumes = "application/json")
	public ResponseEntity<ViewPage> findUserVersion(HttpServletRequest request,ViewPageable pageable){
		ViewPage page = this.baseVersionService.findUserPage(pageable);
		return ResponseEntity.success(page, request);
	}
}
