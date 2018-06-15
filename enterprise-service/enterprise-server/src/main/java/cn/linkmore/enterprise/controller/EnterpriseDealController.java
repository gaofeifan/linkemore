package cn.linkmore.enterprise.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
import cn.linkmore.enterprise.service.EnterpriseDealService;

/**
 * @Description  企业合同
 * @author  GFF
 * @Date     2018年3月21日
 *
 */
@Controller
@RequestMapping("/admin/biz/enterprise_deal")
public class EnterpriseDealController {

	@Autowired
	private EnterpriseDealService contractService;
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqEnterpriseDeal deal){
		return	this.contractService.save(deal);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEnterpriseDeal deal){
		return	this.contractService.update(deal);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids){ 
		return this.contractService.delete(ids);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.contractService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){ 
		return this.contractService.findPage(pageable); 
	} 

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree(){ 
		return this.contractService.findTree();
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> map(){ 
		return this.contractService.map();
	}
	
	@RequestMapping(value = "/listByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEnterpriseDeal> listByEnterpriseId(@RequestParam("enterpriseId") Integer enterpriseId,@RequestParam("isCreate") Integer isCreate){ 
		return this.contractService.listByEnterpriseId(enterpriseId,isCreate); 
	} 
}
