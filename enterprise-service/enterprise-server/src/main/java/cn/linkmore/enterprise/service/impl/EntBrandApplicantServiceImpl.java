package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandApplicantClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandApplicantMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandApplicant;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.service.EntBrandApplicantService;
/**
 * 品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandApplicantServiceImpl implements EntBrandApplicantService {
	@Resource
	private EntBrandApplicantMasterMapper entBrandApplicantMasterMapper;
	
	@Resource
	private EntBrandApplicantClusterMapper entBrandApplicantClusterMapper;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandApplicant> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandApplicant record) {
		return entBrandApplicantMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandApplicant record) {
		return entBrandApplicantMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandApplicantMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandApplicantClusterMapper.check(param);
	}
	
}
