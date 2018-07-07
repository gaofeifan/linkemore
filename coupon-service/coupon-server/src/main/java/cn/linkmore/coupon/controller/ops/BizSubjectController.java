package cn.linkmore.coupon.controller.ops;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.response.ResValuePack;
import cn.linkmore.coupon.service.BizSubjectService;
import cn.linkmore.coupon.service.ComboService;
import cn.linkmore.coupon.service.ValuePackService;

@Controller
@RequestMapping("/ops/coupon_subject")
public class BizSubjectController {

	@Autowired
	private BizSubjectService bizSubjectService;
	@Autowired
	private ComboService comboService;
	@Autowired
	private ValuePackService valuePackService;

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqBizSubject record) {
		return this.bizSubjectService.save(record);
	}

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResBizSubjectBean detail(@RequestParam("id") Long id) {
		ResBizSubjectBean subject = this.bizSubjectService.findById(id);
		return subject;
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqBizSubject record) {
		return this.bizSubjectService.update(record);
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.bizSubjectService.delete(ids.get(0));
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.bizSubjectService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.bizSubjectService.findPage(pageable);
	}
	
	/**
	 * 上线
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id) {
		return this.bizSubjectService.start(id);
	}

	/**
	 * 下线
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int down(@RequestParam("id") Long id) {
		return this.bizSubjectService.stop(id);
	}
	
	@RequestMapping(value = "/v2.0/combo_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCombo> comboList(@RequestParam("comboType") Long comboType){
		return this.comboService.selectComboListByType(comboType);
	} 
	
	@RequestMapping(value = "/v2.0/pack_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResValuePack> packList(@RequestParam("comboType") Long comboType){
		return this.valuePackService.selectPackTypeList(comboType);
	}
}
