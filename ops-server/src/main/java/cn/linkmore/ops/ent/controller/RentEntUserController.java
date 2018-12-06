package cn.linkmore.ops.ent.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.ops.biz.controller.BaseController;
import cn.linkmore.ops.ent.service.RentEntUserService;
import cn.linkmore.util.ExcelRead;

@RestController
@RequestMapping("/admin/ent/rent-ent-user")
public class RentEntUserController extends BaseController{

	@Resource
	private RentEntUserService rentEntUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list( ViewPageable pageable) {
		return this.rentEntUserService.findPage(pageable);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void save( ReqRentEntUser ent) {
		this.rentEntUserService.save(ent);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public void update( ReqRentEntUser ent) {
		this.rentEntUserService.update(ent);
	}
	
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids) {
		this.rentEntUserService.deleteIds(ids);
	}
	

	@RequestMapping(value = "/i-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage listI( ViewPageable pageable) {
		return this.rentEntUserService.findPageI(pageable);
	}
	
	@RequestMapping(value = "/i-add", method = RequestMethod.POST)
	@ResponseBody
	public void saveI( ReqRentUser ent) {
		this.rentEntUserService.saveI(ent);
	}
	
	@RequestMapping(value = "/i-edit", method = RequestMethod.POST)
	@ResponseBody
	public void updateI( ReqRentUser ent) {
		this.rentEntUserService.updateI(ent);
	}
	
	@RequestMapping(value = "/i-ids", method = RequestMethod.POST)
	@ResponseBody
	public void deleteI(@RequestBody List<Long> ids) {
		this.rentEntUserService.deleteIdsI(ids);
	}
	
	@RequestMapping(value = "/importExcel",method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file, 
								//@RequestParam("prefectureId") long prefectureId, 
								@RequestParam("companyId") long companyId) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			
			long userId = getPerson().getId();
			String userName = getPerson().getUsername();
/*
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("createUserId", userId);
			List<ResPreList> findPreList = preService.findSelectListByUser(map);
			if(findPreList != null && findPreList.size()>0) {
				preId=findPreList.get(0).getId();
			}
*/
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					if (StringUtils.isNotBlank(cell.get(0))) {
						ReqRentEntUser rentEntUser=new ReqRentEntUser();
						rentEntUser.setPlate(cell.get(0));
						rentEntUser.setMobile(cell.get(1));
						//rentEntUser.setUserName(userName);
						rentEntUser.setRentComId(companyId);
						rentEntUser.setCreateUserId(userId);
						rentEntUser.setCreateUserName(userName);
						rentEntUser.setCreateTime(new Date());
						rentEntUser.setUpdateUserId(userId);
						rentEntUser.setUpdateUserName(userName);
						rentEntUser.setUpdateTime(new Date());
						rentEntUser.setStatus(1);
						if(!rentEntUserService.exists(rentEntUser)) {
							rentEntUserService.save(rentEntUser);
						}
					}
				}
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}
}
