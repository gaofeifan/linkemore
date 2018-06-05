package cn.linkmore.prefecture.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.TargetSettingClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqTargetSetting;
import cn.linkmore.prefecture.response.ResPreList;

/**
 * 远程调用 - 车区目标设置
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "prefecture-server", path = "/target_setting", fallback = TargetSettingClientHystrix.class, configuration = FeignConfiguration.class)
public interface TargetSettingClient {
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTargetSetting reqTargetSetting);

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTargetSetting reqTargetSetting);

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/v2.0/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> prefectureList();
}
