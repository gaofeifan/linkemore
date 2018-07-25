package cn.linkmore.enterprise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.dao.cluster.EntRentUserClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntVipUserClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EntPrefectureMasterMapper;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.entity.EntVipUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntPreService;

/**
 * 企业车区service
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntPreServiceImpl implements EntPreService {
	
	@Autowired
	private EntPrefectureMasterMapper entPrefectureMasterMapper;
	@Autowired
	private EnterpriseClusterMapper enterpriseClusterMapper;
	@Autowired
	private EntRentUserClusterMapper entRentUserClusterMapper;
	@Autowired
	private EntVipUserClusterMapper entVipUserClusterMapper;

	@Override
	public int saveEntPre(Long preId,Long entId, String preName) {
		
		if(entId == null || entId == 0){
			return 0;
		}
		ResEnterprise resEnterprise = enterpriseClusterMapper.findById(entId);
		if(resEnterprise == null ){
			return 0;
		}
		EntPrefecture record = new EntPrefecture();
		record.setEntId(entId);
		record.setPreId(preId);
		record.setEntName(resEnterprise.getName());
		record.setPreName(preName);
		
		return entPrefectureMasterMapper.save(record);
	}

	@Override
	public int deleteEntPre(Long id) {
		if(id == null || id ==  0){
			return 0;
		}
		List<EntRentUser> rentUsers = entRentUserClusterMapper.findByIdEntPreId(id);
		if(rentUsers.size() > 0){
			return 0;
		}
		List<EntVipUser> vipUsers = entVipUserClusterMapper.findByIdEntPreId(id);
		if(vipUsers.size() >0 ){
			return 0;
		}
		return this.entPrefectureMasterMapper.deleteById(id);
	}

	@Override
	public int updateEntPre(Long id, Long preId, String preName) {
		// TODO Auto-generated method stub
		return 0;
	}

}
