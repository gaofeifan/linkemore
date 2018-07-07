package cn.linkmore.coupon.controller.ops;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqSubject;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.service.SubjectService;
import cn.linkmore.coupon.service.TemplateService;

@RestController
@RequestMapping("/ops/coupon_subject_new")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@Autowired 
	private TemplateService templateService;

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqSubject record) {
		return this.subjectService.save(record);
	}

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResSubject detail(@RequestParam("id") Long id) {
		ResSubject subject = this.subjectService.findById(id);
		return subject;
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqSubject record) {
		return this.subjectService.update(record);
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.subjectService.delete(ids.get(0));
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.subjectService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.subjectService.findPage(pageable);
	}
	
	/*
	 * 上线
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id) {
		return this.subjectService.start(id);
	}

	/*
	 * 下线
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int down(@RequestParam("id") Long id) {
		return this.subjectService.stop(id);
	}
	
	@RequestMapping(value = "/v2.0/subject_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResTemplate> subjectList(){
		return this.templateService.findSubjectCouponList();
	}
}
