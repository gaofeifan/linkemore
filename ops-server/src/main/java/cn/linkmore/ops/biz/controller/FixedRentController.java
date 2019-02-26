package cn.linkmore.ops.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResStall;
import cn.linkmore.ops.biz.service.FixedRentService;
import cn.linkmore.util.ExcelRead;

/**
 * 固定车位管理
 * @author kobe
 *
 */

@RestController
@RequestMapping("/admin/biz/fixed/rent")
public class FixedRentController  extends BaseController{
	
	@Autowired
	private FixedRentService fixedRentService;

	/**
	 * 固定车位列表分页
	 * @param request
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		/*
		if(getPerson().getEntId()!=null && getPerson().getEntId()>0) {
			pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"entId",getPerson().getEntId()));
		}
		*/
		System.out.println("session:ent_id="+getPerson().getEntId());
		System.out.println("session:pre_id="+getPerson().getPreId());
		
		if(getPerson().getPreId()!=null && getPerson().getPreId()>0) {
			pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"preId",getPerson().getPreId()));
		}
		
		return this.fixedRentService.findPage(pageable);
	}

	/**
	 * 车位列表
	 * @param request
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(@RequestParam Map<String, Object> map) {
		map.put("preId", getPerson().getPreId());
		return fixedRentService.stallList(map);
	}

	/**
	 * 空闲车位列表
	 * @param request
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/free_stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> freeStallList(@RequestParam Map<String, Object> map) {
		map.put("preId", getPerson().getPreId());
		return fixedRentService.freeStallList(map);
	}
	
	private boolean isValidPlate(String plate) {
		final String[] values= {"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))"
                ,"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$"};
		final int[] lengths={8,7};
		for (int i = 0; i < lengths.length; i++) {
			if (plate.length() == lengths[i]) {
				Pattern pattern = Pattern.compile(values[i]);
				Matcher matcher = pattern.matcher(plate);
				return matcher.matches();
			}
		}
		return false;
	}
	private boolean isValidPlates(String plates) {
		String[] arrayPlate = plates.split(",");
		for (String plate : arrayPlate) {
			if(!isValidPlate(plate)) {
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * 新增
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqFixedRent reqFixedRent) {
		ViewMsg msg = null;
		try {
			if (StringUtils.isNotEmpty(reqFixedRent.getPlateNos())) {
				if(!isValidPlates(reqFixedRent.getPlateNos())) {
					return new ViewMsg("车牌号不正确", true); 
				}
			}
			
			String checkMsg=fixedRentService.check(reqFixedRent);
			if(StringUtils.isNotEmpty(checkMsg)) {
				return new ViewMsg(checkMsg, false);
			}
			
			reqFixedRent.setEntId(getPerson().getEntId());
			reqFixedRent.setEntName(getPerson().getEntName());
			reqFixedRent.setPreId(getPerson().getPreId());

			reqFixedRent.setCreateUserId(getPerson().getId());
			reqFixedRent.setUpdateUserId(getPerson().getId());
			reqFixedRent.setCreateTime(new Date());
			reqFixedRent.setUpdateTime(new Date());
			reqFixedRent.setStatus((short)1);	//默认开启
			
			int a =fixedRentService.insert(reqFixedRent);
			msg= a>0?new ViewMsg("保存成功", true):new ViewMsg("保存失败", false);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	private boolean isStallRepeat(List<ReqFixedRent> listReqFixedRent) {
		List<String> listStall=new ArrayList<String>();
		for(ReqFixedRent reqFixedRent:listReqFixedRent) {
			reqFixedRent.getStallNames().split(",");
			listStall.addAll(Arrays.asList(reqFixedRent.getStallNames().split(",")));
		}
		long count = listStall.stream().distinct().count();
		return count < listStall.size();
	}
	
	/**
	 * 批量添加
	 * @param file
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/batchSave",method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg batchSave(@RequestBody List<ReqFixedRent> listReqFixedRent) {
		ViewMsg msg = new ViewMsg("批量添加成功", true);
		try {
			if(isStallRepeat(listReqFixedRent)) {
				return new ViewMsg("车位号重复", true);
			}
			for(ReqFixedRent reqFixedRent:listReqFixedRent) {
				if (StringUtils.isNotEmpty(reqFixedRent.getPlateNos())) {
					if(!isValidPlates(reqFixedRent.getPlateNos())) {
						return new ViewMsg("车牌号不正确", true); 
					}
				}
				
				String checkMsg=fixedRentService.check(reqFixedRent);
				if(StringUtils.isNotEmpty(checkMsg)) {
					return new ViewMsg(checkMsg, false);
				}
			}
			
			for(ReqFixedRent reqFixedRent:listReqFixedRent) {
				reqFixedRent.setEntId(getPerson().getEntId());
				reqFixedRent.setEntName(getPerson().getEntName());
				reqFixedRent.setPreId(getPerson().getPreId());
				reqFixedRent.setCreateUserId(getPerson().getId());
				reqFixedRent.setUpdateUserId(getPerson().getId());
				reqFixedRent.setCreateTime(new Date());
				reqFixedRent.setUpdateTime(new Date());
				reqFixedRent.setStatus((short)1);	//默认开启
				int a =fixedRentService.insert(reqFixedRent);
			}
			//msg = new ViewMsg(String.format("导入成功！</br>一共导入%s个车位,%s个车牌",0 ,0), true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("批量添加失败", false);
		}
		return msg;
	}
	
	
	/**
	 * 导入
	 * @param file
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/importExcel",method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					if (StringUtils.isNotBlank(cell.get(1))) {
						ReqFixedRent reqFixedRent=new ReqFixedRent();
						
						reqFixedRent.setLinkPhone(cell.get(0));
						reqFixedRent.setStartTime(cell.get(1));
						reqFixedRent.setEndTime(cell.get(2));
						reqFixedRent.setStallNames(cell.get(3));
						reqFixedRent.setPlateNos(cell.get(4));

						reqFixedRent.setEntId(getPerson().getEntId());
						reqFixedRent.setEntName(getPerson().getEntName());
						reqFixedRent.setPreId(getPerson().getPreId());

						reqFixedRent.setCreateUserId(getPerson().getId());
						reqFixedRent.setUpdateUserId(getPerson().getId());
						reqFixedRent.setCreateTime(new Date());
						reqFixedRent.setUpdateTime(new Date());
						reqFixedRent.setStatus((short)1);	//默认开启
						int a =fixedRentService.insert(reqFixedRent);
					}
				}	
			}
			
			//msg = new ViewMsg(String.format("导入成功！</br>一共导入%s个车位,%s个车牌",0 ,0), true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}
	
	/**
	 * 查看
	 * @param reqFixedRent
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ResFixedRent view(Long fixedId) {
		return fixedRentService.view(fixedId);
	}
	
	/**
	 * 编辑
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqFixedRent reqFixedRent) {
		ViewMsg msg = null;
		try {
			if (StringUtils.isNotEmpty(reqFixedRent.getPlateNos())) {
				if(!isValidPlates(reqFixedRent.getPlateNos())) {
					return new ViewMsg("车牌号不正确", true); 
				}
			}
			reqFixedRent.setUpdateUserId(getPerson().getId());

			int a =fixedRentService.update(reqFixedRent);
			msg= a>0?new ViewMsg("保存成功", true):new ViewMsg("保存失败", false);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	/**
	 * 删除
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/delete/stall", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg deleteStall(Long fixedId,Long stallId) {
		ViewMsg msg = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("fixedId", fixedId);
			//map.put("stallId", stallId);
			map.put("stallIds", Arrays.asList(stallId));
			int a =fixedRentService.deleteStall(map);
			if (a == -2) {
				msg=new ViewMsg("未关闭的车位禁止删除", false);
			}else {
				msg= a>0?new ViewMsg("删除成功", true):new ViewMsg("删除失败", false);
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	/**
	 * 开启
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg open(Long fixedId,Long stallId) {
		ViewMsg msg = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("fixedId", fixedId);
			map.put("stallId", stallId);
			int a =fixedRentService.open(map);
			msg= a>0?new ViewMsg("操作成功", true):new ViewMsg("操作失败", false);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("操作失败", false);
		}
		return msg;
	}
	
	/**
	 * 关闭
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg close(Long fixedId,Long stallId) {
		ViewMsg msg = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("fixedId", fixedId);
			map.put("stallId", stallId);
			int a =fixedRentService.close(map);
			msg= a>0?new ViewMsg("操作成功", true):new ViewMsg("操作失败", false);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("操作失败", false);
		}
		return msg;
	}
}
