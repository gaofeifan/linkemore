package cn.linkmore.ops.account.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.RechargeRecordService;
import cn.linkmore.order.client.RechargeRecordClient;
import cn.linkmore.order.request.ReqRechargeRecordExcel;
import cn.linkmore.order.response.ResRechargeRecordExcel;
/**
 * Service实现类 - 储值记录
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {
	@Autowired
	private RechargeRecordClient rechargeRecordClient; 
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.rechargeRecordClient.findPage(pageable);
	}

	@Override
	public List< ResRechargeRecordExcel> exportList(ReqRechargeRecordExcel bean) {
		return this.rechargeRecordClient.findExportList(bean);
		 
	} 
}
