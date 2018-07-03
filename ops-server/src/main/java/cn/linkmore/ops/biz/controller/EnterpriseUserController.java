package cn.linkmore.ops.biz.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.ops.biz.service.EnterpriseUserService;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ExcelRead;
import cn.linkmore.util.ExcelUtil;

/**
 * 企业定制用户
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/enterprise_user")
public class EnterpriseUserController {

	@Resource
	private EnterpriseUserService enterpriseUserService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		return enterpriseUserService.findPage(pageable, person);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(HttpServletRequest request, ReqEnterpriseUser enterpriseUser) {
		ViewMsg msg = null;
		try {
			enterpriseUserService.update(enterpriseUser);
			msg = new ViewMsg("修改成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(HttpServletRequest request, Long id) {
		ViewMsg msg = null;
		ResEnterpriseUser enterpriseUser = null;
		try {
			enterpriseUser = enterpriseUserService.getEnterpriseUser(id);
			if (null == enterpriseUser) {
				msg = new ViewMsg("删除失败", false);
			} else {
				enterpriseUserService.delete(enterpriseUser);
				msg = new ViewMsg("删除成功", true);
			}
		} catch (RuntimeException e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ViewMsg msg = null;
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		List<ReqEnterpriseUser> entAll = new ArrayList<>();
		ReqEnterpriseUser ep = null;
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			for (List<String> m : list) {
				if (m != null && m.size() > 0) {
					String mobile = m.get(0);
					String plateNo = m.get(1);
					if (StringUtils.isBlank(plateNo) || StringUtils.isBlank(mobile)) {
						msg = new ViewMsg("导入失败,请检查是数据是否完整", false);
						return msg;
					}
					if (!DomainUtil.isChinaPhoneLegal(mobile)) {
						msg = new ViewMsg("导入失败,请检查手机号格式："+mobile+"", false);
						return msg;					}
					if (!DomainUtil.checkPlateNo(plateNo)) {
						msg = new ViewMsg("导入失败,请检车牌号格式:"+plateNo+"", false);
						return msg;
					}
					ep = new ReqEnterpriseUser();
					ep.setMobile(mobile);
					ep.setEnterpriseId(person.getId());
					ep.setPlateNo(plateNo.toUpperCase());
					entAll.add(ep);
				}
			}
			enterpriseUserService.saveAll(entAll);
			msg = new ViewMsg("导入成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}

	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(HttpServletResponse response, @RequestParam("userName") String userName) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		try {
			JSONArray rows = new JSONArray();
			Map<String, Object> line = null;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String title = "定制用户表";
			Map<String, String> titleMap = new LinkedHashMap<String, String>();
			titleMap.put("mobile", "用户手机号");
			titleMap.put("plate", "车牌号");
			Map<String, Object> param = new HashMap<>();
			param.put("entId", person.getId());
			if (StringUtils.isNotBlank(userName) && !"-1".equals(userName)) {
				param.put("userName", userName);
			}
			List<ResEnterpriseUser> resList = enterpriseUserService.findExcel(param);
			for (ResEnterpriseUser user : resList) {
				line = new HashMap<>();
				line.put("mobile", user.getMobile());
				line.put("plate", user.getPlateNo());
				rows.add(line);
			}
			ExcelUtil.exportExcel(title, titleMap, rows, null, 0, os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((title + ".xlsx").getBytes(), "iso-8859-1"));
			response.setContentLength(content.length);
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[8192];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
