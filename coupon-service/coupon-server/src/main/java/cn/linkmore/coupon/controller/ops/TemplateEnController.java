package cn.linkmore.coupon.controller.ops;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResQrc;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.service.QrcService;
import cn.linkmore.coupon.service.TemplateEnService;

@Controller
@RequestMapping("/ops/coupon_enterprise")
public class TemplateEnController {
	
	@Autowired
	private TemplateEnService templateEnService;
	@Autowired
	QrcService qrcService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplate record) {
		record.setDeleteStatus(0);
		this.templateEnService.save(record);
		return 0;
	}
	@RequestMapping(value = "/v2.0/saveBusiness", method = RequestMethod.POST)
	@ResponseBody
	public int saveBusiness(@RequestBody ReqTemplate record) {
		record.setDeleteStatus(0);
		this.templateEnService.saveBusiness(record);
		return 0;
	}

	public static TemplateItem initCouponTemplateItem2(JSONObject json) {
		TemplateItem item = new TemplateItem();
		if (json != null) {
			Integer type = json.getInteger("couponItemType");
			BigDecimal faceAmount = new BigDecimal(0);
			BigDecimal conditionAmount = new BigDecimal(0);
			if(json.containsKey("source")){
				item.setSource(json.getInteger("source"));
			}
			if (type == 0) {
				if(item.getSource() != null && item.getSource().equals(0)){
					faceAmount = json.getBigDecimal("faceAmount");
				}else{
					faceAmount = json.getBigDecimal("faceAmount_se");
				}
			} else if (type == 1) {
				if(item.getSource() != null && item.getSource().equals(0)){
					conditionAmount = json.getBigDecimal("conditionAmount");
				}else{
					conditionAmount = json.getBigDecimal("conditionAmount_se");
				}
				faceAmount = json.getBigDecimal("mj_faceAmount");
			} else if (type == 2) {
				if(item.getSource() != null && item.getSource().equals(0)){
					faceAmount = json.getBigDecimal("zk_faceAmount");
				}else{
					faceAmount = json.getBigDecimal("zk_faceAmount_se");
				}
			}
			Integer quantity = json.getInteger("quantity");
			Integer validDay = json.getInteger("couponValidDay");
			Integer discount = json.getInteger("item_discount");
			item.setConditionAmount(conditionAmount);
			item.setDiscount(discount);
			item.setFaceAmount(faceAmount);
			item.setType(type);
			item.setQuantity(quantity);
			item.setValidDay(validDay);
		}
		return item;
	}
	public static TemplateItem initCouponTemplateItem(JSONObject json) {
		TemplateItem item = new TemplateItem();
		if (json != null) {
			Long couponItemId = json.getLong("couponItemId");
			Integer type = json.getInteger("couponItemType");
			BigDecimal faceAmount = new BigDecimal(0);
			if(json.containsKey("source")){
				item.setSource(json.getInteger("source"));
			}
			if (type == 0) {
					faceAmount = json.getBigDecimal("faceAmount");
			} else if (type == 1) {
					faceAmount = json.getBigDecimal("mj_faceAmount");
			} else if (type == 2) {
					faceAmount = json.getBigDecimal("zk_faceAmount");
			}
			Integer quantity = json.getInteger("quantity");
			Integer validDay = json.getInteger("couponValidDay");
			Integer discount = json.getInteger("item_discount");
			BigDecimal conditionAmount = json.getBigDecimal("conditionAmount");
			if(couponItemId != null){
				item.setId(couponItemId);
			}
			item.setConditionAmount(conditionAmount);
			item.setDiscount(discount);
			item.setFaceAmount(faceAmount);
			item.setType(type);
			item.setQuantity(quantity);
			item.setValidDay(validDay);
		}
		return item;
	}
	public static TemplateItem initCouponTemplateItemUpdate(JSONObject json,String deleteIds) {
		TemplateItem item = new TemplateItem();
		if (json != null) {
			Long couponItemId = json.getLong("couponItemId");
			Integer type = json.getInteger("couponItemType");
			BigDecimal faceAmount = new BigDecimal(0);
			if(json.containsKey("source")){
				item.setSource(json.getInteger("source"));
			}
			if (type == 0) {
				faceAmount = json.getBigDecimal("faceAmount");
			} else if (type == 1) {
				faceAmount = json.getBigDecimal("mj_faceAmount");
			} else if (type == 2) {
				faceAmount = json.getBigDecimal("zk_faceAmount");
			}
			Integer quantity = json.getInteger("quantity");
			Integer validDay = json.getInteger("couponValidDay");
			Integer discount = json.getInteger("item_discount");
			BigDecimal conditionAmount = json.getBigDecimal("conditionAmount");
			if(couponItemId != null){
				item.setId(couponItemId);
			}
			item.setConditionAmount(conditionAmount);
			item.setDiscount(discount);
			item.setFaceAmount(faceAmount);
			item.setType(type);
			item.setQuantity(quantity);
			item.setValidDay(validDay);
		}
		return item;
	}
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResTemplate detail(@RequestParam("id") Long id) {
		ResTemplate couponTemp = this.templateEnService.findById(id);
		return couponTemp;
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplate record) {
		this.templateEnService.update(record);
		return 0;
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.templateEnService.delete(ids.get(0));
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.templateEnService.check(reqCheck.getProperty(),reqCheck.getValue(), reqCheck.getId());
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.templateEnService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/listBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage listBusiness(@RequestBody ViewPageable pageable) {
		return this.templateEnService.findPage(pageable);
	}

	/*
	 * 启用
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id) {
		this.templateEnService.start(id);
		return	0;
	}

	/*
	 * 禁用
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int down(@RequestParam("id") Long id) {
		this.templateEnService.stop(id);
		return 0;
	}
	
	@RequestMapping(value = "/v2.0/selectByEnterpriseId", method = RequestMethod.GET)
	@ResponseBody
	public List<ResTemplate> findByEnterpriseId(@RequestParam("id") Long id) {
		//此处传递id为当前登录人的id
		//Person person = getPerson(request);
		//personId = person.getId();
		List<ResTemplate> list = this.templateEnService.selectByEnterpriseId(id);
		return list;
	}

	
	/**
	 * 下载二维码
	 */
	@RequestMapping(value = "/v2.0/download", method = RequestMethod.POST)
	public void download(@RequestParam("id") Long id,HttpServletResponse response){
		ResQrc qrc = qrcService.findByTempId(id);
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
