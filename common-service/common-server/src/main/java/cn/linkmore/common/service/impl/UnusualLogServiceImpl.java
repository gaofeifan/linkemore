package cn.linkmore.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.UnusualLogClusterMapper;
import cn.linkmore.common.dao.cluster.UnusualLogContentClusterMapper;
import cn.linkmore.common.dao.master.UnusualLogContentMasterMapper;
import cn.linkmore.common.dao.master.UnusualLogMasterMapper;
import cn.linkmore.common.entity.BaseAppVersion;
import cn.linkmore.common.entity.UnusualLog;
import cn.linkmore.common.entity.UnusualLogContent;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.common.service.UnusualLogService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * app异常日志接口实现类
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Service
public class UnusualLogServiceImpl implements UnusualLogService {

	@Resource
	private UnusualLogClusterMapper unusualLogClusterMapper;
	@Resource
	private UnusualLogMasterMapper unusualLogMasterMapper;
	@Resource
	private UnusualLogContentMasterMapper unusualLogContentMasterMapper;
	@Resource
	private UnusualLogContentClusterMapper unusualLogContentClusterMapper;
	
	@Override
	public void insert(cn.linkmore.common.controller.app.request.ReqUnusualLog unusualLog) {
		UnusualLog log = ObjectUtils.copyObject(unusualLog, new UnusualLog());
		this.unusualLogMasterMapper.insertSelective(log);
		UnusualLogContent record = new UnusualLogContent();
		record.setContent(unusualLog.getContent());
		record.setLogId(log.getId());
		this.unusualLogContentMasterMapper.insert(record );
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
		Integer count = this.unusualLogClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<UnusualLog> list = this.unusualLogClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	
	
}
