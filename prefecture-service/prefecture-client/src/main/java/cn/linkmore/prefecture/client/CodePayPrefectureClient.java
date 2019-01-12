package cn.linkmore.prefecture.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.CodePayPrefectureClientHystrix;
import cn.linkmore.prefecture.request.ReqDep;

@FeignClient(value = "prefecture-server", path = "/feign/deploy", fallback = CodePayPrefectureClientHystrix.class, configuration = FeignConfiguration.class)
public interface CodePayPrefectureClient {

	// 查询列表
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable);

	// 查询列表
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage record(ViewPageable pageable);

	// 查询列表
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(ReqDep reqDep);

	// 查询列表
	@RequestMapping(value = "/payList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> payList(String orderNo);
	
	// 查询列表
		@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
		@ResponseBody
		public List<Map<String, Object>> selectAll();

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody String preId);

	// 删除
	@RequestMapping(value = "/down", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> down(@RequestBody String preId);

}
