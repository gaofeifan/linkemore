package cn.linkmore.order.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.client.RechargeAmountClient;
import cn.linkmore.order.request.ReqRechargeAmount;
import cn.linkmore.order.request.RequpdateColumnValue;
import cn.linkmore.order.response.ResRechargeAmount;

/**
 * 充值面额--远程调用
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Component
public class RechargeAmountClientHystrix implements RechargeAmountClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void updateByIdSelective(ReqRechargeAmount bean) {
		log.info("account service rechargeAmount updateByIdSelective(ReqRechargeAmount bean) hystrix");
	}

	@Override
	public void save(ReqRechargeAmount rechargeAmount) {
		log.info("account service rechargeAmount save(ReqRechargeAmount rechargeAmount) hystrix");
	}

	@Override
	public ViewPage getList(ViewPageable pageable) {
		log.info("account service rechargeAmount getList(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public ResRechargeAmount findById(Long id) {
		log.info("account service rechargeAmount findById(Long id) hystrix");
		return null;
	}

	@Override
	public void updateById(ReqRechargeAmount amount) {
		log.info("account service rechargeAmount updateById(ReqRechargeAmount amount) hystrix");
	}

	@Override
	public void updateColumnValue(RequpdateColumnValue value) {
		log.info("account service rechargeAmount updateColumnValue(RequpdateColumnValue value) hystrix");
	}

	
	
}
