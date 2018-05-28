package cn.linkmore.common.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.BaseVersionClientHystrix;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 	版本管理
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/version", fallback=BaseVersionClientHystrix.class,configuration = FeignConfiguration.class)
public interface BaseVersionClient {
	
	/**
	 * @Description  当前版本
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current/{source}",method = RequestMethod.GET)
	@ResponseBody
	public ResVersionBean current(@PathVariable("source")Integer source);
	
	/**
	 * @Description  上报用户版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.POST)
	public void report(@RequestBody ReqVersion vrb);
	
	/**
	 * @Description  添加app版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/app",method = RequestMethod.POST)
	public void saveApp(@RequestBody ReqAppVersion version);
	
	/**
	 * @Description  更新app版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public void updateApp(@RequestBody ReqAppVersion version);
	
	/**
	 * @Description  更新app版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public void deleteAppById(@RequestBody List<Long> ids);
	
	/**
	 * @Description  查询分页
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/page",method = RequestMethod.POST)
	public ViewPage findPage(@RequestBody ViewPageable pageable);

	/**
	 * @Description 校验 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/check",method = RequestMethod.POST)
	public Boolean check(@RequestBody ReqCheck check);

	/**
	 * @Description  查询用户版本（分页）
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/user_page",method = RequestMethod.POST)
	public ViewPage findUserPage(@RequestBody ViewPageable pageable);
}
