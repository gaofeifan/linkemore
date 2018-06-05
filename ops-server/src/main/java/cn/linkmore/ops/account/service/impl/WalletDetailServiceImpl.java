package cn.linkmore.ops.account.service.impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.account.client.WalletDetailClient;
import cn.linkmore.account.request.ReqWalletDetailExport;
import cn.linkmore.account.response.ResWalletDetailExport;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.WalletDetailService;
/**
 * 充值明细--接口实现
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Service
public class WalletDetailServiceImpl implements WalletDetailService {

	@Resource
	private WalletDetailClient walletDetailClient;
	
	@Override
	public List<ResWalletDetailExport> getListByTime(ReqWalletDetailExport bean) {
		return this.walletDetailClient.getListByTime(bean);
	}

	@Override
	public ViewPage getDetailList( ViewPageable pageable) {
		return this.walletDetailClient.list(pageable);
	}
	
	

	
}
