package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.AdminUserClientHystrix;
import cn.linkmore.prefecture.client.hystrix.StaffAdminUserClientHystrix;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdmin;
import cn.linkmore.prefecture.response.ResAdminUser;
/**
 * 远程调用 - 管理员
 * @author   GFF
 * @Date     2018年9月11日
 * @Version  v2.0
 */
@FeignClient(value = "prefecture-server", path = "/staff/admin-user", fallback=StaffAdminUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface StaffAdminUserClient {
	
	@RequestMapping(value="/by-mobile",method=RequestMethod.GET)
	@ResponseBody
	public ResAdminUser findMobile(@RequestParam("mobile") String mobile);

	@RequestMapping(value="/login-time",method=RequestMethod.PUT)
	@ResponseBody
	public void updateLoginTime(@RequestParam("id") Long id);

	@RequestMapping(value="/login",method=RequestMethod.GET)
	@ResponseBody
	public ResAdmin authLogin(@RequestParam("mobile") String mobile);
	
}
