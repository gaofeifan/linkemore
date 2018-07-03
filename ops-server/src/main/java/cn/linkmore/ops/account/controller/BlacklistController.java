package cn.linkmore.ops.account.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import cn.linkmore.account.response.ResUserBlacklist;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.BlacklistService;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.util.ExcelUtil;

/**
 * Controller - 权限模块 - 类记录
 * @author liwenlong
 * @version 1.0
 *
 */ 
@Controller
@RequestMapping("/admin/account/blacklist")
public class BlacklistController {
	
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private PrefectureService prefectureService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.blacklistService.findPage(pageable); 
	} 
	
/*	@RequestMapping(value = "/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<PrefectureBean> list(){
		return this.blacklistService.findPreList(); 
	}  */
	
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@ResponseBody
	public Boolean status(){ 
		return this.blacklistService.status();
	}  
	
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg open() {
		return this.blacklistService.open();
	}
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg close() {
		return this.blacklistService.close();
	}
	
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg enable(@RequestBody List<Long> list){ 
		return this.blacklistService.enable(list);
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(HttpServletResponse response){  
		List<ResPrefecture> pres = this.prefectureService.findPreList();
		Map<Long,String> map = new HashMap<Long,String>();
		for(ResPrefecture pb:pres){
			map.put(pb.getId(), pb.getName());
		}
		List<ResUserBlacklist> list = this.blacklistService.findList();
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		ServletOutputStream sos  = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try { 
	        JSONArray ja = new JSONArray();
	        Map<String,Object> s = null; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for(ResUserBlacklist bl:list){
	            s = new HashMap<String,Object>();
	            s.put("username", bl.getUsername());
	            s.put("prefecture",this.parsePrefecture(map, bl));
	            s.put("totalOrderCount", bl.getTotalOrderCount());
	            s.put("couponCount", bl.getCouponCount());
	            s.put("couponValidate",sdf.format(bl.getCreateTime()));
	            s.put("cashOrderCount", bl.getCashOrderCount()); 
	            s.put("limitStatus", bl.getLimitStatus()==1?"已禁用":"已解锁");
	            ja.add(s);
	        } 
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("username", "编号");
	        headMap.put("prefecture","常规使用地");
	        headMap.put("totalOrderCount", "交易次数");
	        headMap.put("couponCount", "停车券数量");
	        headMap.put("couponValidate","停车券截止日期");
	        headMap.put("cashOrderCount", "现金交易次数"); 
	        headMap.put("limitStatus", "受限状态");
            baos = new ByteArrayOutputStream();
            String title = "预约受限名单"+sdf.format(new Date());
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
	

	private String parsePrefecture(Map<Long,String> map,ResUserBlacklist bl){
		StringBuffer content = new StringBuffer("");
		String name1 = map.get(bl.getMaxPreId());
		String name2 = map.get(bl.getMinPreId());
		if(StringUtils.isNotBlank(name1)){
			content.append(name1);
		}
		if(StringUtils.isNotBlank(name2)){
			if(!"".equals(content.toString())){
				content.append(",");
			}
			content.append(name2);
		}
		return content.toString();
	}
}
