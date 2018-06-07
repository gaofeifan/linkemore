package cn.linkmore.ops.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.OrderOperateLogService;
import cn.linkmore.ops.utils.ExcelUtil;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;

/**
 * Controller - 车位订单操作记录
 * @author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/admin/admin/order_operate")
public class OrderOperateLogController {
	
	@Autowired
	private OrderOperateLogService orderOperateService;
	
	/*
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.orderOperateService.findPage(pageable);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrderOperateLogEntity> detail(@RequestBody Long id){
		return this.orderOperateService.findListById(id);
	}
	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqOrderOperateLogExcel bean,HttpServletResponse response){
		List<ResOrderOperateLogEntity> list = this.orderOperateService.exportList(bean);
		try {
			JSONArray ja = new JSONArray();
	        Map<String,Object> s = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        for (ResOrderOperateLogEntity rb : list) {
	        	s = new HashMap<String,Object>();
	        	s.put("orderNo", rb.getOrderNo());
	        	s.put("stallName", rb.getStallName());
	        	s.put("source", rb.getSource()>1?"APP":"后台");
	        	if(null==rb.getOperation() || rb.getOperation()==1){
	        		s.put("operation", "挂起");
	        	}else{
	        		s.put("operation", "关闭");
	        	}
	        	s.put("operator", rb.getOperator());
	        	if(null == rb.getStatus() || rb.getStatus()==0){
	        		s.put("status", "失败");
	        	}else{
	        		s.put("status", "成功");
	        	}
	        	if(null==rb.getCreateTime()){
	        		s.put("createTime", "");
	        	}else{
	        		s.put("createTime", sdf.format(rb.getCreateTime()));
	        	}
	        	ja.add(s);
			}
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("orderNo", "订单号");
	        headMap.put("stallName", "车位名称");
	        headMap.put("source", "来源");
	        headMap.put("operation", "操作");
	        headMap.put("operator", "操作人");
	        headMap.put("status", "状态");
	        headMap.put("createTime", "创建时间");
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
            String title = "车位订单操作日志信息";
            ExcelUtil.exportExcel(title,headMap,ja,null,0,os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content); 
            response.reset(); 
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); 
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xlsx").getBytes(), "iso-8859-1"));
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
