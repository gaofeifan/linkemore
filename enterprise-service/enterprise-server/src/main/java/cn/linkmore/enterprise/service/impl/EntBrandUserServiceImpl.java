package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandUserMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandUser;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.service.EntBrandUserService;
/**
 * 授权品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandUserServiceImpl implements EntBrandUserService {
	@Resource
	private EntBrandUserMasterMapper entBrandUserMasterMapper;
	
	@Resource
	private EntBrandUserClusterMapper entBrandUserClusterMapper;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return null;
	}

	@Override
	public List<EntBrandUser> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(EntBrandUser record) {
		return entBrandUserMasterMapper.save(record);
	}

	@Override
	public int update(EntBrandUser record) {
		return entBrandUserMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return entBrandUserMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandUserClusterMapper.check(param);
	}
	
}
