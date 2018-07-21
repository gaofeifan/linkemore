/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.dao.cluster.EntAuthPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.entity.EntAuthPre;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.prefecture.client.StallClient;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntStallServiceImpl implements EntStallService {

	@Autowired
	private EntStaffAuthClusterMapper  entStaffAuthClusterMapper;
	
	@Autowired
	private EntAuthPreClusterMapper entAuthPreClusterMapper;
	
	@Autowired
	private StallClient stallClient;
	
	@Override
	public List<ResEntStalls> selectEntStalls(Long id) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("staffId", id);
		map.put("status", 1);
		List<EntStaffAuth> entStaffAuths= entStaffAuthClusterMapper.findList(map);
		int size = entStaffAuths.size();
		if(size == 0){
			return new ArrayList<ResEntStalls>();
		}
		
		EntStaffAuth entStaffAuth = entStaffAuths.get(0);
		Map<String,Object> param = new HashMap<>();
		param.put("authId", entStaffAuth.getAuthId());
		List<EntAuthPre> entAuthPres= entAuthPreClusterMapper.findList(param);
		int preSize = entAuthPres.size();
		EntAuthPre entAuthPre = null;
		
		List<ResEntStalls> entStallList = new ArrayList<>();
		ResEntStalls resEntStalls = null;
		Map<String,Object> params = null;
		for(int i = 0; i < preSize ; i ++){
			entAuthPre = entAuthPres.get(i);
			resEntStalls = new ResEntStalls();
			params = new HashMap<String,Object>();
			params.put("preId", entAuthPre.getPreId());
//			stallClient.
		}
		return null;
	}

}
