package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.ShareClusterMapper;
import cn.linkmore.account.dao.master.ShareMasterMapper;
import cn.linkmore.account.entity.Share;
import cn.linkmore.account.service.ShareService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
/**
 * 分享记录
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class ShareServiceImpl implements ShareService {

	@Resource
	private ShareClusterMapper shareClusterMapper;
	@Resource
	private ShareMasterMapper shareMasterMapper;
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
		Integer count = this.shareClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<Share> list = this.shareClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	
	
	
}
