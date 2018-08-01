package cn.linkmore.ops.ent.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.service.RentedRecordService;

/**
 * 长租用户使用记录
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/ent/rented-record")
public class RentedRecordController {

	@Resource
	private RentedRecordService rentedRecordService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		return this.rentedRecordService.findList(request,pageable);
	}
}
