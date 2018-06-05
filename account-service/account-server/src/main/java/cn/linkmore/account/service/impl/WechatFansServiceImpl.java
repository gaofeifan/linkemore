package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.WechatFansClusterMapper;
import cn.linkmore.account.dao.master.WechatFansMasterMapper;
import cn.linkmore.account.entity.WechatFans;
import cn.linkmore.account.request.ReqWechatFans;
import cn.linkmore.account.request.ReqWechatFansExcel;
import cn.linkmore.account.response.ResWechatFans;
import cn.linkmore.account.service.WechatFansService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
/**
 * 微信粉接口实现
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class WechatFansServiceImpl implements WechatFansService {

	@Resource
	private WechatFansClusterMapper wechatFansClusterMapper;
	@Resource
	private WechatFansMasterMapper wechatFansMasterMapper;
	
	
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
		Integer count = this.wechatFansClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<WechatFans> list = this.wechatFansClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public List<ResWechatFans> exportList(ReqWechatFansExcel bean) {
		return this.wechatFansClusterMapper.exportList(bean);
	}

	@Override
	public void saveReq(ReqWechatFans bean) {
		this.wechatFansMasterMapper.saveReq(bean);
	}

	@Override
	public void updateReq(ReqWechatFans bean) {
		this.wechatFansMasterMapper.updateReq(bean);
	}

	
}
