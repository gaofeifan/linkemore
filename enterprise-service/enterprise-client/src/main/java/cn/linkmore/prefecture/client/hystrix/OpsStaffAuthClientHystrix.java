package cn.linkmore.prefecture.client.hystrix;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.prefecture.client.OpsStaffAuthClient;

/**
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
@Component
public class OpsStaffAuthClientHystrix implements OpsStaffAuthClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void bind(ReqStaffAuthBind staff) {
		log.info("ops staffauth service int bind(ReqStaffAuthBind staff) hystrix");
		
	}
	@Override
	public Tree tree() {
		log.info("ops staffauth service Tree tree() hystrix");
		return null;
	}
	@Override
	public Map<String, Object> resouce(Long staffId) {
		log.info("ops staffauth service  Map<String, Object> resouce(Long staffId) hystrix");
		return null;
	}
	
	

}
