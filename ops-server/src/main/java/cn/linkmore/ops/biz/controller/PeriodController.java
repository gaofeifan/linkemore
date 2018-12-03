/**
 * 
 */
package cn.linkmore.ops.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.ChargeClient;
import cn.linkmore.prefecture.client.PeriodClient;
import cn.linkmore.prefecture.request.ReqAbuttingHourPeriod;
import cn.linkmore.prefecture.request.ReqAbuttingPeriod;
import cn.linkmore.prefecture.request.ReqPeriodCharge;

/**
 * @author PPYX
 *
 */
@Controller
@RequestMapping("/admin/biz/period")
public class PeriodController {
	
	@Autowired
	private ChargeClient chargeClient;
	
	@Autowired
	private PeriodClient periodClient;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		ReqPeriodCharge reqPeriodCharge = new ReqPeriodCharge();
		reqPeriodCharge.setPageNo((pageable.getStart()/10)+1);
		return chargeClient.list(reqPeriodCharge);
	}
	
	
	@RequestMapping(value = "/delete-charge-id", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteChargeById(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		if(ids == null){
			msg = new ViewMsg("ID参数为空", false);
			return msg;
		}
		ReqPeriodCharge reqPeriodCharge = new ReqPeriodCharge();
		reqPeriodCharge.setId(ids.get(0));
		Map<String, Object> result  = this.chargeClient.deleteAbuttingCharge(reqPeriodCharge);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("删除成功", true);
		}else{
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/select-charge-id", method = RequestMethod.POST)
	@ResponseBody
	public Object selectChargeById(@RequestBody ReqPeriodCharge reqPeriodCharge) {
		if(reqPeriodCharge == null){
			return null;
		}
		if(reqPeriodCharge.getId() == null){
			return null;
		}
		Map<String, Object> result  = this.chargeClient.selectChargeById(reqPeriodCharge);
		if(result == null){
			return null;
		}
		return result.get("data");
	}
	
	@RequestMapping(value = "/save-charge", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg saveCharge(ReqPeriodCharge reqPeriodCharge) {
		ViewMsg msg = null;
		if(reqPeriodCharge == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		Map<String, Object> result  = this.chargeClient.saveAbuttingCharge(reqPeriodCharge);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/update-charge-id", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg updateChargeById(ReqPeriodCharge reqPeriodCharge) {
		ViewMsg msg = null;
		if(reqPeriodCharge == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		if(reqPeriodCharge.getId() == null){
			msg = new ViewMsg("ID参数为空", false);
			return msg;
		}
		Map<String, Object> result  = this.chargeClient.updateAbuttingCharge(reqPeriodCharge);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("修改成功", true);
		}else{
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/period-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage periodList(HttpServletRequest request, ViewPageable pageable) {
		if(pageable == null){
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		
		ReqAbuttingPeriod reqAbuttingPeriod = new ReqAbuttingPeriod();
		reqAbuttingPeriod.setPageNo(pageable.getStart()/10);
		reqAbuttingPeriod.setChargeId(Long.parseLong(param.get("chargeId")!=null?param.get("chargeId").toString():"0"));
		return periodClient.list(reqAbuttingPeriod);
	}
	
	
	@RequestMapping(value = "/save-period", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg savePeriod(ReqAbuttingPeriod reqAbuttingPeriod) {
		ViewMsg msg = null;
		if(reqAbuttingPeriod == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		Map<String, Object> result  = this.periodClient.saveAbuttingPeriod(reqAbuttingPeriod);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/update-period", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg updatePeriod(ReqAbuttingPeriod reqAbuttingPeriod) {
		ViewMsg msg = null;
		if(reqAbuttingPeriod == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		if(reqAbuttingPeriod.getId() == null){
			msg = new ViewMsg("ID参数为空", false);
			return msg; 
		}
		Map<String, Object> result  = this.periodClient.updateAbuttingPeriod(reqAbuttingPeriod);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete-period", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deletePeriod(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		if(ids == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		ReqAbuttingPeriod reqAbuttingPeriod = new ReqAbuttingPeriod();
		reqAbuttingPeriod.setId(ids.get(0));
		Map<String, Object> result  = this.periodClient.deleteAbuttingPeriod(reqAbuttingPeriod);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	
	@RequestMapping(value = "/period-hour-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage periodHourList(HttpServletRequest request, ViewPageable pageable) {
		if(pageable == null){
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		
		ReqAbuttingPeriod reqAbuttingPeriod = new ReqAbuttingPeriod();
		reqAbuttingPeriod.setPageNo(pageable.getStart()/10);
		reqAbuttingPeriod.setChargeId(Long.parseLong(param.get("chargeId")!=null?param.get("chargeId").toString():"0"));
		return periodClient.selectHourPeriod(reqAbuttingPeriod);
	}
	
	@RequestMapping(value = "/save-hour-period", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg saveHourPeriod(ReqAbuttingHourPeriod reqAbuttingHourPeriod) {
		ViewMsg msg = null;
		if(reqAbuttingHourPeriod == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		Map<String, Object> result  = this.periodClient.saveHourPeriod(reqAbuttingHourPeriod);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/update-hour-period", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg updateHourPeriod(ReqAbuttingHourPeriod reqAbuttingHourPeriod) {
		ViewMsg msg = null;
		if(reqAbuttingHourPeriod == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		if(reqAbuttingHourPeriod.getId() == null){
			msg = new ViewMsg("ID参数为空", false);
			return msg; 
		}
		Map<String, Object> result  = this.periodClient.updateHourPeriod(reqAbuttingHourPeriod);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete-hour-period", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteHourPeriod(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		if(ids == null){
			msg = new ViewMsg("参数为空", false);
			return msg;
		}
		ReqAbuttingHourPeriod reqAbuttingHourPeriod = new ReqAbuttingHourPeriod();
		reqAbuttingHourPeriod.setId(ids.get(0));
		Map<String, Object> result  = this.periodClient.deleteHourPeriod(reqAbuttingHourPeriod);
		if(result.get("code").toString().equals("200")){
			msg = new ViewMsg("保存成功", true);
		}else{
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

}
