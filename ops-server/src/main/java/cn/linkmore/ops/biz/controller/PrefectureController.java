package cn.linkmore.ops.biz.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.response.ResDistrict;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.ops.utils.ExcelUtil;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResFeeStrategy;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;

/**
 * 
 * @Description - 专区
 * @Author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/admin/biz/prefecture")
public class PrefectureController {

	@Autowired
	private PrefectureService preService;

	/*@Resource
	private EnterpriseService enterpriseService;*/

	/*
	 * 专区信息列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.preService.findPage(pageable);
	}

	/*
	 * 删除专区
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.preService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	/*
	 * 检查名称重复
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck) {
		return this.preService.check(reqCheck);
	}

	/*
	 * 专区下拉列表
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> selectList() {
		return this.preService.findSelectList();
	}

	/*
	 * 计费策略列表
	 */
	@RequestMapping(value = "/strategy_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResFeeStrategy> strategyList() {
		return this.preService.findStrategyList();
	}

	/*
	 * 计费系统列表
	 */
	@RequestMapping(value = "/bill_system", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOldDict> billSystem() {
		return this.preService.findBillSystemList();
	}

	/*
	 * 城市列表
	 */
	@RequestMapping(value = "/city_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> cityList() {
		return this.preService.findCityList();
	}

	/*
	 * 区域列表
	 */
	@RequestMapping(value = "/district_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResDistrict> districtList(@RequestBody Long cityId) {
		return this.preService.findDistrictList(cityId);
	}

	/*
	 * 新增
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqPrefectureEntity prefecture) {
		ViewMsg msg = null;
		try {
			this.preService.save(prefecture);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 根据id获取一条数据
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResPrefectureDetail detail(@RequestBody Long id) {
		return this.preService.findById(id);
	}

	/*
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqPrefectureEntity prefecture) {
		ViewMsg msg = null;
		try {
			this.preService.update(prefecture);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqPreExcel bean, HttpServletResponse response) {
		List<ResPreExcel> list = this.preService.exportList(bean);
		try {
			JSONArray ja = new JSONArray();
			Map<String, Object> s = null;
			Map<Integer, Object> str = new HashMap<>();
			str.put(0, "启用");
			str.put(1, "禁用");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (ResPreExcel rb : list) {
				s = new HashMap<String, Object>();
				s.put("name", rb.getName());
				s.put("strategyName", rb.getStrategyName());
				s.put("firstHourDisplay", rb.getFirstHourDisplay());
				s.put("basePriceDisplay", rb.getBasePriceDisplay());
				s.put("nightPriceDisplay", rb.getNightPriceDisplay());
				s.put("dateContract", sdf.format(rb.getDateContract()));
				s.put("validTime", sdf.format(rb.getValidTime()));
				s.put("stallTotal", rb.getStallTotal());
				s.put("orderIndex", rb.getOrderIndex());
				s.put("status", str.get(rb.getStatus()));
				ja.add(s);
			}
			Map<String, String> headMap = new LinkedHashMap<String, String>();
			headMap.put("name", "名称");
			headMap.put("strategyName", "计费策略");
			headMap.put("firstHourDisplay", "首小时价格");
			headMap.put("basePriceDisplay", "首小时外价格");
			headMap.put("nightPriceDisplay", "晚间价格");
			headMap.put("dateContract", "签约时间");
			headMap.put("validTime", "有效期");
			headMap.put("stallTotal", "车位总数");
			headMap.put("orderIndex", "排序等级");
			headMap.put("status", "状态");
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			String title = "专区信息";
			ExcelUtil.exportExcel(title, headMap, ja, null, 0, os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((title + ".xlsx").getBytes(), "iso-8859-1"));
			response.setContentLength(content.length);
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[8192];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 区域列表
	 */
	/*@RequestMapping(value = "/select_ent", method = RequestMethod.POST)
	@ResponseBody
	public List<Enterprise> findEntprise() {
		Map<String, Object> param = new HashMap<>();
		List<Enterprise> find = enterpriseService.find(param);
		return find;
	}*/

	/*
	 * 专区列表
	 */
	@RequestMapping(value = "/find_city", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>>findByCity(@RequestBody Long cityId) {
		return preService.findByCity(cityId);
	}

}
