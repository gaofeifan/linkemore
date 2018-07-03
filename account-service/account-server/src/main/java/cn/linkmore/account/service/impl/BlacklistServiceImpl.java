package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.account.dao.cluster.UserBlacklistClusterMapper;
import cn.linkmore.account.dao.master.UserBlacklistMasterMapper;
import cn.linkmore.account.entity.UserBlacklist;
import cn.linkmore.account.response.ResUserBlacklist;
import cn.linkmore.account.service.BlacklistService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;

@Service
public class BlacklistServiceImpl implements BlacklistService {
	
	private Logger log = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	private UserBlacklistClusterMapper blacklistClusterMapper;
	@Autowired
	private UserBlacklistMasterMapper blacklistMasterMapper;
	
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
		Integer count = this.blacklistClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResUserBlacklist> list = this.blacklistClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	/*@Override
	public List<PrefectureBean> findPreList() {
		return this.blacklistMapper.findPreList(); 
	}
*/
	@Override
	public void enable(List<Long> list) {
		this.blacklistMasterMapper.enable(list);
	}

	@Override
	public List<ResUserBlacklist> findList() {
		return this.blacklistClusterMapper.findList();
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackForClassName = "RuntimeExcpeiton")
	public void reset(){
		int delCount = this.blacklistMasterMapper.clear();
		log.info("delete {} records",delCount);
		int saveCount = this.blacklistMasterMapper.batchSave();
		log.info("insert {} records",saveCount);
		delCount = this.blacklistMasterMapper.deleteWhitelist();
		log.info("remove {} whitelist",delCount);
		
	}

}
