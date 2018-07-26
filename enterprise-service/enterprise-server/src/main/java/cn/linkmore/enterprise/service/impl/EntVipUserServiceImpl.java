/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.StringUtils;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EntVipUserMasterMapper;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntVipUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntVipUserService;
import cn.linkmore.util.StringUtil;

/**
 * 企业vip用户
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntVipUserServiceImpl implements EntVipUserService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntVipUserMasterMapper entVipUserMasterMapper;
	@Autowired
	private EnterpriseClusterMapper enterpriseClusterMapper;
	@Autowired
	private EntPrefectureClusterMapper  entPrefectureClusterMapper;
	
	@Autowired
	private UserClient userClient;

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntVipUserService#saveEntVipUser(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int saveEntVipUser(Long entId, Long entPreId, String mobile, String realname, String plate) {
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
		EntVipUser record = new EntVipUser();
		record.setEntId(entId);
		record.setEntPreId(entPreId);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setPlate(plate);
		Long userId = userClient.getUserIdByMobile(mobile);
		if(userId == null){
			logger.info("{}手机号不存在",mobile);
		}
		record.setUserId(userId);
		return entVipUserMasterMapper.save(record);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntVipUserService#deleteEntVipUser(java.lang.Long)
	 */
	@Override
	public int deleteEntVipUser(Long id) {
		
		return this.entVipUserMasterMapper.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntVipUserService#updateEntVipUser(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateEntVipUser(Long id, String mobile, String realname, String plate) {
		EntVipUser record = new EntVipUser();
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
		return this.entVipUserMasterMapper.updateByIdSelective(record);
	}

}
