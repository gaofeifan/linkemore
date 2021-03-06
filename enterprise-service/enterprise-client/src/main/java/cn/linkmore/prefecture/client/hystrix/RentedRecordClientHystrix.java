package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.prefecture.client.OpsRentedRecordClient;
/**
 * 长租用户使用记录熔断
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Component
public class RentedRecordClientHystrix implements OpsRentedRecordClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ViewPage findList(ViewPageable pageable) {
		log.info("enterprise service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}
	@Override
	public List<ResRentedRecord> exportList(ReqRentedRecord bean) {
		log.info("enterprise service ResRentedRecord exportList(ReqRentedRecord bean) hystrix");
		return null;
	}

}
