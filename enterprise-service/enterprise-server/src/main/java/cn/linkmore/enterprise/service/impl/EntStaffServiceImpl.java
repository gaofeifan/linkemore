/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntAuthStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffClusterMapper;
import cn.linkmore.enterprise.dao.master.EntStaffMasterMapper;
import cn.linkmore.enterprise.entity.EntAuthStall;
import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.service.EntStaffService;
import cn.linkmore.util.DomainUtil;

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
	private EntStaffClusterMapper entStaffClusterMapper;
	
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
		record.setCreateTime(new Date());
		return entStaffMasterMapper.save(record);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntStaffService#deleteEntStaff(java.lang.Long)
	 */
	@Override
	public int deleteEntStaff(Long id) {
//		List<EntAuthStall> authStalls =entAuthStallClusterMapper.findByEntStaffId(id);
//		if(authStalls.size() > 0){
//			return 0;
//		}
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
		return this.entStaffMasterMapper.updateByIdSelective(record);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.entStaffClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<EntStaff> list = this.entStaffClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void start(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("sql","status = 1");
		map.put("id",id);
		this.entStaffMasterMapper.updateByColumn(map );
	}

	@Override
	public void stop(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("sql","status = 0");
		map.put("id",id);
		this.entStaffMasterMapper.updateByColumn(map );
	}

	@Override
	public Integer check(String property, String value, Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", property);
		param.put("value", value);
		param.put("id", id);
		return this.entStaffClusterMapper.check(param); 
	}

	
	
	
}
