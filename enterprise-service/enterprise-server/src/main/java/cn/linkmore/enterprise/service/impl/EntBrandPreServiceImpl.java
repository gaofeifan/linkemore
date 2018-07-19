package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandPreClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandPreMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandPre;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.service.EntBrandPreService;
/**
 * 品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandPreServiceImpl implements EntBrandPreService {
	@Resource
	private EntBrandPreMasterMapper entBrandPreMasterMapper;
	
	@Resource
	private EntBrandPreClusterMapper entBrandPreClusterMapper;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandPre> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandPre record) {
		return entBrandPreMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandPre record) {
		return entBrandPreMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandPreMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandPreClusterMapper.check(param);
	}

	
}
