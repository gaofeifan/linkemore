package cn.linkmore.security.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.dao.cluster.ClazzClusterMapper;
import cn.linkmore.security.dao.cluster.DictClusterMapper;
import cn.linkmore.security.dao.cluster.InterfaceClusterMapper;
import cn.linkmore.security.dao.master.InterfaceMasterMapper;
import cn.linkmore.security.entity.Interface;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.response.ResClazz;
import cn.linkmore.security.response.ResDict;
import cn.linkmore.security.response.ResInterface;
import cn.linkmore.security.service.InterfaceService;
import cn.linkmore.util.DomainUtil;


/**
 * Service实现类 -权限模块 - 接口信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {

	@Autowired
	private InterfaceClusterMapper interfaceClusterMapper;
	@Autowired
	private InterfaceMasterMapper interfaceMasterMapper;
	@Autowired
	private ClazzClusterMapper clazzClusterMapper;
	@Autowired
	private DictClusterMapper dictClusterMapper;
	
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
		Integer count = this.interfaceClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResInterface> list = this.interfaceClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Interface inter) {
		inter.setCreateTime(new Date());
		this.interfaceMasterMapper.save(inter);
	}
	
	@Override
	public Interface update(Interface inter) {
		this.interfaceMasterMapper.update(inter);
		return inter;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.interfaceMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.interfaceClusterMapper.check(param); 
	}

	@Override
	public Tree findTree() {
		List<ResClazz> list =  this.clazzClusterMapper.findAll();
		List<ResDict> dicts = this.dictClusterMapper.findByGroupCode("security-clazz-category");
		List<Tree> trees = new ArrayList<Tree>();
		Map<Long,Tree> treeMap = new HashMap<Long,Tree>();
		Tree tree = null;
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
		tree  = new Tree(); 
		tree.setId("0");
		tree.setName("未分类");
		tree.setIsParent(true);
		tree.setCode("0");
		tree.setmId("0");
		tree.setOpen(true);
		tree.setChildren(new ArrayList<Tree>());
		trees.add(tree); 
		Tree child = null;
		for(ResClazz clazz:list) {
			tree = treeMap.get(clazz.getPackageId());
			if(tree==null) {
				continue;
			}
			child = new Tree();
			child.setId(clazz.getId().toString());
			child.setpId(tree.getId());
			child.setName(clazz.getName());
			child.setIsParent(false);
			child.setCode(clazz.getId().toString());
			child.setmId(clazz.getId().toString()); 
			tree.getChildren().add(child);
		}
		Tree root = new Tree();
		root.setName("系统菜单树");
		root.setId("a");
		root.setIsParent(false);
		root.setCode("a");
		root.setOpen(true);
		root.setmId("a"); 
		root.setChildren(trees);
		return root;
	}
}
