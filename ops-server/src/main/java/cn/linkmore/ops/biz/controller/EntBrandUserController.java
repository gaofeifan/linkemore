package cn.linkmore.ops.biz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EntBrandUserService;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.util.ExcelRead;
/**
 * 品牌授权用户
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/ent-brand-user")
public class EntBrandUserController {
	
	@Resource
	private EntBrandUserService entBrandUserService;
	
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource
	private UserClient userClient;
	
	public static final String REGEX_MOBILE = "^((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))\\d{8}$";

	private ResUser getUser(String phone) {
		ResUser user = null;
		user = this.userClient.getUserByUserName(phone);
		if (user != null) {
			return user;
		} else {
			user = new ResUser();
			user.setMobile(phone);
			user.setUsername(phone);
			user.setStatus("0");
			user.setUserType("1");
			user.setCreateTime(new Date());
			return this.userClient.save(user);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqEntBrandUser record) {
		ViewMsg msg = null;
		try {
			this.entBrandUserService.save(record);
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
	public ViewMsg update(ReqEntBrandUser record) {
		ViewMsg msg = null;
		try {
			this.entBrandUserService.update(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.entBrandUserService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.entBrandUserService.findPage(pageable);
	}
	
	public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
	@RequestMapping(value = "/import_excel", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			List<Map<String, Object>> rows = new ArrayList<>();
			Map<String, Object> row = null;
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					row = new HashMap<>();
					if (StringUtils.isNotBlank(cell.get(0))) {
						row.put("entName", cell.get(0));
					} else {
						msg = new ViewMsg("导入失败，企业名称不能为空", false);
					}
					if (StringUtils.isNotBlank(cell.get(1))) {
						row.put("realname", cell.get(1));
					} else {
						msg = new ViewMsg("导入失败，姓名不能为空", false);
					}
					if (StringUtils.isNotBlank(cell.get(2))) {
						if(isMobile(cell.get(2).toString())) {
							row.put("mobile", cell.get(2));
						}else {
							msg = new ViewMsg("导入失败，手机号无效", false);
						}
						
					} else {
						msg = new ViewMsg("导入失败，手机号不能为空", false);
					}
					if (StringUtils.isNotBlank(cell.get(3))) {
						row.put("plateNo", cell.get(3));
					} else {
						msg = new ViewMsg("导入失败，车牌号不能为空", false);
					}
					rows.add(row);
				}
			}
			
			int count = 0;
			List<ReqEntBrandUser> reqUserList = new ArrayList<ReqEntBrandUser>();
			List<ResEnterprise> enterpriseList = enterpriseService.selectAll();
			Map<String,Object> map = new HashMap<String,Object>();
			if(CollectionUtils.isNotEmpty(enterpriseList)) {
				for(ResEnterprise ent : enterpriseList) {
					map.put(ent.getName(), ent.getId());
				}
			}
			ReqEntBrandUser reqBrandUser = null;
			for (Map<String, Object> r : rows) {
				if(map.get(r.get("entName")) != null && r.get("mobile") != null && r.get("entName") != null) {
					Long entId = (Long) map.get(r.get("entName"));
					reqBrandUser = new ReqEntBrandUser();
					reqBrandUser.setEntId(entId);
					reqBrandUser.setMobile(r.get("mobile").toString());
					reqBrandUser.setPlateNo(r.get("plateNo").toString());
					reqBrandUser.setRealname(r.get("realname").toString());
					ResUser user = getUser(reqBrandUser.getMobile());
					if(user != null) {
						reqBrandUser.setUserId(user.getId());
					}
					reqUserList.add(reqBrandUser);
					this.entBrandUserService.insertBatch(reqUserList);
					count ++;
				}else {
					if(r.get("mobile") != null) {
						msg = new ViewMsg("导入失败，企业不存在", false);
					}
				}
			}
			if (count != 0) {
				msg = new ViewMsg("导入成功,其中有" + count + "条数据已存在", true);
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}
	
}
