package cn.linkmore.prefecture.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.PrefectureEntranceClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureEntranceMasterMapper;
import cn.linkmore.prefecture.entity.PrefectureEntrance;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;
import cn.linkmore.prefecture.service.PrefectureEntranceService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class PrefectureEntranceServiceImpl implements PrefectureEntranceService {

	@Autowired
	private PrefectureEntranceMasterMapper prefectureEntranceMasterMapper;
	
	@Autowired
	private PrefectureEntranceClusterMapper prefectureEntranceClusterMapper;
	
	@Override
	public int save(ReqPrefectureEntrance reqPrefectureEntrance) {
		PrefectureEntrance preEle = new PrefectureEntrance();
		preEle = ObjectUtils.copyObject(reqPrefectureEntrance, preEle);
		int count=prefectureEntranceMasterMapper.save(preEle);
		return count;
	}

	@Override
	public int update(ReqPrefectureEntrance reqPrefectureEntrance) {
		PrefectureEntrance preEle = new PrefectureEntrance();
		preEle = ObjectUtils.copyObject(reqPrefectureEntrance, preEle);
		int count=prefectureEntranceMasterMapper.update(preEle);
		return count;
	}

	@Override
	public int delete(List<Long> ids) {
		return prefectureEntranceMasterMapper.deleteBatch(ids);
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
		Integer count = this.prefectureEntranceClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<PrefectureEntrance> list = this.prefectureEntranceClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

}
