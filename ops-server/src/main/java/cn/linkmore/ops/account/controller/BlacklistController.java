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

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.BlacklistService;
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
	

}
