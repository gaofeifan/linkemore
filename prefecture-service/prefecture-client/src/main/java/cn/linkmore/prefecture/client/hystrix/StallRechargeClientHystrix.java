package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallRechargeClient;
import cn.linkmore.prefecture.request.ReqStallRechargeExcel;
import cn.linkmore.prefecture.response.ResStallRecharge;
/**
 * 远程调用实现 - 电池更换记录
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallRechargeClientHystrix implements StallRechargeClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall_recharge list() hystrix");
		return null;
	}

	@Override
	public List<ResStallRecharge> export(ReqStallRechargeExcel reqStallRecharge) {
		log.info("prefecture service stall_recharge export() hystrix");
		return null;
	}
}
