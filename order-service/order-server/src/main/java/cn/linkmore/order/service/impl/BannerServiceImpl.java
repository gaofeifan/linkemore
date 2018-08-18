package cn.linkmore.order.service.impl;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.dao.cluster.WalletBannerClusterMapper;
import cn.linkmore.order.dao.master.WalletBannerMasterMapper;
import cn.linkmore.order.entity.WalletBanner;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
import cn.linkmore.order.service.BannerService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.StringUtil;


@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class BannerServiceImpl implements BannerService {
	
	
	@Resource
	private WalletBannerClusterMapper walletBannerMapper;
	@Resource
	private WalletBannerMasterMapper walletBannerMasterMapper;


	@Override
	public void save(ReqWalletBanner banner) {
		if(StringUtils.isNotBlank(banner.getImage())){
			banner.setImage("http://oss.pabeitech.com/image"+banner.getImage());
		}
		banner.setCreateTime(new Date());
		banner.setStatus(0);
		walletBannerMasterMapper.saveReq(banner);
	}

	@Override
	public void update(ReqWalletBanner b) {
		WalletBanner ban = this.walletBannerMapper.findById(b.getId());
		ban.setImage(b.getImage());
		ban.setOrderIndex(b.getOrderIndex());
		ban.setPath(b.getPath());
		ban.setStatus(0);
		if(!b.getImage().equals(ban.getImage())){
			ban.setImage("http://oss.pabeitech.com/image"+b.getImage());
		}
		this.walletBannerMasterMapper.updateById(ban);
	}

	@Override
	public void setStatus(Long bid, int status) {
		updateColumnValue(bid," status ",status);
	}

	@Override
	public void updateColumnValue(Long bid, String column, int status) {
		this.walletBannerMasterMapper.updateColumnValue(bid,column,status);
	}

	@Override
	public WalletBanner findById(Long bid) {
		return walletBannerMapper.findById(bid);
	}

	@Override
	public int findStatusCount() {
		Map<String, Object> map = new HashMap<>();
		map.put("status"+StringUtil.EQUALS, 1);
		String sql = StringUtil.joinSql(map);
		List<ResWalletBanner> list = this.selectList(sql);
		return list.size();
	}
	
	@Override
	public List<ResWalletBanner> selectList(String sql) {
		return this.walletBannerMapper.findList(sql);
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
		Integer count = this.walletBannerMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<WalletBanner> list = this.walletBannerMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void setStatus(List<Long> ids, int status) {
		StringBuffer join = new StringBuffer();
		for (Long long1 : ids) {
			join.append(long1).append(",");
		}
		this.walletBannerMasterMapper.updateStatus(join.substring(0, join.length()-1),status);
	}

	

}
