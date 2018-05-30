package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.client.WechatFansClient;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.request.ReqWechatFans;
import cn.linkmore.account.request.ReqWechatFansExcel;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.response.ResWechatFans;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class WechatFansClientHystrix  implements WechatFansClient{
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service WechatFans list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public List<ResWechatFans> exportList(ReqWechatFansExcel bean) {
		log.info("account service WechatFans exportList(ReqWechatFansExcel bean) hystrix");
		return null;
	}

	@Override
	public void save(ReqWechatFans bean) {
		log.info("account service WechatFans save(ReqWechatFans bean) hystrix");
	}

	@Override
	public void update(ReqWechatFans bean) {
		log.info("account service WechatFans update(ReqWechatFans bean) hystrix");
	}
}

