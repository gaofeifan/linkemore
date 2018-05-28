package cn.linkmore.common.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.DistrictClientHystrix;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqDistrict;
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
	void delete(List<Long> ids);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqDistrict district);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqDistrict district);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean check(ReqCheck object);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description 查询树桩结构数据 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Tree findTree();

}
