package cn.linkmore.ops.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.StallRechargeService;
import cn.linkmore.prefecture.client.StallRechargeClient;
import cn.linkmore.prefecture.request.ReqStallRechargeExcel;
import cn.linkmore.prefecture.response.ResStallRecharge;

/**
 * Service实现类 - 电池更换记录
 * @author zhaoyufei
 *
 */
@Service
public class StallRechargeServiceImpl implements StallRechargeService {
	@Autowired
	private StallRechargeClient stallRechargeClient;
	
	/*
	 * 列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.stallRechargeClient.list(pageable);
	}
	
	 

	@Override
	public List<ResStallRecharge> exportList(
			ReqStallRechargeExcel excelbean) {
		return this.stallRechargeClient.export(excelbean);
	}

}
