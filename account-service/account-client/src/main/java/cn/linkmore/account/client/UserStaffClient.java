package cn.linkmore.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.UserStaffClientHystrix;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/user_staff", fallback=UserStaffClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserStaffClient {
	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	public ResUserStaff findById(@PathVariable("id") Long id);
	
	@RequestMapping( method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqUserStaff record);
	
	@RequestMapping( method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqUserStaff record);
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check);
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);  
}
