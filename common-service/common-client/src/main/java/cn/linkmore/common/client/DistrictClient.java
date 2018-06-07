package cn.linkmore.common.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.DistrictClientHystrix;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqDistrict;
import cn.linkmore.common.response.ResDistrict;
import cn.linkmore.feign.FeignConfiguration;


/**
 * 远程调用---区域
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/district", fallback = DistrictClientHystrix.class, configuration = FeignConfiguration.class)
public interface DistrictClient {

	/**
	 * @Description 批量删除 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	void delete(@RequestBody List<Long> ids);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	void update(@RequestBody ReqDistrict district);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	void save(@RequestBody ReqDistrict district);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/check",method = RequestMethod.POST)
	@ResponseBody
	Boolean check(@RequestBody ReqCheck object);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/page",method = RequestMethod.POST)
	@ResponseBody
	ViewPage findPage(@RequestBody ViewPageable pageable);

	/**
	 * @Description 查询树桩结构数据 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/tree",method = RequestMethod.GET)
	@ResponseBody
	Tree findTree();
	
	/**
	 * 根据城市id查询区域id下拉框数据
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value="/select_list",method = RequestMethod.GET)
	@ResponseBody
	List<ResDistrict> findSelectListByCityId(@RequestParam("cityId") Long cityId);

}
