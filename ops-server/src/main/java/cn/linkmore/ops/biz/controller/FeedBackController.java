package cn.linkmore.ops.biz.controller;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.FeedBackService;
import cn.linkmore.ops.request.ReqFeedBack;
import cn.linkmore.ops.utils.ExcelUtil;

/**
 * 问题反馈
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz_feedback")
public class FeedBackController {

	@Resource
	private FeedBackService feedBackService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		ViewPage page = feedBackService.findPage(pageable);
		return page;
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export( ReqFeedBack bean,HttpServletResponse response){ 
		List<ResFeedBack> list = this.feedBackService.exportList(bean);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		ServletOutputStream sos  = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try { 
	        JSONArray ja = new JSONArray();
	        Map<String,Object> s = null; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        for(ResFeedBack feedBack:list){
	            s = new HashMap<String,Object>();
	            s.put("mobile", feedBack.getMobile());
		        s.put("nickname",feedBack.getNickname());
		        s.put("content",feedBack.getContent());
		        s.put("createTime", sdf.format(feedBack.getCreateTime()));
	            ja.add(s);
	        } 
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("mobile", "用户手机号");
	        headMap.put("nickname", "昵称");
	        headMap.put("content", "内容");
	        headMap.put("createTime", "日期");
            baos = new ByteArrayOutputStream();
            String title = "问题反馈信息";
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

}
