package cn.linkmore.order.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.client.WalletBannerClient;
import cn.linkmore.order.request.ReqUpdateStatus;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
/**
 * 钱包管理--容错
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Component
public class WalletBannerClientHystrix implements WalletBannerClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqWalletBanner banner) {
		log.info("account service walletBanner save(ReqWalletBanner banner) hystrix");		
	}

	@Override
	public void updateById(ReqWalletBanner b) {
		log.info("account service walletBanner updateById(ReqWalletBanner b) hystrix");		
	}

	@Override
	public void updateStatus(ReqUpdateStatus updateStutus) {
		log.info("account service walletBanner updateStatus(ReqUpdateStatus updateStutus) hystrix");		
		
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("account service walletBanner findPage(ViewPageable pageable) hystrix");		
		return null;
	}

	@Override
	public ResWalletBanner findById(Long bid) {
		log.info("account service walletBanner findById(Long bid) hystrix");		
		return null;
	}

	@Override
	public List<ResWalletBanner> selectList(String sql) {
		log.info("account service walletBanner selectList(String sql) hystrix");		
		return null;
	}
	
	

	


}
