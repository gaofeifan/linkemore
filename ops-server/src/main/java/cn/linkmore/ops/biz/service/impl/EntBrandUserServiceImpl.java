package cn.linkmore.ops.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandUser;
import cn.linkmore.ops.biz.service.EntBrandUserService;
import cn.linkmore.prefecture.client.OpsEntBrandUserClient;
/**
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Service
public class EntBrandUserServiceImpl implements EntBrandUserService {

	@Resource
	private OpsEntBrandUserClient entBrandUserClient;
	
	@Override
	public int save(ReqEntBrandUser record) {
		this.entBrandUserClient.save(record);
		return 0;
	}

	@Override
	public int update(ReqEntBrandUser record) {
		this.entBrandUserClient.update(record);
		return 0;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandUserClient.list(pageable);
		return page;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.entBrandUserClient.delete(ids);
	}
}
