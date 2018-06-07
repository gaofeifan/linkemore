package cn.linkmore.order.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqWalletDetailExport;
import cn.linkmore.order.response.ResWalletDetailExport;

/**
 * 充值明细
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface WalletDetailService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage getDetailList(ViewPageable pageable);

	/**
	 * @Description  根据条件查询导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletDetailExport> getListByTime(ReqWalletDetailExport bean);
	

}
