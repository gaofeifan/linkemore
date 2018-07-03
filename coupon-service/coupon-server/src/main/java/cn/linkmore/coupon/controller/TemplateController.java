package cn.linkmore.coupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResQrc;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.QrcService;
import cn.linkmore.coupon.service.TemplateItemService;
import cn.linkmore.coupon.service.TemplateService;

@Controller
@RequestMapping("/coupon_template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private TemplateItemService templateItemService;
	@Autowired
	QrcService qrcService;

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplate record) {
		return	this.templateService.save(record);
	}

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResTemplate detail(@RequestParam("id") Long id) {
		ResTemplate couponTemp = this.templateService.findById(id);
		List<ResTemplateItem> items = templateItemService.findList(couponTemp.getId());
		String discount = "";
		for (ResTemplateItem item : items) {
			if (Integer.valueOf(0).equals(item.getType())) {
				discount += "<div>" + item.getFaceAmount().doubleValue() + "元立减券" + item.getQuantity() + "张有效期"
						+ item.getValidDay() + "天    " + "<div><hr>";
			} else if (Integer.valueOf(1).equals(item.getType())) {
				discount += "<div>" + "满" + item.getConditionAmount().doubleValue() + "元减"
						+ item.getFaceAmount().doubleValue() + "元" + item.getQuantity() + "张有效期" + item.getValidDay()
						+ "天" + "<div><hr>";
			} else if (Integer.valueOf(2).equals(item.getType())) {
				discount += "<div>" + item.getDiscount() / 10.0 + "折停车券最高减"+ item.getFaceAmount().doubleValue() +"元"+ item.getQuantity() + "张有效期"
						+ item.getValidDay() + "天    " + "<div><hr>";
			}
		}
		couponTemp.setItems(items);
		couponTemp.setDiscount(discount);
		return couponTemp;
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplate record) {
		return	this.templateService.update(record);
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.templateService.delete(ids.get(0));
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.templateService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.templateService.findPage(pageable);
	}

	/*
	 * 启用
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id) {
		return	this.templateService.start(id);
	}

	/*
	 * 禁用
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int down(@RequestParam("id") Long id) {
		return	this.templateService.stop(id);
	}

	/**
	 * 下载二维码
	 */
	@RequestMapping(value = "/v2.0/download", method = RequestMethod.GET)
	@ResponseBody
	public ResQrc download(@RequestParam("id") Long id) {
		ResQrc qrc = qrcService.findByTempId(id);
		return qrc;
	}
}
