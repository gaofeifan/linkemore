package cn.linkmore.ops.coupon.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResQrc;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.ops.coupon.service.TemplateService;

@Controller
@RequestMapping("/admin/coupon_template_pull")
public class TemplatePullController {

	@Autowired
	private TemplateService templateService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqTemplate record) {
		ViewMsg msg = null;
		try {
			this.templateService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResTemplate detail(@RequestBody Long id) {
		return this.templateService.findById(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqTemplate record) {
		ViewMsg msg = null;
		try {
			this.templateService.update(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.templateService.delete(ids.get(0));
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(String property, String value, Long id) {
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setProperty(property);
		reqCheck.setValue(value);
		reqCheck.setId(id);
		return this.templateService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.templateService.findPage(pageable);
	}

	/*
	 * 启用
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.templateService.start(id);
			msg = new ViewMsg("启用成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("启用失败", false);
		}
		return msg;
	}

	/*
	 * 禁用
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg down(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.templateService.stop(id);
			msg = new ViewMsg("暂停成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("暂停失败", false);
		}
		return msg;
	}

	/**
	 * 下载二维码
	 */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void download(Long id, HttpServletResponse response) {
		ResQrc qrc = this.templateService.download(id);
		BufferedInputStream bis = null;
		BufferedOutputStream  bos = null;
		ServletOutputStream out = null;
		URL url;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String dateNowStr = sdf.format(new Date());
			String[] split = qrc.getUrl().split("\\.");
			String fileType = split[split.length-1];
			url = new URL(qrc.getUrl());
			URLConnection con = url.openConnection();
			bis = new BufferedInputStream(con.getInputStream());
			response.setContentType("multipart/form-data");
			response.setHeader("Content-disposition", "attachment;filename="+dateNowStr+"."+fileType); 
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream(); 
			bos = new BufferedOutputStream (out);
			byte[] buffer = new byte[1];  
			while(bis.read(buffer)!=-1){     
                bos.write(buffer);     
            }   
			bos.flush();
            out.flush();   
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bis!=null){
				try {bis.close();} catch (IOException e) { e.printStackTrace();}
			}
			if(out!=null){
				try {out.close();} catch (IOException e) {}
			}
			if(bos!=null){
				try {bos.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
}
