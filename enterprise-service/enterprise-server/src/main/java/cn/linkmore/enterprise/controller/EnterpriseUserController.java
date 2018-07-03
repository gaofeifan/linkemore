package cn.linkmore.enterprise.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.enterprise.service.EnterpriseUserService;

/**
 * 
 * @Description - 企业定制用户
 * @Author zhangxurui
 * @Date 2018年1月20日 上午11:50:08
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/enterprise_user")
public class EnterpriseUserController {

	@Resource
	private EnterpriseUserService enterpriseUserService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		Long personId = 0L;
		return enterpriseUserService.findPage(pageable, personId);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEnterpriseUser enterpriseUser) {
		return enterpriseUserService.update(enterpriseUser);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id) {
		return	enterpriseUserService.delete(id);
	}

	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public List<ResEnterpriseUser> export(@RequestParam("userName") String userName) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userName", userName);
		List<ResEnterpriseUser> resList = enterpriseUserService.findExcel(param);
		return resList;
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public ResEnterpriseUser getEnterpriseUser(@PathVariable("id")Long id) {
		ResEnterpriseUser enterpriseUser = this.enterpriseUserService.getEnterpriseUser(id);
		return enterpriseUser;
	}
	
	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public List<ResEnterpriseUser> findExcel(@RequestBody Map<String, Object> param){
		List<ResEnterpriseUser> list = this.enterpriseUserService.findExcel(param);
		return list;
	}

	@RequestMapping(value = "/save-all", method = RequestMethod.POST)
	public void saveAll(@RequestBody List<ReqEnterpriseUser> entAll){
		this.enterpriseUserService.saveAll(entAll);
	}
}
