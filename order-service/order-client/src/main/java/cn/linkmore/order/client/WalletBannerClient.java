package cn.linkmore.order.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.WalletBannerClientHystrix;
import cn.linkmore.order.request.ReqUpdateStatus;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
 
/**
 * 分享记录
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "order-server", path = "/feign/banner", fallback=WalletBannerClientHystrix.class,configuration = FeignConfiguration.class)
public interface WalletBannerClient {

	@RequestMapping(value = "/v2.0" ,method = RequestMethod.POST)
	void save(@RequestBody ReqWalletBanner banner);

	@RequestMapping(value = "/v2.0" ,method = RequestMethod.PUT)
	void updateById(@RequestBody ReqWalletBanner b);

	@RequestMapping(value = "/v2.0/status" ,method = RequestMethod.PUT)
	void updateStatus(ReqUpdateStatus updateStutus);

	@RequestMapping(value = "/v2.0/page" , method = RequestMethod.POST)
	ViewPage findPage(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/v2.0/detail" , method = RequestMethod.GET)
	ResWalletBanner findById(Long bid);

	@RequestMapping(value = "/v2.0/condition" , method = RequestMethod.GET)
	List<ResWalletBanner> selectList(String sql);
	
}
