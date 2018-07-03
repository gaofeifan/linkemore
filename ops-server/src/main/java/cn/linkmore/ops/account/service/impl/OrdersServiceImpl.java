package cn.linkmore.ops.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.OrdersService;
import cn.linkmore.ops.request.ReqOrderExcel;
/**
 * 订单--接口实现
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.util.ObjectUtils;
@Service
public class OrdersServiceImpl implements OrdersService {

	@Resource
	private OrderClient orderClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.orderClient.findPage(pageable);
		return page;
	}

	@Override
	public List<ResOrderExcel> exportList(ReqOrderExcel bean) {
		cn.linkmore.order.request.ReqOrderExcel excel = ObjectUtils.copyObject(bean, new cn.linkmore.order.request.ReqOrderExcel());
		return this.orderClient.exportList(excel);
	}
	
	
}
