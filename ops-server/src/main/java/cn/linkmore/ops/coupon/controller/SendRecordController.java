package cn.linkmore.ops.coupon.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqSendRecord;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.ops.coupon.service.SendRecordService;
import cn.linkmore.ops.coupon.service.TemplateService;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.ExcelRead;

@Controller
@RequestMapping("/admin/coupon_send_record")
public class SendRecordController {

	@Autowired
	private SendRecordService sendRecordService;
	
	@Autowired
	private TemplateService templateService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqSendRecord record) {
		ViewMsg msg = null;
		try {
			String[] list = record.getPhoneJson().split(",");
			ResTemplate couponTemplate = this.templateService.findById(record.getTemplateId());
			int surplusQuantity = couponTemplate.getTotalQuantity() - couponTemplate.getSendQuantity();
			if(list.length > surplusQuantity){
				msg = new ViewMsg("发放用户数大于剩余数量", false);
				return msg;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtils.isNotBlank(record.getTaskTimeStr())){
				record.setTaskTime(sdf.parse(record.getTaskTimeStr()));
				if(new Date().compareTo(record.getTaskTime()) > 0){
					msg = new ViewMsg("定时时间小于当前时间", false);
					return msg;
				}
			}
			this.sendRecordService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.sendRecordService.findPage(pageable);
	}
	

	@RequestMapping(value = "/saveBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg saveBusiness(ReqSendRecord record,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
			record.setCreatorId(person.getId().intValue());
			record.setCreatorName(person.getUsername());
			this.sendRecordService.saveBusiness(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			if(e.getMessage().contains("金额不足0")){
				msg = new ViewMsg("0", false);
			}else if(e.getMessage().contains("金额不足1")){
				msg = new ViewMsg("1", false);
			}else{
				msg = new ViewMsg("保存失败", false);
			}
		}
		return msg;
	}
	
	@RequestMapping(value = "/import_excel", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			List<Map<String, Object>> rows = new ArrayList<>();
			Map<String, Object> row = null;
			List<String> mobileList = new ArrayList<String>();
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					row = new HashMap<>();
					if (StringUtils.isNotBlank(cell.get(0))) {
						row.put("phone", cell.get(0));
						mobileList.add(cell.get(0));
					} else {
						msg = new ViewMsg("导入失败，手机号不能为空", false);
					}
					rows.add(row);
				}
			}
			int count = 0;
			for (Map<String, Object> r : rows) {
				count++;
			}
			if (count != 0) {
				msg = new ViewMsg("共有" + count + "条数据", true);
				msg.add("mobileList", mobileList);
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/importExcelBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcelBusiness(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			List<Map<String, Object>> rows = new ArrayList<>();
			Map<String, Object> row = null;
			List<String> mobileList = new ArrayList<String>();
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					row = new HashMap<>();
					if (StringUtils.isNotBlank(cell.get(0))) {
						row.put("phone", cell.get(0));
						mobileList.add(cell.get(0));
					} else {
						msg = new ViewMsg("导入失败，手机号不能为空", false);
					}
					rows.add(row);
				}
			}
			int count = 0;
			for (Map<String, Object> r : rows) {
				count++;
			}
			if (count != 0) {
				msg = new ViewMsg("共有" + count + "条数据", true);
				msg.add("mobileList", mobileList);
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}

}
