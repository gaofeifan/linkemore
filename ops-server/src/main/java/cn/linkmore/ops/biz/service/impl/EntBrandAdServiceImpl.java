package cn.linkmore.ops.biz.service.impl;

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
	public int delete(Long id) {
		this.entBrandAdClient.delete(id);
		return 0;
	}

	/*@Override
	public Boolean check(ReqCheck reqCheck) {
		Boolean check = this.entBrandAdClient.check(reqCheck);
		return check;
	}*/

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandAdClient.list(pageable);
		return page;
	}

	@Override
	public ResBrandAd findById(Long id) {
		return this.entBrandAdClient.findById(id);
	}
	
}
