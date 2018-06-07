package cn.linkmore.ops.account.service;
import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqWalletDetailExport;
import cn.linkmore.order.response.ResWalletDetailExport;

/**
 * 充值明细--接口
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public interface WalletDetailService {

	/**
	 * @Description  查询导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletDetailExport> getListByTime(ReqWalletDetailExport bean);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage getDetailList( ViewPageable pageable);


}
