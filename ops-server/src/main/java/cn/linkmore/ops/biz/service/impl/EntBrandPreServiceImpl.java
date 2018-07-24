package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.ops.biz.service.EntBrandPreService;
import cn.linkmore.prefecture.client.OpsEntBrandPreClient;
import cn.linkmore.security.client.PersonClient;
/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Service
public class EntBrandPreServiceImpl implements EntBrandPreService {

	@Resource
	private OpsEntBrandPreClient entBrandPreClient;
	@Resource
	private PersonClient personClient;
	@Override
	public int save(ReqEntBrandPre record) {
		this.entBrandPreClient.save(record);
		return 0;
	}

	@Override
	public int update(ReqEntBrandPre record) {
		this.entBrandPreClient.update(record);
		return 0;
	}

	@Override
	public int delete(Long id) {
		this.entBrandPreClient.delete(id);
		return 0;
	}

	/*@Override
	public Boolean check(ReqCheck reqCheck) {
		Boolean check = this.entBrandPreClient.check(reqCheck);
		return check;
	}*/

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandPreClient.list(pageable);
		return page;
	}
}
