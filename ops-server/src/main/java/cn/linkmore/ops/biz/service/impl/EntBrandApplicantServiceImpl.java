package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.EntBrandApplicantService;
import cn.linkmore.prefecture.client.OpsEntBrandApplicantClient;
/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Service
public class EntBrandApplicantServiceImpl implements EntBrandApplicantService {

	@Resource
	private OpsEntBrandApplicantClient entBrandApplicantClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandApplicantClient.list(pageable);
		return page;
	}
	
}
