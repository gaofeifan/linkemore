package cn.linkmore.account.controller;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.request.ReqWalletDetailExport;
import cn.linkmore.account.response.ResWalletDetailExport;
import cn.linkmore.account.service.WalletDetailService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 充值明细
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@RestController
@RequestMapping(value = "/wallet_detail")
public class WalletDetailController{

	@Resource
	private WalletDetailService walletDetailService;
	
	/**
	 * 钱包--充值明细
	 */
	@RequestMapping(value = "/v2.0/page", method = RequestMethod.POST)
	public ViewPage walletDetailList(@RequestBody ViewPageable pageable){
		return  this.walletDetailService.getDetailList(pageable);
	}
	
	/**
	 * 导出
	 * @return 
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	public List<ResWalletDetailExport> exportExcel( ReqWalletDetailExport bean){
		List<ResWalletDetailExport> result = walletDetailService.getListByTime(bean);
		return result;
	}
}
