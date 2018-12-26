package cn.linkmore.account.controller.ops;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.account.service.UserGroupDetailService;
import cn.linkmore.account.service.UserGroupInputService;
import cn.linkmore.account.service.UserGroupService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;


/**
 * 用户分组
 * @author LLH
 *
 */
@RestController
@RequestMapping("/user_group")
public class UserGroupController {

	@Resource
	private UserGroupService userGroupService;
	@Resource
	private UserGroupDetailService userGroupDetailService;
	@Resource
	private UserGroupInputService userGroupInputService;

	public ObjectMapper mapper= new ObjectMapper();
	
	/**
	 * 保存分组
	 * @param reqUserGroup
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqUserGroup reqUserGroup) {
		int count=this.userGroupService.insert(reqUserGroup);
		if (StringUtils.isNotEmpty(reqUserGroup.getUserIds())) {
			String[] userIdsArray= reqUserGroup.getUserIds().split(",");
			List<Long> userIdsList= new ArrayList<Long>();
			for(String userId: userIdsArray) {
				userIdsList.add(Long.parseLong(userId));
				ReqUserGroupDetail userGroupDetail=new ReqUserGroupDetail();
				userGroupDetail.setUserId(Long.parseLong(userId));
				userGroupDetail.setUserGroupId(reqUserGroup.getId());
				userGroupDetail.setCreateUserId(reqUserGroup.getCreateUserId());
				userGroupDetail.setCreateTime(new Date());
				userGroupDetailService.insert(userGroupDetail);
			}
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("userGroupId", reqUserGroup.getId());
			map.put("inputType", (byte) 1);
			map.put("createUserId", reqUserGroup.getCreateUserId());
			map.put("createUserName", reqUserGroup.getCreateUserName());
			map.put("createTime", new Date());
			map.put("updateUserId", reqUserGroup.getUpdateUserId());
			map.put("updateUserName", reqUserGroup.getUpdateUserName());
			map.put("updateTime", new Date());
			map.put("userIds", userIdsList);
			userGroupInputService.insertByUserIds(map);
		}
		
		return count;
		
	}

	/**
	 * 更新分组
	 * @param reqUserGroup
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqUserGroup reqUserGroup) {
		return this.userGroupService.updateByPrimaryKeySelective(reqUserGroup);
	}

	/**
	 * 删除分组
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int deleteByIds(@RequestBody List<Long> ids) {
		this.userGroupDetailService.deleteByGroupIds(ids);
		return this.userGroupService.deleteByIds(ids);
	}

	/**
	 * 更改分组状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map) {
		return this.userGroupService.updateStatus(map);
	}

	/**
	 * 分组列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.userGroupService.findPage(pageable);
	}

	/**
	 * 分组列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserGroup> findList(@RequestBody Map<String, Object> map){
		return this.userGroupService.findList(map);
	}

	/**
	 * 根据id获取一条分组记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResUserGroup getByPrimarkey(@RequestBody Long id) {
		return this.userGroupService.selectByPrimaryKey(id);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 用户列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage userList(@RequestBody ViewPageable pageable) {
		return this.userGroupDetailService.findPage(pageable);
	}
	/**
	 * 保存用户
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	@ResponseBody
	public int userSave(@RequestBody ReqUserGroupDetail reqUserGroupDetail) {
		return this.userGroupDetailService.insert(reqUserGroupDetail);
	}
	/**
	 * 按ID删除用户
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/user/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public int deleteUserByIds(@RequestBody List<Long> ids) {
		return this.userGroupDetailService.deleteByIds(ids);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 录入列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/input/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage inputList(@RequestBody ViewPageable pageable) {
		return this.userGroupInputService.findPage(pageable);
	}
	/**
	 * 保存
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/input/save", method = RequestMethod.POST)
	@ResponseBody
	public int inputSave(@RequestBody ReqUserGroupInput reqUserGroupInput) {
		return this.userGroupInputService.insert(reqUserGroupInput);
	}

	/**
	 * 保存用户-通过userid
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/input/saveByUserIds", method = RequestMethod.POST)
	@ResponseBody
	public int userSaveByUserIds (@RequestParam Map<String, Object> map) {
		List<Long> ids=null;
		try {
			ids = new ObjectMapper().readValue(String.valueOf(map.get("ids")),new TypeReference<List<Long>>() { });
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		map.put("userIds", ids);
		map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong( (String)map.get("createTime") )));
		map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong( (String)map.get("updateTime") )));
		
		
		userGroupDetailService.insertByUserIds(map);
		return this.userGroupInputService.insertByUserIds(map);
	}

	/**
	 * 修改
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/input/update", method = RequestMethod.POST)
	@ResponseBody
	public int inputUpdate(@RequestBody ReqUserGroupInput reqUserGroupInput) {
		return this.userGroupInputService.updateByPrimaryKeySelective(reqUserGroupInput);
	}
	
	/**
	 * 按ID删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/input/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public int inputDeleteByIds(@RequestBody List<Long> ids) {
		return this.userGroupInputService.deleteByIds(ids);
	}

	/**
	 * 按ID删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/input/deleteAndUserByIds", method = RequestMethod.POST)
	@ResponseBody
	public int deleteAndUserByIds(@RequestBody List<Long> ids) {
		return this.userGroupInputService.deleteAndUserByIds(ids);
	}
	
	/**
	 * 判断是否存在
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/input/exists", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputExists(@RequestBody ReqUserGroupInput reqUserGroupInput ) {
		return this.userGroupInputService.exists(reqUserGroupInput);
	}
	
	/**
	 * 同步 一个用户分组的数据
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/input/sync/byUserGroupId", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputSyncByUserGroupId(@RequestBody Long UserGroupId ) {
		return this.userGroupInputService.syncByUserGroupId(UserGroupId);
	}
	
	/**
	 * 同步 一个用户的数据
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/input/sync/byUserId", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputSyncByUserId(@RequestBody Long UserId ) {
		return this.userGroupInputService.syncByUserId(UserId);
	}

	/**
	 * 同步 一个车牌的数据
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/input/sync/byPlate", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputSyncByPlate(@RequestBody String plate ) {
		return this.userGroupInputService.syncByPlate(plate);
	}
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/input/pageUserByNotInUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage pageUserByNotInUserGroup(@RequestBody ViewPageable pageable) {
		return this.userGroupInputService.pageUserByNotInUserGroup(pageable);
	}
	
}
