package cn.linkmore.enterprise.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseDealClusterMapper;
import cn.linkmore.enterprise.dao.master.EnterpriseDealMasterMapper;
import cn.linkmore.enterprise.entity.EnterpriseDeal;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
import cn.linkmore.enterprise.service.EnterpriseDealService;
import cn.linkmore.security.client.DictClient;
import cn.linkmore.security.client.PageClient;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
@Transactional
public class EnterpriseDealServiceImpl implements EnterpriseDealService {
	@Autowired
	private PageClient pageClient; 
	@Autowired
	private EnterpriseDealClusterMapper enterpriseDealClusterMapper;
	
	@Autowired
	private EnterpriseDealMasterMapper enterpriseDealMasterMapper;
	
	@Autowired
	private EnterpriseClusterMapper enterpriseClusterMapper;
	@Autowired
	private DictClient dictClient;
	@Autowired
	private BaseDictClient baseDictClient;
	
	public static final String INDUSTRY = "biz-enterprise-industry";
	
	@Override
	public int save(ReqEnterpriseDeal reqDeal) {
		EnterpriseDeal deal = ObjectUtils.copyObject(reqDeal, new EnterpriseDeal(),null,null,new String[] {"usedDealPayAmount","userDealGiftAmount"});
		deal.setUsedDealPayAmount(new BigDecimal(reqDeal.getUsedDealPayAmount()));
		deal.setUserDealGiftAmount(new BigDecimal(reqDeal.getUserDealGiftAmount()));
		return this.enterpriseDealMasterMapper.save(deal);
	}

	@Override
	public int update(ReqEnterpriseDeal reqDeal) {
		EnterpriseDeal deal = ObjectUtils.copyObject(reqDeal, new EnterpriseDeal(),null,null,new String[] {"usedDealPayAmount","userDealGiftAmount"});
		this.enterpriseDealMasterMapper.updateByIdSelective(deal);		
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.enterpriseDealMasterMapper.delete(ids);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.enterpriseDealClusterMapper.check(param); 
	}

	@Override
	public List<Tree> findTree() {
		List<Tree> pchildren = null;
		List<Tree> children = null;
		List<Tree> roots = new ArrayList<>();
		List<ResBaseDict> findByGroupCode = this.baseDictClient.findList(INDUSTRY);
		Tree root = null;
		for (ResBaseDict dict : findByGroupCode) {
			root = new Tree();
			root.setName(dict.getName());
			root.setId(dict.getId().toString());
			root.setIsParent(false);
			root.setCode(dict.getId().toString());
			root.setOpen(true);
			root.setmId(dict.getId().toString());
			roots.add(root);
		}
		for (Tree tree : roots) {
			int rootId = Integer.parseInt(tree.getId());
			List<ResEnterprise> regions = this.enterpriseClusterMapper.selectRegionByIndustry(rootId);
			if(regions.size() == 0){
				continue;
			}
			pchildren = new ArrayList<>();
			Tree tree1;
			for (ResEnterprise region : regions) {
				tree1 = new Tree();
				tree1.setName(region.getAddress());
				tree1.setId(region.getRegion().toString());
				tree1.setCode(region.getRegion().toString());
				tree1.setOpen(true);
				tree1.setpId(tree.getId());
				List<ResEnterprise> data = getEnterpriseData((long)rootId, region.getRegion());
				if(data.size() != 0){
					Tree tree2;
					children = new ArrayList<>();
					for (ResEnterprise enterprise : data) {
						tree2 = new Tree();
						tree2.setName(enterprise.getName());
						tree2.setId(enterprise.getId().toString());
						tree2.setCode(enterprise.getId().toString());
						tree2.setOpen(true);
						tree2.setIsParent(false);
						tree2.setpId(region.getRegion().toString());
						children.add(tree2);
					}
					tree1.setChildren(children);
				}
				pchildren.add(tree1);
			}
			tree.setChildren(pchildren);
		}
		List<Tree> deleteRoot = new ArrayList<>();
		for (Tree tree : roots) {
			if(tree != null && (tree.getChildren() == null || tree.getChildren().size() ==0)){
				deleteRoot.add(tree);
			}
		}
		roots.removeAll(deleteRoot);
		return roots;
	}
	
	@Override
	public Map<String, Object> map() {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ResEnterprise> list = this.enterpriseClusterMapper.findList(new HashMap<>());
		param.put("category", list);
		/*List<ResPage> pages = this.pageClient.findAll();
		param.put("page", pages);*/
		return param;
	}

	private List<ResEnterprise> getEnterpriseData( Long industryId ,Long regionId){
		List<ResEnterprise> list = this.enterpriseClusterMapper.findList(new HashMap<>());
		if(list.size() != 0){
			List<ResEnterprise> collect = list.stream().filter(en -> en.getIndustry() ==industryId).filter(en -> en.getRegion() == regionId).collect(Collectors.toList());
			return collect;
		}
		return null;
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
		Integer count = this.enterpriseDealClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResEnterpriseDeal> list = this.enterpriseDealClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId ,Integer isCreate) {
		return this.enterpriseDealClusterMapper.listByEnterpriseId(enterpriseId,isCreate);
	}

	@Override
	public ResEnterpriseDeal selectByDealNumber(String number) {
		return this.enterpriseDealClusterMapper.selectByDealNumber(number);
	}

	@Override
	public void updateCreateStatus(Map<String, Object> map) {
		this.enterpriseDealMasterMapper.updateCreateStatus(map);
	}
	
	
	
	
}
