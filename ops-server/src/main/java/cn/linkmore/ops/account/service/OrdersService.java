package cn.linkmore.ops.account.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrderExcel;

/**
 * 订单
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface OrdersService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  文件导出
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResOrderExcel> exportList(ReqOrderExcel bean);

}
