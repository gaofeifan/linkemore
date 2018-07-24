package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandStall;
import cn.linkmore.ops.biz.service.EntBrandStallService;
import cn.linkmore.prefecture.client.OpsEntBrandStallClient;
/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Service
public class EntBrandStallServiceImpl implements EntBrandStallService {

	@Resource
	private OpsEntBrandStallClient entBrandStallClient;
	
	@Override
	public int save(ReqEntBrandStall record) {
		this.entBrandStallClient.save(record);
		return 0;
	}

	@Override
	public int update(ReqEntBrandStall record) {
		this.entBrandStallClient.update(record);
		return 0;
	}

	@Override
	public int delete(Long id) {
		this.entBrandStallClient.delete(id);
		return 0;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandStallClient.list(pageable);
		return page;
	}
}
