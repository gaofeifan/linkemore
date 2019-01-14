package cn.linkmore.ops.account.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.UserGroupService;
import cn.linkmore.ops.biz.controller.BaseController;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.ExcelRead;

/**
 * 用户分组
 * 
 * @author llh
 *
 */
@RestController
@RequestMapping("/admin/account/group")
public class UserGroupController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqUserGroup reqUserGroup) {
		ViewMsg msg = null;
		try {
			reqUserGroup.setCreateUserId(getPerson().getId());
			reqUserGroup.setCreateUserName(getPerson().getUsername());
			reqUserGroup.setCreateTime(new Date());
			reqUserGroup.setUpdateUserId(getPerson().getId());
			reqUserGroup.setUpdateUserName(getPerson().getUsername());
			reqUserGroup.setUpdateTime(new Date());
			reqUserGroup.setStatus((byte) 1);
			this.userGroupService.insert(reqUserGroup);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqUserGroup reqUserGroup) {
		ViewMsg msg = null;
		try {
			
			reqUserGroup.setUpdateUserId(getPerson().getId());
			reqUserGroup.setUpdateUserName(getPerson().getUsername());
			reqUserGroup.setUpdateTime(new Date());
			
			this.userGroupService.updateByPrimaryKey(reqUserGroup);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteByIds(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.userGroupService.deleteByIds(ids);
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
	 * 更新状态 开启
	 * 
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg statusStart(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 2);
			map.put("updateTime", sdf.format(new Date()));
			map.put("updateUserId", getPerson().getId());
			map.put("updateUserName", getPerson().getUsername());
			map.put("ids", ids);
			this.userGroupService.updateStatus(map);
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
	 * 
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg statusStop(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 1);
			map.put("updateTime", sdf.format(new Date()));
			map.put("updateUserId", person.getId());
			map.put("updateUserName", person.getUsername());
			map.put("ids", ids);
			this.userGroupService.updateStatus(map);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}

	/**
	 * 用户分组列表-分页
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable) {
		return this.userGroupService.findPage(pageable);
	}

	/**
	 * 用户分组列表-无分页
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserGroup> findList(@RequestParam Map<String, Object> map) {
		return this.userGroupService.findList(map);
	}

	/**
	 * 根据id获取一条记录
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResUserGroup getByPrimarkey(@RequestBody Long id) {
		return this.userGroupService.getByPrimarkey(id);
	}

	/**
	 * 分组用户列表-分页
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage userList(ViewPageable pageable) {
		return this.userGroupService.userFindPage(pageable);
	}

	/**
	 * 保存分组用户
	 * 
	 * @param reqUserGroupDetail
	 * @return
	 */
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	@ResponseBody
	public int userSave(ReqUserGroupDetail reqUserGroupDetail) {
		reqUserGroupDetail.setCreateUserId(getPerson().getId());
		reqUserGroupDetail.setCreateTime(new Date());
		return this.userGroupService.userInsert(reqUserGroupDetail);
	}

	/**
	 * 按ID删除分组用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteUserByIds(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.userGroupService.userDeleteByIds(ids);
		msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}


	@RequestMapping(value = "/input/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage inputList(ViewPageable pageable) {
		return this.userGroupService.inputFindPage(pageable);
	};

	@RequestMapping(value = "/input/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg inputSave(ReqUserGroupInput reqUserGroupInput){
		ViewMsg msg = null;
		try {
			reqUserGroupInput.setCreateUserId(getPerson().getId());
			reqUserGroupInput.setCreateUserName(getPerson().getUsername());
			reqUserGroupInput.setCreateTime(new Date());
			reqUserGroupInput.setUpdateUserId(getPerson().getId());
			reqUserGroupInput.setUpdateUserName(getPerson().getUsername());
			reqUserGroupInput.setUpdateTime(new Date());
			if (!userGroupService.inputExists(reqUserGroupInput)) {
				this.userGroupService.inputInsert(reqUserGroupInput);
				msg = new ViewMsg("保存成功", true);
			}else {
				msg = new ViewMsg("该分组下手机号+车牌号重复!", false);
			}
			
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	};
	
	@RequestMapping(value = "/input/saveByUserIds", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg inputSaveByUserIds(@RequestParam Map<String, Object> map){
		ViewMsg msg = null;
		try {
			//map.put("userGroupId", reqUserGroup.getId());
			map.put("inputType", (byte) 1);
			map.put("createUserId", getPerson().getId());
			map.put("createUserName", getPerson().getUsername());
			map.put("createTime", new Date().getTime());
			map.put("updateUserId", getPerson().getId());
			map.put("updateUserName", getPerson().getUsername());
			map.put("updateTime", new Date().getTime());
			//map.put("userIds", userIdsList);
			userGroupService.inputSaveByUserIds(map);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	};
	
	@RequestMapping(value = "/input/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg inputUpdate(ReqUserGroupInput reqUserGroupInput){
		ViewMsg msg = null;
		try {
			reqUserGroupInput.setUpdateUserId(getPerson().getId());
			reqUserGroupInput.setUpdateUserName(getPerson().getUsername());
			reqUserGroupInput.setUpdateTime(new Date());
			this.userGroupService.inputUpdateByPrimaryKey(reqUserGroupInput);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	};
	
	@RequestMapping(value = "/input/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg inputDeleteByIds(@RequestBody List<Long> ids){
		ViewMsg msg = null;
		try {
			this.userGroupService.inputDeleteByIds(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	};
	
	@RequestMapping(value = "/input/deleteAndUserByIds", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteAndUserByIds(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.userGroupService.inputDeleteAndUserByIds(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
 
	}
	
	@RequestMapping(value = "/input/exists", method = RequestMethod.POST)
	@ResponseBody
	public boolean inputExists(@RequestBody ReqUserGroupInput reqUserGroupInput){
		return this.userGroupService.inputExists(reqUserGroupInput);
	};
	
	@RequestMapping(value = "/input/import/excel", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg userImportExcel(@RequestParam("file") MultipartFile file,
			@RequestParam("userGroupId") long userGroupId) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			long userId = getPerson().getId();
			String userName = getPerson().getUsername();
			int count = 0;
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					if (StringUtils.isNotBlank(cell.get(0))) {
						ReqUserGroupInput reqUserGroupInput = new ReqUserGroupInput();

						reqUserGroupInput.setCreateUserId(userId);
						reqUserGroupInput.setCreateUserName(userName);
						reqUserGroupInput.setCreateTime(new Date());
						reqUserGroupInput.setUpdateUserId(userId);
						reqUserGroupInput.setUpdateUserName(userName);
						reqUserGroupInput.setUpdateTime(new Date());

						reqUserGroupInput.setUserGroupId(userGroupId);
						reqUserGroupInput.setPlate(cell.get(0));
						reqUserGroupInput.setMobile(cell.get(1));
						reqUserGroupInput.setInputType((byte) 2);
						if (!userGroupService.inputExists(reqUserGroupInput)) {
							this.userGroupService.inputInsert(reqUserGroupInput);
							count++;
						}
					}
				}
			}
			msg = new ViewMsg(
					String.format("导入成功！</br>一共导入%s个车牌</br>其中成功导入:%s个车牌", (list != null ? list.size() : 0), count),
					true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}
	
	
	@RequestMapping(value = "/input/sync/byUserGroupId", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg syncByUserGroupId(@RequestBody Long userGroupId) {
		ViewMsg msg = new ViewMsg("操作成功", true);
		try {
			boolean ret=this.userGroupService.syncByUserGroupId(userGroupId);
			if(!ret) {
				msg = new ViewMsg("操作失败", false);
			}
		} catch (Exception e) {
			msg = new ViewMsg("操作失败", false);
		}
		return msg;
	}
	
	/**
	 * 用户分页-不包括当前分组的用户-
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/input/pageUserByNotInUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage pageUserByNotInUserGroup(ViewPageable pageable) {
		return this.userGroupService.pageUserByNotInUserGroup(pageable);
	}
	
}
