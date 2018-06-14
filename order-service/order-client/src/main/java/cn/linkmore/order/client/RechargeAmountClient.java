package cn.linkmore.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.RechargeAmountClientHystrix;
import cn.linkmore.order.request.ReqRechargeAmount;
import cn.linkmore.order.request.RequpdateColumnValue;
import cn.linkmore.order.response.ResRechargeAmount;
 
/**
 * 充值面额--远程调用
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "order-server", path = "/admin/amount", fallback=RechargeAmountClientHystrix.class,configuration = FeignConfiguration.class)
public interface RechargeAmountClient {

	/**
	 * @Description  你更新null处理
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0",method=RequestMethod.PUT)
	void updateByIdSelective(@RequestBody ReqRechargeAmount bean);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0",method=RequestMethod.POST)
	void save(@RequestBody ReqRechargeAmount rechargeAmount);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ResponseBody
	@RequestMapping(value="/v2.0/page",method=RequestMethod.POST)
	ViewPage getList(@RequestBody ViewPageable pageable);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0/deltail/{id}",method=RequestMethod.GET)
	ResRechargeAmount findById(@PathVariable("id")Long id);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0",method=RequestMethod.PUT)
	void updateById(@RequestBody ReqRechargeAmount amount);

	@RequestMapping(value="/v2.0/condition",method=RequestMethod.PUT)
	void updateColumnValue(@RequestBody RequpdateColumnValue value);

	
}
