/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.dao.cluster.EntAuthStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntStaffMasterMapper;
import cn.linkmore.enterprise.entity.EntAuthStall;
import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.service.EntStaffService;

/**
 * 企业员工信息
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntStaffServiceImpl implements EntStaffService {
	
	@Autowired
	private EntStaffMasterMapper entStaffMasterMapper;
	
	@Autowired
	private EntAuthStallClusterMapper entAuthStallClusterMapper;

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntStaffService#saveEntStaff(java.lang.Long, java.lang.String, java.lang.String, java.lang.Short)
	 */
	@Override
	public int saveEntStaff(Long entId, String mobile, String realname, Short type) {
		EntStaff record = new EntStaff();
		record.setEntId(entId);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setType(type);
		record.setStatus((short) 0);
		return entStaffMasterMapper.save(record);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntStaffService#deleteEntStaff(java.lang.Long)
	 */
	@Override
	public int deleteEntStaff(Long id) {
		List<EntAuthStall> authStalls =entAuthStallClusterMapper.findByEntStaffId(id);
		if(authStalls.size() > 0){
			return 0;
		}
		return this.entStaffMasterMapper.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntStaffService#updateEntStaff(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Short, java.lang.Short)
	 */
	@Override
	public int updateEntStaff(Long id, Long entId, String mobile, String realname, Short type, Short status) {
		EntStaff record = new EntStaff();
		record.setId(id);
		record.setEntId(entId);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setType(type);
		record.setStatus(status);
		record.setCreateTime(new Date());
		return this.entStaffMasterMapper.updateByIdSelective(record);
	}

}
