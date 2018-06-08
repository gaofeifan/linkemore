package cn.linkmore.ops.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.BannerService;
import cn.linkmore.order.client.WalletBannerClient;
import cn.linkmore.order.request.ReqUpdateStatus;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
import cn.linkmore.util.StringUtil;

@Service
public class BannerServiceImpl implements BannerService {
	
	
	@Resource
	private WalletBannerClient walletBannerClient;


	@Override
	public void save(ReqWalletBanner banner) {
		walletBannerClient.save(banner);
	}

	@Override
	public void update(ReqWalletBanner b) {
		this.walletBannerClient.updateById(b);
	}


	@Override
	public ResWalletBanner findById(Long bid) {
		return walletBannerClient.findById(bid);
	}

	@Override
	public int findStatusCount() {
		Map<String, Object> map = new HashMap<>();
		map.put("status"+StringUtil.EQUALS, 1);
		String sql = StringUtil.joinSql(map);
		List<ResWalletBanner> list = this.selectList(sql);
		return list.size();
	}
	
	@Override
	public List<ResWalletBanner> selectList(String sql) {
		return this.walletBannerClient.selectList(sql);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.walletBannerClient.findPage(pageable);
	}

	@Override
	public void updateStatus(ReqUpdateStatus reqUpdateStatus) {
		this.walletBannerClient.updateStatus(reqUpdateStatus);
		
	}

	

}
