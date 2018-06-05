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
import cn.linkmore.ops.admin.service.StallOperateLogService;
import cn.linkmore.ops.utils.ExcelUtil;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;

/**
 * Controller - 车位上下线
 * @author zhaoyufei
 */
@Controller
@RequestMapping("/admin/admin/stall_operate")
public class StallOperateLogController {
	
	@Autowired
	private StallOperateLogService stallOperateService;
	
	/*
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.stallOperateService.findPage(pageable);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOperateLog> detail(@RequestBody Long id){
		return this.stallOperateService.findListById(id);
	}
	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqStallOperateLogExcel bean,HttpServletResponse response){
		List<ResStallOperateLog> list = this.stallOperateService.exportList(bean);
		try {
			JSONArray ja = new JSONArray();
	        Map<String,Object> s = null;
	        String[] str = {"","后台","APP电池更换","APP车区管理","异常订单"};
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for (ResStallOperateLog rb : list) {
	        	s = new HashMap<String,Object>();
	        	s.put("stallName",rb.getStallName());
	        	s.put("source", str[rb.getSource()]);
	        	if(rb.getOperation()==1){
	        		s.put("operation","上线");
	        	}else{
	        		s.put("operation", "下线");
	        	}
	        	s.put("operator", rb.getOperator());
	        	if(null == rb.getStatus() || rb.getStatus()==0){
	        		s.put("status", "失败");
	        	}else if(rb.getStatus()==1){
	        		s.put("status", "成功");
	        	}
	        	s.put("createTime", sdf.format(rb.getCreateTime()));
	        	ja.add(s);
			}
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("stallName", "车位名称");
	        headMap.put("source", "来源");
	        headMap.put("operation", "操作");
	        headMap.put("operator", "操作人");
	        headMap.put("status", "状态");
	        headMap.put("createTime", "创建时间");
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
            String title = "上下线日志信息";
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
