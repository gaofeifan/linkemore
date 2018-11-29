package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.enterprise.dao.cluster.RentEntClusterMapper;
import cn.linkmore.enterprise.dao.master.RentEntMasterMapper;
import cn.linkmore.enterprise.entity.RentEnt;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.enterprise.request.ReqRentEntStall;
import cn.linkmore.enterprise.service.RentEntService;
import cn.linkmore.enterprise.service.RentEntStallService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class RentEntServiceImpl implements RentEntService {

	@Resource
	private RentEntClusterMapper rentEntClusterMapper;
	@Resource
	private RentEntMasterMapper rentEntMasterMapper;
	@Resource
	private RentEntStallService rentEntStallService;
	@Override
	public RentEnt selectByPrimaryKey(Long id) {
		return this.rentEntClusterMapper.selectByPrimaryKey(id);
	}

	@Override
	public void save(ReqRentEnt ent) {
		RentEnt rentEnt = new RentEnt();
		this.rentEntMasterMapper.insert(ObjectUtils.copyObject(ent, rentEnt));
		List<ReqRentEntStall> list = ent.getStalls();
		for (ReqRentEntStall reqRentEntStall : list) {
			reqRentEntStall.setRentEntId(rentEnt.getId());
		}
		this.rentEntStallService.saveBatch(list);
	}
	
	@Override
	public void update(ReqRentEnt ent) {
		this.rentEntMasterMapper.updateByPrimaryKey(ObjectUtils.copyObject(ent, new RentEnt()));
	}
	
	@Override
	public void delete(List<Long> ids) {
		this.rentEntMasterMapper.deleteByIds(ids);
	}
	
	@Override
	public ViewPage findPage(ViewPageable pageable){
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.rentEntClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<RentEnt> list = this.rentEntClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public ViewPage stallListCompany(ViewPageable pageable) {
		return rentEntStallService.stallListCompany(pageable);
	}

	@Override
	public List<Tree> tree(Long entId) {
		List<RentEnt> list = this.rentEntClusterMapper.findByEntId(entId);
		List<Tree> roots = new ArrayList<>();
		Tree root = null;
		for (RentEnt dict : list) {
			root = new Tree();
			root.setName(dict.getCompanyName());
			root.setId(dict.getId().toString());
			root.setIsParent(false);
			root.setCode(dict.getId().toString());
			root.setOpen(true);
			root.setmId(dict.getId().toString());
			roots.add(root);
		}
		return roots;
	}
	
	
	
}
