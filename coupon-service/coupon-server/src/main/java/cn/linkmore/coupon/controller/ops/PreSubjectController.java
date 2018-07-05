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
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqPreSubject;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.service.BizSubjectService;
import cn.linkmore.coupon.service.PreSubjectService;

@Controller
@RequestMapping("/ops/coupon_pre_subject")
public class PreSubjectController {

	@Autowired
	private BizSubjectService bizSubjectService;
	@Autowired
	private PreSubjectService preSubjectService;

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPreSubject record) {
		return	this.preSubjectService.save(record);
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPreSubject record) {
		return this.preSubjectService.update(record);
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		for(long id:ids){
			this.preSubjectService.delete(id);
		}
		return 0;
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.preSubjectService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.preSubjectService.findPage(pageable);
	}
	
	
	@RequestMapping(value = "/v2.0/subject_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResBizSubjectBean> subjectList(@RequestParam("type") Long type){
		return this.bizSubjectService.selectSubjectListByType(type);
	}

}
