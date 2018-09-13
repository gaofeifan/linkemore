package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntPreture;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.PrefectrueClientHystrix;
import cn.linkmore.prefecture.response.ResPreList;

/**
 * 企业车区远程调用
 * 
 * @author GFF
 * @Date 2018年7月27日
 * @Version v2.0
 */
@FeignClient(value = "enterprise-server", path = "/ops/enterprise", fallback = PrefectrueClientHystrix.class, configuration = FeignConfiguration.class)
public interface PrefectrueClient {

	/**
	 * @Description 分页查询
	 * @Author GFF
	 * @Version v2.0
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	ViewPage findPage(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	void save(@RequestBody ReqAddEntPreture auth);

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	void update(@RequestBody ReqAddEntPreture auth);

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	void delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@ResponseBody
	List<ResEntPrefecture> findAll(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/not-create-pre", method = RequestMethod.GET)
	@ResponseBody
	List<ResPreList> findNotCreateEntPre();
}
