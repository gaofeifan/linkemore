package cn.linkmore.ops.biz.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StrategyDateService;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.prefecture.request.ReqStrategyDate;
import cn.linkmore.prefecture.request.ReqStrategyDateDetail;
import cn.linkmore.prefecture.response.ResStrategyDate;


/**
 * Controller - 分期策略
 * 
 * @author lilinhai
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/admin/biz/strategy/date")

public class StrategyDateController {
	@Autowired
	private StrategyDateService strategyDateService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private ObjectMapper mapper= new ObjectMapper();
	/**
	 * 新增时段
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStrategyDate reqStrategyInterval) {
		ViewMsg msg = null;
		try {
			reqStrategyInterval.setCreateTime(new Date());
			reqStrategyInterval.setUpdateTime(new Date());
			reqStrategyInterval.setStatus((byte)1);

			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");

			reqStrategyInterval.setCreateUserId(person.getId());
			reqStrategyInterval.setCreateUserName(person.getUsername());
			reqStrategyInterval.setUpdateUserId(person.getId());
			reqStrategyInterval.setUpdateUserName(person.getUsername());

			if(StringUtils.isNotEmpty(reqStrategyInterval.getDateGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqStrategyDateDetail.class); 
				 List<ReqStrategyDateDetail> reqStrategyDateDetailList =  mapper.readValue(reqStrategyInterval.getDateGroup(), javaType);   //这里不需要强制转换
				 reqStrategyInterval.setStrategyDateDetail(reqStrategyDateDetailList);
				 /*
				JSONArray jsonArray = JSONObject.parseArray(reqStrategyInterval.getDateGroup());
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject json = jsonArray.getJSONObject(i);
						objectMapper.readValue(src, valueType)
					}
				}	*/
			}
			
			
			
			this.strategyDateService.save(reqStrategyInterval);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}
	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqStrategyDate reqStrategyInterval) {
		ViewMsg msg = null;
		try {
			
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			
			reqStrategyInterval.setUpdateUserId(person.getId());
			reqStrategyInterval.setUpdateUserName(person.getUsername());			
			reqStrategyInterval.setUpdateTime(new Date());

			if(StringUtils.isNotEmpty(reqStrategyInterval.getDateGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqStrategyDateDetail.class); 
				 List<ReqStrategyDateDetail> reqStrategyDateDetailList =  mapper.readValue(reqStrategyInterval.getDateGroup(), javaType);   //这里不需要强制转换
				 reqStrategyInterval.setStrategyDateDetail(reqStrategyDateDetailList);
			}

			this.strategyDateService.update(reqStrategyInterval);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	/**
	 * 更新状态 开启
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg statusStart( @RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("status", 2);
			map.put("updateTime",sdf.format(new Date()) );
			map.put("updateUserId", person.getId());
			map.put("updateUserName", person.getUsername());
			map.put("ids", ids);
			
			this.strategyDateService.updateStatus(map);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	/**
	 * 更新状态 关闭
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg statusStop(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("status", 1);
			map.put("updateTime", sdf.format(new Date()));
			map.put("updateUserId", person.getId());
			map.put("updateUserName", person.getUsername());
			map.put("ids", ids);
			this.strategyDateService.updateStatus(map);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.strategyDateService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}	
	

	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable) {
		return this.strategyDateService.findPage(pageable);
	}
	
	/**
	 * 列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyDate> findList(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 2);
		return this.strategyDateService.findList();
	}
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyDate getByPrimarkey(@RequestBody Long id) {
		return this.strategyDateService.selectByPrimaryKey(id);
	}
}
