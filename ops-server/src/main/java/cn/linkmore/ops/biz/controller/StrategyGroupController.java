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
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StrategyGroupService;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;


/**
 * Controller - 分组策略
 * 
 * @author lilinhai
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/admin/biz/strategy/group")

public class StrategyGroupController {
	@Autowired
	private StrategyGroupService strategyGroupService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private ObjectMapper mapper= new ObjectMapper();
	
	/**
	 * 新增时段
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStrategyGroup reqStrategyGroup) {
		ViewMsg msg = null;
		try {
			reqStrategyGroup.setCreateTime(new Date());
			reqStrategyGroup.setUpdateTime(new Date());
			reqStrategyGroup.setStatus((byte)1);

			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");

			reqStrategyGroup.setCreateUserId(person.getId());
			reqStrategyGroup.setCreateUserName(person.getUsername());
			reqStrategyGroup.setUpdateUserId(person.getId());
			reqStrategyGroup.setUpdateUserName(person.getUsername());

			if(StringUtils.isNotEmpty(reqStrategyGroup.getStallGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqStrategyGroupDetail.class); 
				 List<ReqStrategyGroupDetail> reqStrategyGroupDetail =  mapper.readValue(reqStrategyGroup.getStallGroup(), javaType);   //这里不需要强制转换
				 reqStrategyGroup.setStrategyGroupDetail(reqStrategyGroupDetail);
			}
			this.strategyGroupService.save(reqStrategyGroup);
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
	public ViewMsg update(ReqStrategyGroup reqStrategyGroup) {
		ViewMsg msg = null;
		try {
			
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			
			reqStrategyGroup.setUpdateUserId(person.getId());
			reqStrategyGroup.setUpdateUserName(person.getUsername());			
			reqStrategyGroup.setUpdateTime(new Date());

			this.strategyGroupService.update(reqStrategyGroup);
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
			
			
			this.strategyGroupService.updateStatus(map);
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
			this.strategyGroupService.updateStatus(map);
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
			this.strategyGroupService.delete(ids);
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
	 * 删除分组中的车位
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/stall/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteStall(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.strategyGroupService.deleteStall(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/stall/exists", method = RequestMethod.POST)
	@ResponseBody
	public Long existsStall(@RequestParam Map<String,Object> map) {
		return this.strategyGroupService.existsStall(map);
	}

	/**
	 * 添加一个车位
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stall/add", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg addStall(ReqStrategyGroupDetail reqStrategyGroupDetail) {
		ViewMsg msg = null;
		try {
			this.strategyGroupService.addStall(reqStrategyGroupDetail);
			msg = new ViewMsg("添加成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("添加失败", false);
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
		return this.strategyGroupService.findPage(pageable);
	}
	/*
	 * 信息列表-无分页
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyGroup> findList() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		return this.strategyGroupService.findList(param);
	}
	
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyGroup getByPrimarkey(@RequestBody Long id) {
		return this.strategyGroupService.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 获取分区信息，以及分区下的车位信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/area/list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyGroupArea> selectStallByPrimaryKey(@RequestBody Long id) {
		return this.strategyGroupService.selectStallByPrimaryKey(id);
	}
	
	/**
	 * 获取分组树
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree findTree(@RequestParam("preId") Integer preId, @RequestParam("parkingInterval") Integer parkingInterval) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("preId", preId);
		param.put("parkingInterval", parkingInterval);
		return this.strategyGroupService.findTree(param);
	}
	
	/**
	 * 根据preId,areaId,startName,endName 获取车区信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/findAreaStall", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findAreaStall(@RequestParam Map<String, Object> param) {
		return this.strategyGroupService.findAreaStall(param);
	}
	
	
}
