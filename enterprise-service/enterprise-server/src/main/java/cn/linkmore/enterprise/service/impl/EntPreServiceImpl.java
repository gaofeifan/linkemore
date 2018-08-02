package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntRentUserClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntVipUserClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EntPrefectureMasterMapper;
import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.entity.EntVipUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntPreService;
import cn.linkmore.util.DomainUtil;

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
	private EntPrefectureClusterMapper entPrefectureClusterMapper;
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
	public int updateEntPre(Long id, Long entId,Long preId, String preName) {
		ResEnterprise resEnterprise = enterpriseClusterMapper.findById(entId);
		if(resEnterprise == null ){
			return 0;
		}
		EntPrefecture record = new EntPrefecture();
		record.setEntId(entId);
		record.setPreId(preId);
		record.setEntName(resEnterprise.getName());
		record.setPreName(preName);
		record.setId(id);
		return entPrefectureMasterMapper.updateByIdSelective(record);
	}

	@Override
	public List<EntPrefecture> findList(Map<String, Object> map) {
		List<EntPrefecture> list = this.entPrefectureClusterMapper.findList(map);
		return list;
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
		Integer count = this.entPrefectureClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<EntOperateAuth> list = this.entPrefectureClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	
	

}
