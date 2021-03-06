package cn.linkmore.ops.biz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EntBrandAdService;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.prefecture.response.ResPreList;
/**
 * 企业品牌广告
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/ent-brand-ad")
public class EntBrandAdController {
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource
	private EntBrandAdService entBrandAdService;
	
	@Autowired
	private PrefectureService preService;
	
	@Resource
	private BaseDictClient baseDictClient;
	
	@Resource
	private CouponClient couponClient;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqEntBrandAd record) {
		ViewMsg msg = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("entId", record.getEntId());
			map.put("id", new Date().getTime());
			int count = this.entBrandAdService.count(map);
			if(count > 0 ) {
				msg = new ViewMsg("当前企业品牌广告已存在", false);
			}else {
				this.entBrandAdService.save(record);
				msg = new ViewMsg("保存成功", true);
			}
			
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
	public ViewMsg update(ReqEntBrandAd record) {
		ViewMsg msg = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("entId", record.getEntId());
			map.put("id", record.getId());
			int count = this.entBrandAdService.count(map);
			if(count > 0 ) {
				msg = new ViewMsg("当前企业品牌广告已存在", false);
			}else {
				this.entBrandAdService.update(record);
				msg = new ViewMsg("保存成功", true);
			}
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
			this.entBrandAdService.delete(ids);
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
		return this.entBrandAdService.findPage(pageable);
	}
	
	/**
	 * 企业优惠券列表
	 * @return
	 */
	@RequestMapping(value = "/temp-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTemplate> tempList(HttpServletRequest request, Long entId) {
		return this.couponClient.findEntTemplateList(entId);
	}
	
	/**
	 * 企业列表
	 * @return
	 */
	@RequestMapping(value = "/ent-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEnterprise> selectAll() {
		return this.enterpriseService.selectAll();
	}
	
	/*
	 * 专区下拉列表
	 */
	@RequestMapping(value = "/pre-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> selectList() {
		return this.preService.findSelectList();
	}
	
	/*
	 * 城市列表
	 */
	@RequestMapping(value = "/city-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> cityList() {
		return this.preService.findCityList();
	}
	
	/*
	 * 周期列表
	 */
	@RequestMapping(value = "/period-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResBaseDict> periodList() {
		return this.baseDictClient.findList("period");
	}
	
	/*
	 * 根据id获取一条数据
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResBrandAd detail(@RequestBody Long id) {
		return this.entBrandAdService.findById(id);
	}
	
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg changedUp(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.entBrandAdService.start(id);
			msg = new ViewMsg("启用成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg(e.getMessage(), false);
			return msg;
		} catch (Exception e) {
			msg = new ViewMsg("启用失败", false);
			return msg;
		}
		return msg;
	}

	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg changedDown(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.entBrandAdService.stop(id);
			msg = new ViewMsg("禁用成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg(e.getMessage(), false);
			return msg;
		} catch (Exception e) {
			msg = new ViewMsg("禁用失败", false);
			return msg;
		}
		return msg;
	}

}
