package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;
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
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.entBrandPreClient.list(pageable);
		return page;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.entBrandPreClient.delete(ids);
	}

	@Override
	public ResBrandPre findById(Long id) {
		return this.entBrandPreClient.findById(id);
	}

	@Override
	public int stop(Long id) {
		return this.entBrandPreClient.stop(id);
	}

	@Override
	public int start(Long id) {
		return this.entBrandPreClient.start(id);
	}
	
	@Override
	public int count(Map<String, Object> map) {
		return this.entBrandPreClient.count(map);
	}
}
