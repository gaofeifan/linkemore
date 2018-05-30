package cn.linkmore.ops.controller;

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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;

import cn.linkmore.account.response.ResWechatFans;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqWechatFansExcel;
import cn.linkmore.ops.service.WechatFansService;
import cn.linkmore.ops.utils.ExcelUtil;
import io.swagger.annotations.Api;
/**
 * 微信粉
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@RestController
@Api(tags = "wechat_fans", description = "微信粉")
@RequestMapping("/wechat_fans")
public class WechatFansController {
	
	@Autowired
	private WechatFansService wechatFansService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(HttpServletRequest request,@RequestBody ViewPageable pageable){
		ViewPage page = this.wechatFansService.findPage(pageable);
		return ResponseEntity.success(page, request);
	} 
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ResponseEntity<?> export(@RequestBody ReqWechatFansExcel bean,HttpServletResponse response,HttpServletRequest request){ 
		List<ResWechatFans> list = this.wechatFansService.exportList(bean);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		ServletOutputStream sos  = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try { 
	        JSONArray ja = new JSONArray();
	        Map<String,Object> s = null; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for(ResWechatFans fans:list){
	            s = new HashMap<String,Object>();
	            s.put("nickname", fans.getNickname());
		        s.put("city",fans.getCity()+fans.getDistrict());
		        s.put("subscribeStatus",this.parseStatus(fans.getSubscribeStatus()));
		        s.put("uid", fans.getUid()==null?"未注册":"已注册");
		        s.put("sex",this.parseSex(fans.getSex())); 
		        s.put("createTime", sdf.format(fans.getCreateTime()));
	            ja.add(s);
	        } 
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("nickname", "昵称");
	        headMap.put("city","地区");
	        headMap.put("subscribeStatus", "关注状态");
	        headMap.put("uid", "注册状态");
	        headMap.put("sex","性别"); 
	        headMap.put("createTime", "关注时间");
            baos = new ByteArrayOutputStream();
            String title = "微信粉丝信息";
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
		return ResponseEntity.success(null, request);
	}
	private String parseSex(Integer sex){
		String text = "保密"; 
		if(sex==null){
			return text;
		}
  		if(sex==1){
  			text = "先生";
  		}if(sex==2){
  			text = "女士";
  		} 
		return text;
	}
	private String parseStatus(Integer status){
		String text = "已关注"; 
  		if(status==0){
  			text = "取消关注";
  		}if(status==10001){
  			text = "捷峻";
  		} 
		return text;
	}
}
