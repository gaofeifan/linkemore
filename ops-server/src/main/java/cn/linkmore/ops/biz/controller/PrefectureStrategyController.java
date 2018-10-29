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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PrefectureStrategyService;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.prefecture.request.ReqPrefectureLockTime;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.request.ReqPrefectureStrategyGroup;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.response.ResStrategyFee;


/**
 * Controller - 车区策略
 * 
 * @author lilinhai
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/admin/biz/prefecture_strategy")

public class PrefectureStrategyController {
	@Autowired
	private PrefectureStrategyService prefectureStrategyService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private ObjectMapper mapper= new ObjectMapper();
	
	/**
	 * 新增时段
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody

	public ViewMsg save(ReqPrefectureStrategy reqPrefectureStrategy) {
		ViewMsg msg = null;
		try {
			reqPrefectureStrategy.setCreateTime(new Date());
			reqPrefectureStrategy.setUpdateTime(new Date());
			reqPrefectureStrategy.setStatus((byte)1);

			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");

			reqPrefectureStrategy.setCreateUserId(person.getId());
			reqPrefectureStrategy.setCreateUserName(person.getUsername());
			reqPrefectureStrategy.setUpdateUserId(person.getId());
			reqPrefectureStrategy.setUpdateUserName(person.getUsername());
			
			if(StringUtils.isNotEmpty(reqPrefectureStrategy.getJsonStrategyGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqPrefectureStrategyGroup.class); 
				 List<ReqPrefectureStrategyGroup> reqReqPrefectureStrategyGroup =  mapper.readValue(reqPrefectureStrategy.getJsonStrategyGroup(), javaType);   //这里不需要强制转换
				 reqPrefectureStrategy.setStrategyGroup(reqReqPrefectureStrategyGroup);
			}
			
			if(StringUtils.isNotEmpty(reqPrefectureStrategy.getJsonLockTime())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqPrefectureLockTime.class); 
				 List<ReqPrefectureLockTime> reqReqPrefectureLockTime =  mapper.readValue(reqPrefectureStrategy.getJsonLockTime(), javaType);   //这里不需要强制转换
				 reqPrefectureStrategy.setLockTime(reqReqPrefectureLockTime);
			}
			
			this.prefectureStrategyService.save(reqPrefectureStrategy);
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
	public ViewMsg update(ReqPrefectureStrategy reqPrefectureStrategy) {
		ViewMsg msg = null;
		try {
			
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			
			reqPrefectureStrategy.setUpdateUserId(person.getId());
			reqPrefectureStrategy.setUpdateUserName(person.getUsername());			
			reqPrefectureStrategy.setUpdateTime(new Date());
			
			if(StringUtils.isNotEmpty(reqPrefectureStrategy.getJsonStrategyGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqPrefectureStrategyGroup.class); 
				 List<ReqPrefectureStrategyGroup> reqReqPrefectureStrategyGroup =  mapper.readValue(reqPrefectureStrategy.getJsonStrategyGroup(), javaType);   //这里不需要强制转换
				 reqPrefectureStrategy.setStrategyGroup(reqReqPrefectureStrategyGroup);
			}
			
			if(StringUtils.isNotEmpty(reqPrefectureStrategy.getJsonLockTime())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqPrefectureLockTime.class); 
				 List<ReqPrefectureLockTime> reqReqPrefectureLockTime =  mapper.readValue(reqPrefectureStrategy.getJsonLockTime(), javaType);   //这里不需要强制转换
				 reqPrefectureStrategy.setLockTime(reqReqPrefectureLockTime);
			}
			
			this.prefectureStrategyService.update(reqPrefectureStrategy);
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
			
			
			this.prefectureStrategyService.updateStatus(map);
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
			this.prefectureStrategyService.updateStatus(map);
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
			this.prefectureStrategyService.delete(ids);
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
		return this.prefectureStrategyService.findPage(pageable);
	}

	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResPrefectureStrategyNew getByPrimarkey(@RequestBody Long id) {
		return this.prefectureStrategyService.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取计费策略列表
	 * @return
	 */
	@RequestMapping(value = "/strategy_fee/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyFee> findList(){
		return this.prefectureStrategyService.findList();
	}
	/**
	 * 验证运营时段
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/validate/time", method = RequestMethod.POST)
	@ResponseBody
	public int validateTime(@RequestParam Map<String,String> map) {
		return this.prefectureStrategyService.validateTime(map);
	}

	/**
	 * 验证运营 分期策略是否交叉
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/validate/date", method = RequestMethod.POST)
	@ResponseBody
	public int validateDate(@RequestParam Map<String,String> map) {
		return this.prefectureStrategyService.validateDate(map);
	}
	
}
