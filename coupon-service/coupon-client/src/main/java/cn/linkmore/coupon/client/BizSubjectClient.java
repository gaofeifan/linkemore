package cn.linkmore.coupon.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.hystrix.BizSubjectClientHystrix;
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.response.ResValuePack;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 主题
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/coupon_subject", fallback=BizSubjectClientHystrix.class,configuration = FeignConfiguration.class)
public interface BizSubjectClient {
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqBizSubject record) ;

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResBizSubjectBean detail(@RequestParam("id") Long id);

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqBizSubject record) ;

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) ;

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) ;
	
	/**
	 * 上线
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id);

	/**
	 * 下线
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int down(@RequestParam("id") Long id) ;
	
	@RequestMapping(value = "/v2.0/combo_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCombo> comboList(@RequestParam("comboType") Long comboType);
	
	@RequestMapping(value = "/v2.0/pack_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResValuePack> packList(@RequestParam("comboType") Long comboType);
}
