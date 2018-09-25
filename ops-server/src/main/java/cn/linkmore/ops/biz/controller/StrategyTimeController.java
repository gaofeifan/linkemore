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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StrategyTimeService;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.request.ReqStrategyTimeDetail;
import cn.linkmore.prefecture.response.ResStrategyTime;


/**
 * Controller - 时段策略
 * 
 * @author lilinhai
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/admin/biz/strategy/time")

public class StrategyTimeController {
	@Autowired
	private StrategyTimeService strategyTimeService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private ObjectMapper mapper= new ObjectMapper();
	
	/**
	 * 新增时段
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStrategyTime reqStrategyTime) {
		ViewMsg msg = null;
		try {
			reqStrategyTime.setCreateTime(new Date());
			reqStrategyTime.setUpdateTime(new Date());
			reqStrategyTime.setStatus(1);

			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");

			reqStrategyTime.setCreateUserId(person.getId());
			reqStrategyTime.setCreateUserName(person.getUsername());
			reqStrategyTime.setUpdateUserId(person.getId());
			reqStrategyTime.setUpdateUserName(person.getUsername());			
			
			if(StringUtils.isNotEmpty(reqStrategyTime.getTimeGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqStrategyTimeDetail.class); 
				 List<ReqStrategyTimeDetail> reqStrategyTimeDetail =  mapper.readValue(reqStrategyTime.getTimeGroup(), javaType);   //这里不需要强制转换
				 reqStrategyTime.setStrategyTimeDetail(reqStrategyTimeDetail);
			}
			this.strategyTimeService.save(reqStrategyTime);
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
	public ViewMsg update(ReqStrategyTime reqStrategyTime) {
		ViewMsg msg = null;
		try {
			
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			
			reqStrategyTime.setUpdateUserId(person.getId());
			reqStrategyTime.setUpdateUserName(person.getUsername());			
			reqStrategyTime.setUpdateTime(new Date());
			if(StringUtils.isNotEmpty(reqStrategyTime.getTimeGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqStrategyTimeDetail.class); 
				 List<ReqStrategyTimeDetail> reqStrategyTimeDetail =  mapper.readValue(reqStrategyTime.getTimeGroup(), javaType);   //这里不需要强制转换
				 reqStrategyTime.setStrategyTimeDetail(reqStrategyTimeDetail);
			}
			this.strategyTimeService.update(reqStrategyTime);
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
			
			
			this.strategyTimeService.updateStatus(map);
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
			this.strategyTimeService.updateStatus(map);
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
			this.strategyTimeService.delete(ids);
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
		return this.strategyTimeService.findPage(pageable);
	}
	
	/**
	 * 列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyTime> findList(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		return this.strategyTimeService.findList();
	}
	
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyTime getByPrimarkey(@RequestBody Long id) {
		return this.strategyTimeService.selectByPrimaryKey(id);
	}
	
}
