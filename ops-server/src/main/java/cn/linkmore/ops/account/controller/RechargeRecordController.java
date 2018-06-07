package cn.linkmore.ops.account.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.RechargeRecordService;
import cn.linkmore.ops.utils.ExcelUtil;
import cn.linkmore.order.request.ReqRechargeRecordExcel;
import cn.linkmore.order.response.ResRechargeRecordExcel;

/**
 * Controller - 储值记录
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/admin/account/recharge_record")
public class RechargeRecordController {
	@Resource
	private RechargeRecordService rechargeRecordService;
	 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.rechargeRecordService.findPage(pageable); 
	}  
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqRechargeRecordExcel bean,HttpServletResponse response){ 
		List<ResRechargeRecordExcel> list = this.rechargeRecordService.exportList(bean);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		ServletOutputStream sos  = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try { 
	        JSONArray ja = new JSONArray();
	        Map<String,Object> s = null;
	        String[] str = {"代充","微信","支付宝","银联"};
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for(ResRechargeRecordExcel rrb:list){
	            s = new HashMap<String,Object>();
	            s.put("code", rrb.getCode());
	            s.put("mobile", rrb.getMobile());
	            s.put("packageAmount", rrb.getPackageAmount());
	            s.put("paymentAmount", rrb.getPackageAmount());
	            s.put("payType",str[rrb.getPayType()]);
	            s.put("payInfo", payInfo(rrb));
	            s.put("createTime", null!=rrb.getCreateTime()?sdf.format(rrb.getCreateTime()):null);
	            ja.add(s);
	        } 
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("code", "编号");
	        headMap.put("mobile","用户");
	        headMap.put("packageAmount", "集会金额");
	        headMap.put("paymentAmount", "支付金额");
	        headMap.put("payType","支付方式");
	        headMap.put("payInfo", "支付信息"); 
	        headMap.put("createTime", "创建时间");
            baos = new ByteArrayOutputStream();
            String title = "储值记录信息";
            ExcelUtil.exportExcel(title,headMap,ja,null,0,baos);
            byte[] content = baos.toByteArray();
            is = new ByteArrayInputStream(content); 
            response.reset(); 
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); 
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xlsx").getBytes(), "iso-8859-1"));
            response.setContentLength(content.length);
            sos = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(sos);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead); 
            } 
            bos.flush(); 
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if( bos!=null){
        		try{
        			bos.close();
        		}catch(Exception e){}
        	}
        	if( sos!=null){
        		try{
        			sos.close();
        		}catch(Exception e){}
        	}
        	if( bis!=null){
        		try{
        			bis.close();
        		}catch(Exception e){}
        	}
        	if(is!=null){
        		try {
					is.close();
				} catch (IOException e) {}
        	}
        	if( baos!=null){
        		try{
        			baos.close();
        		}catch(Exception e){}
        	} 
        }
	}
	private String payInfo(ResRechargeRecordExcel rrb){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(rrb.getPayStatus()){
			return "已支付"+ (null!=rrb.getPayTime()?sdf.format(rrb.getPayTime()):null);
		}else{
			return "未支付";
		}
	}
}
