package cn.linkmore.ops.ent.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.ops.biz.controller.BaseController;
import cn.linkmore.ops.ent.service.EntUserPlateService;
import cn.linkmore.prefecture.client.OpsPrefectureClient;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.ExcelRead;

/**
 * 企业免费车牌
 * @author   jiaohanbin
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/ent/ent-user-plate")
public class EntUserPlateController extends BaseController {

	@Resource
	private EntUserPlateService userPlateService;
	
	@Resource
	private OpsPrefectureClient prefectureClient;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		return this.userPlateService.findList(request,pageable);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqEntUserPlate plate) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
			
			plate.setEntId(person.getEntId());
			plate.setCreateTime(new Date());
			plate.setCreateUserId(person.getId());
			plate.setCreateUserName(person.getUsername());
			
			Map<String, Object> param = new HashMap<>();
			param.put("createUserId", person.getEntId());
			List<ResPrefectureDetail> preList = prefectureClient.findList(param);
			if(CollectionUtils.isNotEmpty(preList)) {
				plate.setPreId(preList.get(0).getId());
			}else {
				throw new BusinessException(StatusEnum.PREFECTURE_NOT_EXIST);
			}
			Map<String,Object> checkParam = new HashMap<String,Object>();
			checkParam.put("preId", plate.getPreId());
			checkParam.put("plateNo", plate.getPlateNo());
			//this.userPlateService.save(userPlate);
			if(!userPlateService.exists(checkParam)) {
				this.userPlateService.save(plate);
				msg = new ViewMsg("保存成功", true);
			}else {
				msg = new ViewMsg("当前车牌已存在", false);
			}
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqEntUserPlate plate) {
		ViewMsg msg = null;
		try {
			this.userPlateService.update(plate);
			msg = new ViewMsg("更新成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("更新失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.userPlateService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/importExcel",method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		Map<String, Object> checkParam = null;
		Long preId = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
			Map<String, Object> param = new HashMap<>();
			param.put("createUserId", person.getId());
			List<ResPrefectureDetail> preList = prefectureClient.findList(param);
			if(CollectionUtils.isNotEmpty(preList)) {
				preId = preList.get(0).getId();
			}else {
				throw new BusinessException(StatusEnum.PREFECTURE_NOT_EXIST);
			}
			
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			long userId = getPerson().getId();
			String userName = getPerson().getUsername();
			Set<String> plateSet = new HashSet<String>();
			int count=0;
			List<ReqEntUserPlate> plateList = new ArrayList<ReqEntUserPlate>();
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					if (StringUtils.isNotBlank(cell.get(0))) {
						ReqEntUserPlate userPlate=new ReqEntUserPlate();
						if(!plateSet.contains(cell.get(0))) {
							plateSet.add(cell.get(0));
							userPlate.setPlateNo(cell.get(0));
							userPlate.setMobile(cell.get(1));
							userPlate.setCreateUserId(userId);
							userPlate.setCreateUserName(userName);
							userPlate.setCreateTime(new Date());
							userPlate.setPreId(preId);
							if(person.getEntId()!=null) {
								userPlate.setEntId(person.getEntId());
							}							
							checkParam = new HashMap<String,Object>();
							checkParam.put("preId", userPlate.getPreId());
							checkParam.put("plateNo", userPlate.getPlateNo());
							//this.userPlateService.save(userPlate);
							if(!userPlateService.exists(checkParam)) {
								plateList.add(userPlate);
								count++;
							}
							
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(plateList)) {
				int num = userPlateService.saveBatch(plateList);
			}
			msg = new ViewMsg(String.format("导入成功！</br>一共导入%s个车牌</br>其中成功导入:%s个车牌",(list !=null?list.size():0) ,count), true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}
	
}
