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
import cn.linkmore.prefecture.client.hystrix.AdminAuthClientHystrix;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminAuthPre;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResStaffCity;
/**
 * 远程调用 - 车位授权
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/ops/admin_auth", fallback=AdminAuthClientHystrix.class,configuration = FeignConfiguration.class)
public interface AdminAuthClient {
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	/**
	 * 检查
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqAdminAuth admin);
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqAdminAuth admin);
	/**
	 * 资源树
	 */
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree();
	/*
	 * 权限回显
	 */
	@RequestMapping(value = "/v2.0/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(@RequestParam("id") Long id);
	
	/*
	 * 绑定
	 */
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestParam("id") Long id,@RequestParam("citys") String citys,@RequestParam("pres") String pres,@RequestParam("json") String json);
	/*
	 * 删除
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/by-admin-id", method = RequestMethod.GET)
	@ResponseBody
	public List<ResStaffCity> findStaffCitysByAdminId(@RequestParam("id")Long id);
	
	@RequestMapping(value = "/pre-by-admin-id", method = RequestMethod.GET)
	@ResponseBody
	public List<ResPre> findStaffPreByAdminId(@RequestParam("id") Long id);
	
}
