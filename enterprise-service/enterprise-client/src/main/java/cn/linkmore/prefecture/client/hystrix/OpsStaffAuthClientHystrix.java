package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntStaff;
import cn.linkmore.enterprise.request.ReqCheck;
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
	public List<Tree> tree() {
		log.info("ops staffauth service Tree tree() hystrix");
		return null;
	}
	@Override
	public Map<String, Object> resouce(Long staffId) {
		log.info("ops staffauth service  Map<String, Object> resouce(Long staffId) hystrix");
		return null;
	}
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("ops staffauth service  ViewPage findPage(ViewPageable pageable) hystrix");
		return null;
	}
	@Override
	public void delete(Long id) {
		log.info("ops staffauth service  void delete(Long id) hystrix");
		
	}
	@Override
	public void save(ReqAddEntStaff staff) {
		log.info("ops staffauth service  void save(List ...) hystrix");
		
	}
	@Override
	public void update(ReqAddEntStaff staff) {
		log.info("ops staffauth service  void update(List ...) hystrix");
		
	}
	@Override
	public void start(Long id) {
		log.info("ops staffauth service  void start(Long id) hystrix");
		
	}
	@Override
	public void stop(Long id) {
		log.info("ops staffauth service  void stop(Long id) hystrix");
	}
	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("ops staffauth service  Boolean check(ReqCheck reqCheck) hystrix");
		return null;
	}
	
	
	
	
	

}
