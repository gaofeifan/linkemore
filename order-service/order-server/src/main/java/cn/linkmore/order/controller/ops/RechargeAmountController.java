package cn.linkmore.order.controller.ops;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqRechargeAmount;
import cn.linkmore.order.request.RequpdateColumnValue;
import cn.linkmore.order.response.ResRechargeAmount;
import cn.linkmore.order.service.RechargeAmountService;
 
/**
 * 充值面额--
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@RestController
@RequestMapping(value = "/admin/amount")
public class RechargeAmountController {
	@Resource
	private RechargeAmountService rechargeAmountService;
	/**
	 * @Description  你更新null处理
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0",method=RequestMethod.PUT)
	public void updateByIdSelective(@RequestBody ReqRechargeAmount bean) {
		rechargeAmountService.updateData(bean);
	}

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0",method=RequestMethod.POST)
	public void save(@RequestBody ReqRechargeAmount rechargeAmount) {
		this.rechargeAmountService.create(rechargeAmount);
	}

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ResponseBody
	@RequestMapping(value="/v2.0/page",method=RequestMethod.POST)
	public ViewPage getList(@RequestBody ViewPageable pageable) {
		ViewPage page = this.rechargeAmountService.getList(pageable);
		return page;
	}

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ResponseBody
	@RequestMapping(value="/v2.0/deltail/{id}",method=RequestMethod.GET)
	public ResRechargeAmount findById(@PathVariable("id")Long id) {
		return this.findById(id);
	}

	/**
	 * @Description  更新字段数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0/condition",method=RequestMethod.PUT)
	public void updateColumnValue(@RequestBody RequpdateColumnValue value) {
		this.rechargeAmountService.updateColumnValue(" AND " + value.getProperties(), value.getValue(), value.getStatus());
	}
}
