package cn.linkmore.account.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.NoticeClient;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
/**
 * 消息管理--熔断
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class NoticeClientHystrix implements NoticeClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	

	@Override
	public ResPage listNotice(ReqPageNotice bean) {
		log.info("account service listNotice(ReqPageNotice bean) hystrix");
		return null;
	}


	@Override
	public ResNotice read(ReqNotice notice) {
		log.info("account service ResNotice read(ReqNotice notice) hystrix");
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(ReqNotice notice) {
		log.info("account service void delete(ReqNotice notice) hystrix");
		// TODO Auto-generated method stub
		
	}

	
	
}
