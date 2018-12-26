package cn.linkmore.account.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.UserGroupClientHystrix;
import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;

@FeignClient(value = "account-server", path = "/user_group", fallback=UserGroupClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserGroupClient {

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqUserGroup reqUserGroup);

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqUserGroup reqUserGroup);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int deleteByIds(@RequestBody List<Long> ids);
	
	/**
	 * 更改状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map);

	/**
	 * 用户分组列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	/**
	 * 用户分组列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserGroup> findList(@RequestBody Map<String, Object> map);

	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResUserGroup getByPrimarkey(@RequestBody Long id);


	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage userList(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	@ResponseBody
	public int userSave(@RequestBody ReqUserGroupDetail reqUserGroupDetail);

	@RequestMapping(value = "/user/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public int deleteUserByIds(@RequestBody List<Long> ids);

	
	@RequestMapping(value = "/input/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage inputList(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/input/save", method = RequestMethod.POST)
	@ResponseBody
	public int inputSave(@RequestBody ReqUserGroupInput reqUserGroupInput);
	
	@RequestMapping(value = "/input/saveByUserIds", method = RequestMethod.POST)
	@ResponseBody
	public int inputSaveByUserIds(@RequestParam Map<String, Object> map);
	
	@RequestMapping(value = "/input/update", method = RequestMethod.POST)
	@ResponseBody
	public int inputUpdate(@RequestBody ReqUserGroupInput reqUserGroupInput);
	
	@RequestMapping(value = "/input/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public int inputDeleteByIds(@RequestBody List<Long> ids);

	@RequestMapping(value = "/input/deleteAndUserByIds", method = RequestMethod.POST)
	@ResponseBody
	public int inputDeleteAndUserByIds(List<Long> ids);
	
	@RequestMapping(value = "/input/exists", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputExists(@RequestBody ReqUserGroupInput reqUserGroupInput);

	@RequestMapping(value = "/input/sync/byUserGroupId", method = RequestMethod.POST)
	@ResponseBody
	public boolean syncByUserGroupId(Long userGroupId);
	
	@RequestMapping(value = "/input/sync/byUserId", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputSyncByUserId(@RequestBody Long UserId );
	
	@RequestMapping(value = "/input/sync/byPlate", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputSyncByPlate(@RequestBody String plate );

	
	@RequestMapping(value = "/input/pageUserByNotInUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage pageUserByNotInUserGroup(ViewPageable pageable);




}
