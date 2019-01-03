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
import cn.linkmore.prefecture.dao.cluster.PrefectureElementClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureElementMasterMapper;
import cn.linkmore.prefecture.entity.PrefectureElement;
import cn.linkmore.prefecture.request.ReqPrefectureElement;
import cn.linkmore.prefecture.service.PrefectureElementService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class PrefectureElementServiceImpl implements PrefectureElementService {

	@Autowired
	private PrefectureElementMasterMapper prefectureElementMasterMapper;
	
	@Autowired
	private PrefectureElementClusterMapper prefectureElementClusterMapper;
	
	@Override
	public int save(ReqPrefectureElement reqPrefectureElement) {
		PrefectureElement preEle = new PrefectureElement();
		preEle = ObjectUtils.copyObject(reqPrefectureElement, preEle);
		int count=prefectureElementMasterMapper.save(preEle);
		return count;
	}

	@Override
	public int update(ReqPrefectureElement reqPrefectureElement) {
		PrefectureElement preEle = new PrefectureElement();
		preEle = ObjectUtils.copyObject(reqPrefectureElement, preEle);
		int count=prefectureElementMasterMapper.update(preEle);
		return count;
	}

	@Override
	public int delete(List<Long> ids) {
		return prefectureElementMasterMapper.deleteBatch(ids);
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
		Integer count = this.prefectureElementClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<PrefectureElement> list = this.prefectureElementClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

}
