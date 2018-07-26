/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentUserMasterMapper;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntRentUserService;
import cn.linkmore.util.StringUtil;

/**
 * 长租用户信息
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntRentUserServiceImpl implements EntRentUserService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntRentUserMasterMapper entRentUserMasterMapper;
	
	@Autowired
	private EnterpriseClusterMapper enterpriseClusterMapper;
	@Autowired
	private EntPrefectureClusterMapper  entPrefectureClusterMapper;
	
	@Autowired
	private UserClient userClient;

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntRentUserService#saveEntRentUser(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int saveEntRentUser(Long entId, Long entPreId, Long stallId, String mobile, String realname, String plate) {
		
		ResEnterprise resEnterprise =this.enterpriseClusterMapper.findById(entId);
		if(resEnterprise == null){
			return 0;
		}
		EntPrefecture  entPrefecture= this.entPrefectureClusterMapper.findById(entPreId);
		if(entPrefecture == null){
			return 0;
		}
		if(StringUtil.isBlank(mobile)){
			return 0;
		}
		EntRentUser record = new EntRentUser();
		record.setEntId(entId);
		record.setEntPreId(entPreId);
		record.setStalllId(stallId);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setPlate(plate);
		
		Long userId = userClient.getUserIdByMobile(mobile);
		if(userId == null){
			logger.info("{}手机号不存在",mobile);
		}
		record.setUserId(userId);
		return this.entRentUserMasterMapper.save(record);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntRentUserService#deleteEntRentUser(java.lang.Long)
	 */
	@Override
	public int deleteEntRentUser(Long id) {
		return this.entRentUserMasterMapper.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntRentUserService#updateEntRentUser(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateEntRentUser(Long id, String mobile, String realname, String plate) {
		EntRentUser record = new EntRentUser();
		if(id == null){
			return 0;
		}
		if(StringUtil.isBlank(mobile)){
			return 0;
		}
		record.setId(id);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setPlate(plate);
		Long userId = userClient.getUserIdByMobile(mobile);
		if(userId == null){
			logger.info("{}手机号不存在",mobile);
		}
		record.setUserId(userId);
		return this.entRentUserMasterMapper.updateByIdSelective(record);
	}

}
