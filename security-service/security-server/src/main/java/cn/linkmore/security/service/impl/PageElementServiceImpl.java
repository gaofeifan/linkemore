package cn.linkmore.security.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.dao.cluster.ClazzClusterMapper;
import cn.linkmore.security.dao.cluster.DictClusterMapper;
import cn.linkmore.security.dao.cluster.InterfaceClusterMapper;
import cn.linkmore.security.dao.cluster.PageClusterMapper;
import cn.linkmore.security.dao.cluster.PageElementClusterMapper;
import cn.linkmore.security.dao.master.PageElementMasterMapper;
import cn.linkmore.security.entity.PageElement;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPageElement;
import cn.linkmore.security.response.ResAuthElement;
import cn.linkmore.security.response.ResClazz;
import cn.linkmore.security.response.ResDict;
import cn.linkmore.security.response.ResInterface;
import cn.linkmore.security.response.ResPage;
import cn.linkmore.security.response.ResPageElement;
import cn.linkmore.security.service.PageElementService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;


/**
 * Service实现类 -权限模块 - 页面元素信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PageElementServiceImpl implements PageElementService {

	@Autowired
	private PageClusterMapper pageClusterMapper;
	
	@Autowired
	private ClazzClusterMapper clazzClusterMapper;
	
	@Autowired
	private InterfaceClusterMapper interfaceClusterMapper;
	
	@Autowired
	private DictClusterMapper dictClusterMapper;
	
	@Autowired
	private PageElementClusterMapper pageElementClusterMapper;
	
	@Autowired
	private PageElementMasterMapper pageElementMasterMapper;
	
	@Override
	public Tree findTree(){
		List<ResPage> list =  this.pageClusterMapper.findAll();
		List<ResDict> dicts = this.dictClusterMapper.findByGroupCode("security-page-category");
		List<Tree> trees = new ArrayList<Tree>();
		Map<Long,Tree> treeMap = new HashMap<Long,Tree>();
		Tree tree  =null;
		for(ResDict di:dicts) { 
			tree = new Tree(); 
			tree.setId(di.getId().toString());
			tree.setName(di.getName());
			tree.setIsParent(true);
			tree.setCode(di.getCode());
			tree.setmId(di.getId().toString());
			tree.setOpen(true);
			tree.setChildren(new ArrayList<Tree>());
			trees.add(tree); 
			treeMap.put(di.getId(), tree);
		} 
		Tree child = null;
		for(ResPage page:list) {
			tree = treeMap.get(page.getCategoryId().longValue());
			if(tree==null) {
				continue;
			}
			child = new Tree();
			child.setId(page.getId().toString());
			child.setpId(tree.getId());
			child.setName(page.getName());
			child.setIsParent(false);
			child.setCode(page.getId().toString());
			child.setmId(page.getId().toString()); 
			tree.getChildren().add(child);
		}
		Tree root = new Tree();
		root.setName("系统页面树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0"); 
		root.setChildren(trees);
		return root;
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
		Integer count = this.pageElementClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResPageElement> list = this.pageElementClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public int save(ReqPageElement reqPageElement) {
		PageElement pageElement = new PageElement();
		pageElement = ObjectUtils.copyObject(reqPageElement, pageElement);
		pageElement.setCreateTime(new Date());
		return this.pageElementMasterMapper.save(pageElement);
	}
	
	@Override
	public int update(ReqPageElement reqPageElement) {
		PageElement pageElement = new PageElement();
		pageElement = ObjectUtils.copyObject(reqPageElement, pageElement);
		return this.pageElementMasterMapper.update(pageElement);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.pageElementMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("pageId", reqCheck.getPageId());
		param.put("id", reqCheck.getId());
		return this.pageElementClusterMapper.check(param); 
	}
	
	@Override
	public Map<String,Object> map() {
		List<ResDict> dicts = this.dictClusterMapper.findByGroupCode("security-clazz-category");
		List<ResClazz> clazzs = this.clazzClusterMapper.findAll(); 
		List<ResInterface> records = this.interfaceClusterMapper.findAll();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("category", dicts);
		map.put("clazz", clazzs);
		map.put("interface", records);
		return map;
	}

	@Override
	public List<ResAuthElement> findResAuthElementList() { 
		return this.pageElementClusterMapper.findResAuthElementList(); 
	}
}
