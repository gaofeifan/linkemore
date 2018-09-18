package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.StallAssignClusterMapper;
import cn.linkmore.prefecture.dao.master.StallAssignMasterMapper;
import cn.linkmore.prefecture.entity.StallAssign;
import cn.linkmore.prefecture.response.ResStallAssign;
import cn.linkmore.prefecture.service.StallAssignService;
import cn.linkmore.util.DomainUtil;

/**
 * Service实现类 - 车位指定记录
 * @author liwenlong
 * @version 1.0
 *
 */
@Service
public class StallAssignServiceImpl implements StallAssignService {
	
	@Autowired
	private StallAssignClusterMapper stallAssignClusterMapper; 
	@Autowired
	private StallAssignMasterMapper assignMasterMapper;
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
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty().replaceAll(" ", "")));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.stallAssignClusterMapper.count(param);
		List<ResStallAssign> list = new ArrayList<ResStallAssign>();
		if(count>0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.stallAssignClusterMapper.findPage(param);
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public void save(StallAssign sa) {
		this.assignMasterMapper.save(sa);
		
	}

	@Override
	public StallAssign find(String lockSn) {
		return this.stallAssignClusterMapper.findByLockSn(lockSn);
	}

	@Override
	public void cancel(StallAssign sa) {
		this.assignMasterMapper.cancel(sa);
	}

	
	
	
	
}
