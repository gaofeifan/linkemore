package cn.linkmore.ops.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqDataCount;
import cn.linkmore.ops.admin.service.DataCountService;
import cn.linkmore.order.client.DataCountClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.util.ObjectUtils;
@Service
public class DataCountServiceIMpl implements DataCountService {

	@Resource DataCountClient dataCountClient;
	
	@Override
	public void save(ReqDataCount dataCount) {
		cn.linkmore.order.request.ReqDataCount object = ObjectUtils.copyObject(dataCount, new cn.linkmore.order.request.ReqDataCount());
		object.setOrderIncome(dataCount.getOrderIncome());
		dataCountClient.saveVirtualData(object);
	}

	@Override
	public void stop() {
		dataCountClient.stop();
	}

	@Override
	public void start() {
		dataCountClient.start();
	}

	@Override
	public void delete(Long ids) {
		dataCountClient.delete(ids);
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		return dataCountClient.list(pageable);
	}

	
}
