package cn.linkmore.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.UserStaffClientHystrix;
import cn.linkmore.account.response.ResUserStaff;
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
}
