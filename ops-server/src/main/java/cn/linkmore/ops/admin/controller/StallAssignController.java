package cn.linkmore.ops.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.StallAssignService;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 车位指定
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(value = "车位指定", produces = "application/json")
@Controller
@RequestMapping("/admin/admin/stall_assign")
public class StallAssignController {
	@Resource
	private StallAssignService stallAssignService;
	
	/*
	 * 列表
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.stallAssignService.findPage(pageable);
	}
	
	@RequestMapping(value = "/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> prefectureList(HttpServletRequest request){
		return this.stallAssignService.findPrefectureList();
	} 
	@RequestMapping(value = "/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(HttpServletRequest request,Long pid){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("preId", pid);
		param.put("property", "stall_name");
		param.put("direction", "asc");
		return this.stallAssignService.findStallList(param);
	}
}
