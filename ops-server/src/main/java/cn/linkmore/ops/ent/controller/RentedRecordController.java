package cn.linkmore.ops.ent.controller;

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
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.ops.biz.controller.BaseController;
import cn.linkmore.ops.ent.service.RentedRecordService;
import cn.linkmore.ops.utils.ExcelUtil;

/**
 * 长租用户使用记录
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/ent/rented-record")
public class RentedRecordController extends BaseController{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private RentedRecordService rentedRecordService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		return this.rentedRecordService.findList(request,pageable);
	}
	
	@RequestMapping(value = "/mini-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage miniList(HttpServletRequest request, ViewPageable pageable) {
		pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"preId",getPerson().getPreId()));
		return this.rentedRecordService.findList(request,pageable);
	}
	
	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqRentedRecord bean, HttpServletResponse response) {
		List<ResRentedRecord> list = this.rentedRecordService.exportList(bean);
		try {
			JSONArray ja = new JSONArray();
			Map<String, Object> s = null;
			Map<Integer, Object> str = new HashMap<>();
			str.put(0, "未完成");
			str.put(1, "已完成");
			str.put(2, "失败");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (ResRentedRecord rb : list) {
				s = new HashMap<String, Object>();
				s.put("plateNo", rb.getPlateNo());
				s.put("mobile", rb.getMobile());
				s.put("preName", rb.getPreName());
				s.put("stallName", rb.getStallName());
				s.put("downTime", sdf.format(rb.getDownTime()));
				if(rb.getLeaveTime() != null) {
					s.put("leaveTime", sdf.format(rb.getLeaveTime()));
				}else {
					s.put("leaveTime", "");
				}
				s.put("status", str.get(rb.getStatus()));
				ja.add(s);
			}
			Map<String, String> headMap = new LinkedHashMap<String, String>();
			headMap.put("plateNo", "车牌号");
			headMap.put("mobile", "用户手机号");
			headMap.put("preName", "车区名称");
			headMap.put("stallName", "车位名称");
			headMap.put("downTime", "降锁时间");
			headMap.put("leaveTime", "离场时间");
			headMap.put("status", "状态");
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String title = "长租车位使用记录";
			ExcelUtil.exportExcel(title, headMap, ja, null, 0, os);
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
			log.info("message = {} stack = {}",e.getMessage(),e.getStackTrace());
			e.printStackTrace();
		}
	}
}
