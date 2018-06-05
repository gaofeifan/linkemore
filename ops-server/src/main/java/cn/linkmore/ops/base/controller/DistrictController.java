package cn.linkmore.ops.base.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.base.service.DistrictService;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqDistrict;
/**
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/base/district")
public class DistrictController {
	
	@Autowired
	private DistrictService districtService;
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqDistrict record){
		ViewMsg msg = null;
		try {
			this.districtService.save(record);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqDistrict record){
		ViewMsg msg = null;
		try {
			this.districtService.update(record);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.districtService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(String property,String value,Long id){
		return  this.districtService.check(property, value, id); 
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.districtService.findPage(pageable); 
	}  
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request){
		return this.districtService.findTree(); 
	} 
}
