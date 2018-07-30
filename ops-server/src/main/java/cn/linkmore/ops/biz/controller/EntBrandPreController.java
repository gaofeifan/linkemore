package cn.linkmore.ops.biz.controller;

import java.util.List;
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
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EntBrandPreService;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.prefecture.response.ResFeeStrategy;
import cn.linkmore.prefecture.response.ResPreList;
/**
 * 企业品牌车区
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/ent-brand-pre")
public class EntBrandPreController {
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource
	private EntBrandPreService entBrandPreService;
	
	@Autowired
	private PrefectureService preService;
	
	@Resource
	private BaseDictClient baseDictClient;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqEntBrandPre record) {
		ViewMsg msg = null;
		try {
			this.entBrandPreService.save(record);
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
	public ViewMsg update(ReqEntBrandPre record) {
		ViewMsg msg = null;
		try {
			this.entBrandPreService.update(record);
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
			this.entBrandPreService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg changedUp(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.entBrandPreService.start(id);
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
			this.entBrandPreService.stop(id);
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

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.entBrandPreService.findPage(pageable);
	}
	
	/*
	 * 根据id获取一条数据
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResBrandPre detail(@RequestBody Long id) {
		return this.entBrandPreService.findById(id);
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
	 * 计费策略列表
	 */
	@RequestMapping(value = "/strategy-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResFeeStrategy> strategyList() {
		return this.preService.findStrategyList();
	}
	
	/*
	 * 周期列表
	 */
	@RequestMapping(value = "/period-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResBaseDict> periodList() {
		return this.baseDictClient.findList("period");
	}

}
