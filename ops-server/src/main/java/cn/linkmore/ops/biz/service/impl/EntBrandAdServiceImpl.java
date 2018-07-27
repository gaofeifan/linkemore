package cn.linkmore.ops.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.ops.biz.service.EntBrandAdService;
import cn.linkmore.prefecture.client.OpsEntBrandAdClient;
import cn.linkmore.security.client.PersonClient;
/**
 * @author   jiaohanbin
 * @Version  v2.0
 */
@Service
public class EntBrandAdServiceImpl implements EntBrandAdService {

	@Resource
	private OpsEntBrandAdClient entBrandAdClient;
	
	@Resource
	private PersonClient personClient;
	@Override
	public int save(ReqEntBrandAd record) {
		this.entBrandAdClient.save(record);
		return 0;
	}

	@Override
	public int update(ReqEntBrandAd record) {
		this.entBrandAdClient.update(record);
		return 0;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandAdClient.list(pageable);
		return page;
	}

	@Override
	public ResBrandAd findById(Long id) {
		return this.entBrandAdClient.findById(id);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.entBrandAdClient.delete(ids);
	}

	@Override
	public int start(Long id) {
		
		return this.entBrandAdClient.start(id);
	}

	@Override
	public int stop(Long id) {
		return this.entBrandAdClient.stop(id);
	}
	
}
