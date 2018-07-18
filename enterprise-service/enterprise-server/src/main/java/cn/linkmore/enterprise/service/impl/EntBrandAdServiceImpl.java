package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandAdClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandAdMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandAd;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.service.EntBrandAdService;
/**
 * 品牌广告
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandAdServiceImpl implements EntBrandAdService {
	@Resource
	private EntBrandAdMasterMapper entBrandAdMasterMapper;
	
	@Resource
	private EntBrandAdClusterMapper entBrandAdClusterMapper;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandAd> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandAd record) {
		return entBrandAdMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandAd record) {
		return entBrandAdMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandAdMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandAdClusterMapper.check(param);
	}

	
}
