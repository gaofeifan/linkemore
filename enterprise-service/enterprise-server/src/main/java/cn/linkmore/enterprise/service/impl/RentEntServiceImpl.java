package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.RentEntClusterMapper;
import cn.linkmore.enterprise.dao.cluster.RentEntStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.RentEntUserClusterMapper;
import cn.linkmore.enterprise.dao.master.RentEntMasterMapper;
import cn.linkmore.enterprise.dao.master.RentEntUserMasterMapper;
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
	
	@Resource
	private RentEntUserClusterMapper rentEntUserClusterMapper;
	
	@Resource
	private RentEntUserMasterMapper rentEntUserMasterMapper;
	
	@Resource
	private RentEntStallClusterMapper rentEntStallClusterMapper;
	@Override
	public RentEnt findById(Long id) {
		return this.rentEntClusterMapper.findById(id);
	}

	@Override
	public void save(ReqRentEnt ent) {
		RentEnt rentEnt = new RentEnt();
		this.rentEntMasterMapper.save(ObjectUtils.copyObject(ent, rentEnt));
		List<ReqRentEntStall> list = ent.getStalls();
		if(CollectionUtils.isNotEmpty(list)) {
			for (ReqRentEntStall reqRentEntStall : list) {
				reqRentEntStall.setRentComId(rentEnt.getId());
			}
			this.rentEntStallService.saveBatch(list);
		}
		
	}
	
	@Override
	public void update(ReqRentEnt ent) {
		this.rentEntMasterMapper.update(ObjectUtils.copyObject(ent, new RentEnt()));
	}
	
	@Override
	public void delete(List<Long> ids) {
		this.rentEntMasterMapper.deleteByIds(ids);
		this.rentEntStallService.deleteByCompanyIds(ids);
		this.rentEntUserMasterMapper.deleteByCompanyIds(ids);
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
		if(CollectionUtils.isNotEmpty(list)) {
			for(RentEnt rentEnt: list) {
				param.put("rentComId", rentEnt.getId());
				Integer stallCount = this.rentEntStallClusterMapper.count(param);
				Integer userCount = this.rentEntUserClusterMapper.count(param);
				rentEnt.setStallCount(stallCount);
				rentEnt.setUserCount(userCount);
			}
		}
		
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public ViewPage stallListCompany(ViewPageable pageable) {
		return rentEntStallService.stallListCompany(pageable);
	}

	@Override
	public List<Tree> tree(Long entId) {
		List<RentEnt> list = this.rentEntClusterMapper.findByComId(entId);
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

	@Override
	public int updateStatus(Map<String, Object> map) {
		return rentEntMasterMapper.updateStatus(map);
	}

	@Override
	public void deleteStall(List<Long> ids) {
		this.rentEntStallService.deleteStall(ids);
	}

	@Override
	public void saveStall(ReqRentEnt ent) {
		List<ReqRentEntStall> list = ent.getStalls();
		if(CollectionUtils.isNotEmpty(list)) {
			this.rentEntStallService.saveBatch(list);
		}
	}
	
	@Override
	public void updateOverdueStatus() {
		System.out.println("updateOverdueStatus");
		rentEntMasterMapper.updateOverdueStatus();
	}
	
}
