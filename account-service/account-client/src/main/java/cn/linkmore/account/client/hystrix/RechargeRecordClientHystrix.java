package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.RechargeRecordClient;
import cn.linkmore.account.request.ReqRechargeRecordExcel;
import cn.linkmore.account.response.ResRechargeRecordExcel;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 储值记录--容错
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Component
public class RechargeRecordClientHystrix implements RechargeRecordClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("account service record list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public List<ResRechargeRecordExcel> findExportList(ReqRechargeRecordExcel bean) {
		log.info("account service record findExportList(ReqRechargeRecordExcel bean) hystrix");
		return null;
	}


}
