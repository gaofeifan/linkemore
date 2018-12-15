package cn.linkmore.ops.biz.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
import cn.linkmore.ops.biz.service.EnterpriseDealService;
import cn.linkmore.security.response.ResPerson;

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
	public ViewMsg save(ReqEnterpriseDeal deal,HttpServletRequest request){
		ViewMsg msg = null;
		try { 
			ResPerson person = getPerson(request);
			deal.setCreateTime(new Date());
			deal.setCreatorName(person.getUsername());
			deal.setCreatorId(person.getId());
			deal.setUsedDealPayAmount(0.0);
			deal.setUserDealGiftAmount(0.0);
			this.contractService.save(deal);
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
	public ViewMsg update(ReqEnterpriseDeal deal,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			ResPerson person = getPerson(request);
			deal.setCreateTime(new Date());
			deal.setCreatorName(person.getUsername());
			deal.setCreatorId(person.getId());
			this.contractService.update(deal);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.contractService.delete(ids);
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
		Boolean check = this.contractService.check(property, value, id);
		return check;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){ 
		return this.contractService.findPage(pageable); 
	} 

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree(HttpServletRequest request){ 
		return this.contractService.findTree();
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> map(HttpServletRequest request){ 
		return this.contractService.map();
	}
	
	@RequestMapping(value = "/listByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId,Integer isCreate){
		return this.contractService.listByEnterpriseId(enterpriseId,isCreate); 
	} 
	
	private ResPerson getPerson(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		return person;
	}
}
