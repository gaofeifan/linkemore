package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandStallMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandStall;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.service.EntBrandStallService;
/**
 * 品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandStallServiceImpl implements EntBrandStallService {
	@Resource
	private EntBrandStallMasterMapper entBrandStallMasterMapper;
	
	@Resource
	private EntBrandStallClusterMapper entBrandStallClusterMapper;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandStall> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandStall record) {
		return entBrandStallMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandStall record) {
		return entBrandStallMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandStallMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandStallClusterMapper.check(param);
	}
	
}
