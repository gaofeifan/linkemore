package cn.linkmore.account.client.hystrix;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.account.client.FeedBackClient;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqFeedBack;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class FeedBackClientHystrix implements FeedBackClient{
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("account service feedback findPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public List<ResFeedBack> exportList(ReqFeedBack bean) {
		log.info("account service ResFeedBack exportList(ReqFeedBack bean) hystrix");
		return null;
	}
	 
	
	
}

