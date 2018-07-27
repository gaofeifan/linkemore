package cn.linkmore.enterprise.controller.ops;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.service.EntBrandAdService;
import cn.linkmore.prefecture.request.ReqCheck;

/**
 * @Description - 品牌企业广告
 * @Author jiaohanbin
 * @Version v2.0
 */
@Controller
@RequestMapping("/ops/ent-brand-ad")
public class EntBrandAdController {
	
	@Resource
	private EntBrandAdService entBrandAdService;

	/**
	 * 车区列表分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.entBrandAdService.findPage(pageable);
	}
	
	/**
	 * 删除品牌车区
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.entBrandAdService.delete(ids);
	}
	
	/**
	 * 检查名称重复
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		/*Integer count = this.entBrandAdService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}*/		
		return flag;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqEntBrandAd reqEntBrandAd) {
		return	this.entBrandAdService.save(reqEntBrandAd);
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEntBrandAd reqEntBrandAd) {
		return this.entBrandAdService.update(reqEntBrandAd);
	}
	
	@RequestMapping(value = "/v2.0/find", method = RequestMethod.POST)
	@ResponseBody
	public ResBrandAd findById(@RequestParam("id") Long id) {
		return this.entBrandAdService.findById(id);
	}
	
	/*
	 * 启用
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id) {
		return	this.entBrandAdService.start(id);
	}

	/*
	 * 禁用
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int stop(@RequestParam("id") Long id) {
		return	this.entBrandAdService.stop(id);
	}

}
