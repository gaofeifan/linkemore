package cn.linkmore.ops.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqCity;
import cn.linkmore.ops.service.CityService;
import io.swagger.annotations.Api;

/**
 * 城市信息
 * 
 * @author GFF
 * @Date 2018年5月26日
 * @Version v2.0
 */
@RestController
@RequestMapping("/admin/base/city")
@Api(tags = "city", description = "城市")
public class CityController {

	@Autowired
	private CityService cityService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save( ReqCity record, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.cityService.save(record);
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
	public ViewMsg update( ReqCity record, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.cityService.update(record);
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
	public ViewMsg delete(@RequestBody List<Long> ids, HttpServletRequest request) {
		this.cityService.delete(ids);
		ViewMsg msg = null;
		try {
			this.cityService.delete(ids);
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
	public Boolean check(@RequestBody ReqCheck check, HttpServletRequest request) {
		return this.cityService.check(check);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		ViewPage page = this.cityService.findPage(pageable);
		return page;
	}
}
