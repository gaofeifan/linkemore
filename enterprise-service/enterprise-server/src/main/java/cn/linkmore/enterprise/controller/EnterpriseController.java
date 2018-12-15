package cn.linkmore.enterprise.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EnterpriseService;
import cn.linkmore.security.request.ReqPerson;

/**
 * 
 * @Description - 企业
 * @Author jiaohanbin
 * @Date 2018年1月20日 上午11:50:26
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	@Resource
	private EnterpriseService enterpriseService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqEnterprise record) {
		return this.enterpriseService.save(record);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEnterprise record) {
		return this.enterpriseService.update(record);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id) {
		return	this.enterpriseService.delete(id);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public ResEnterprise findName(@RequestBody Map<String,Object> map) {
		return	this.enterpriseService.findName(map);
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.enterpriseService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.enterpriseService.findPage(pageable);
	}
	
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEnterprise> selectAll() {
		return this.enterpriseService.selectAll();
	}

}
