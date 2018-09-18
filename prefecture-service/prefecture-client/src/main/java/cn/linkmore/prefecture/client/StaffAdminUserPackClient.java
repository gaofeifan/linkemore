package cn.linkmore.prefecture.client;

import java.util.HashMap;
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
import cn.linkmore.prefecture.client.hystrix.AdminUserPackClientHystrix;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminUser;
/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@FeignClient(value = "prefecture-server", path = "/staff/admin-user-pack", fallback=AdminUserPackClientHystrix.class,configuration = FeignConfiguration.class)
public interface StaffAdminUserPackClient {

	@ResponseBody
	@RequestMapping(value="/by-admin-id",method=RequestMethod.GET)
	HashMap<String, Object> findByAdminId(@RequestParam("adminId")Long adminid);
	
	
}
