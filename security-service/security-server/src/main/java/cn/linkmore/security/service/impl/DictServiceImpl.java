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
import cn.linkmore.security.dao.cluster.DictClusterMapper;
import cn.linkmore.security.dao.cluster.DictGroupClusterMapper;
import cn.linkmore.security.dao.master.DictMasterMapper;
import cn.linkmore.security.entity.Dict;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.response.ResDict;
import cn.linkmore.security.response.ResDictGroup;
import cn.linkmore.security.service.DictService;
import cn.linkmore.util.DomainUtil;


/**
 * Service实现类 -权限模块 - 字典信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private DictClusterMapper dictClusterMapper;
	@Autowired
	private DictMasterMapper dictMasterMapper;
	@Autowired
	private DictGroupClusterMapper dictGroupClusterMapper;
	
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
		Integer count = this.dictClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResDict> list = this.dictClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Dict dict) {
		dict.setCreateTime(new Date());
		this.dictMasterMapper.save(dict);
	}
	
	@Override
	public Dict update(Dict dict) {
		this.dictMasterMapper.update(dict);
		return dict;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.dictMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.dictClusterMapper.check(param); 
	}

	@Override
	public Tree findTree(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", "order_index");
		param.put("direction", "asc");
		List<ResDictGroup> list =  this.dictGroupClusterMapper.findList(param);
		List<Tree> trees = new ArrayList<Tree>();
		Tree root = new Tree();
		root.setName("数据字典分类树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0"); 
		root.setChildren(trees);
		Tree tree = null;
		List<Tree> children = new ArrayList<Tree>();
		for(ResDictGroup dg:list) {
			tree = new Tree();
			tree.setName(dg.getName());
			tree.setCode(dg.getCode());
			tree.setmId(dg.getId().toString());
			tree.setId(dg.getId().toString());
			tree.setIsParent(false); 
			children.add(tree);
		}
		root.setChildren(children);
		return root;
	}

	@Override
	public List<ResDict> findByGroupCode(String code) {
		return this.dictClusterMapper.findByGroupCode(code);
	}
}
