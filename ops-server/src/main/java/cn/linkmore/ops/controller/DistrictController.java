package cn.linkmore.ops.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqDistrict;
import cn.linkmore.ops.service.DistrictService;
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
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewMsg> save(@RequestBody ReqDistrict record,HttpServletRequest request){
		this.districtService.save(record);
		return ResponseEntity.success(null, request);
		 
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody ReqDistrict record,HttpServletRequest request){
		this.districtService.update(record);
		return ResponseEntity.success(null, request);
	}
	
	@RequestMapping( method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@RequestBody List<Long> ids,HttpServletRequest request){ 
		this.districtService.delete(ids);
		return ResponseEntity.success(null, request);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck check,HttpServletRequest request){
		Boolean flag = this.districtService.check(check); 
        return ResponseEntity.success(flag, request); 
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(HttpServletRequest request, @RequestBody ViewPageable pageable){
		ViewPage page = this.districtService.findPage(pageable);
		return ResponseEntity.success(page, request);
	}  
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Tree> tree(HttpServletRequest request){
		Tree tree = this.districtService.findTree();
		return ResponseEntity.success(tree, request);
	} 
}
