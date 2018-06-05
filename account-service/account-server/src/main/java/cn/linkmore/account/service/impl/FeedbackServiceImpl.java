package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.FeedbackClusterMapper;
import cn.linkmore.account.dao.master.FeedbackMasterMapper;
import cn.linkmore.account.request.ReqFeedBack;
import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.account.service.FeedbackService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
/**
 * 问题反馈接口实现
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Resource
	private FeedbackClusterMapper feedbackClusterMapper;
	@Resource
	private FeedbackMasterMapper feedbackMasterMapper;
	
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
		Integer count = this.feedbackClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResFeedBack> list = this.feedbackClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public List<ResFeedBack> exportList(ReqFeedBack bean) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		if(bean.getMobile()!=null){
			param.put("mobile", bean.getMobile());
		}
		if(bean.getNickname()!=null){
			param.put("nickname", bean.getNickname());
		}
		if(bean.getStartTime()!=null){
			param.put("startTime", bean.getStartTime());
		}
		if(bean.getNickname()!=null){
			param.put("endTime", bean.getEndTime());
		}
		return  this.feedbackClusterMapper.findExportList(param); 
	} 

	
}
