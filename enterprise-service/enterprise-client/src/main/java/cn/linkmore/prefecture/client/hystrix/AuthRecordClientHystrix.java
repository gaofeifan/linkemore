package cn.linkmore.prefecture.client.hystrix;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.AuthRecordClient;
/**
 * 授权使用记录熔断
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Component
public class AuthRecordClientHystrix implements AuthRecordClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ViewPage findList(ViewPageable pageable) {
		log.info("enterprise service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public int operateSwitch(Map<String, Object> param) {
		log.info("enterprise service ViewPage operateSwitch(ViewPageable pageable) hystrix");
		return 0;
	}

}
