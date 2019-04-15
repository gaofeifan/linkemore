package cn.linkmore.ops.ent.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.controller.BaseController;
import cn.linkmore.ops.ent.service.AuthRecordService;

/**
 * 授权记录
 * @author   jhb
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/ent/auth-record")
public class AuthRecordController extends BaseController{

	@Resource
	private AuthRecordService authRecordService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"preId",getPerson().getPreId()));
		return this.authRecordService.findList(request,pageable);
	}
	
	/**
	 * 开启
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg open(Long authId) {
		ViewMsg msg = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", authId);
			map.put("switchFlag", 1);
			map.put("updateTime", sdf.format(new Date()));
			int a =authRecordService.operateSwitch(map);
			msg= a>0?new ViewMsg("操作成功", true):new ViewMsg("操作失败", false);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("操作失败", false);
		}
		return msg;
	}
	
	/**
	 * 关闭
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg close(Long authId) {
		ViewMsg msg = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", authId);
			map.put("switchFlag", (short)0);
			map.put("updateTime", sdf.format(new Date()));
			int a =authRecordService.operateSwitch(map);
			msg= a>0?new ViewMsg("操作成功", true):new ViewMsg("操作失败", false);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("操作失败", false);
		}
		return msg;
	}
	
	
}
