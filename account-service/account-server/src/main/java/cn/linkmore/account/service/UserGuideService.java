package cn.linkmore.account.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserGuideClusterMapper;
import cn.linkmore.account.dao.master.UserGuideMasterMapper;
import cn.linkmore.account.entity.UserGuide;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserGuide;
import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.StringUtil;

@Service
public class UserGuideService {
	@Resource
	private UserGuideMasterMapper userGuideMapper;
	@Resource
	private UserGuideClusterMapper userGuideClusterMapper ;
	public List<ResUserGuide> find(String language) {
		List<UserGuide> list = userGuideClusterMapper.findAll(); 
		Map<Long,List<UserGuide>> ugm = new HashMap<Long,List<UserGuide>>();
		List<UserGuide> ugs = null;
		for(UserGuide ug:list){
			ugs = ugm.get(ug.getParentId());
			if(ugs==null){
				ugs = new ArrayList<UserGuide>();
				ugm.put(ug.getParentId(), ugs);
			}
			ugs.add(ug);
		}   
		return this.parse(ugm.get(0l), ugm,language);
	}
	private List<ResUserGuide> parse(List<UserGuide> list,Map<Long,List<UserGuide>> ugm,String language){
		List<ResUserGuide> trees = new ArrayList<ResUserGuide>();
		ResUserGuide tree = null;
		if(StringUtil.isNotBlank(language)){
			if(language.equals("zh")){
				for(UserGuide ug:list){
					tree = new ResUserGuide();
					tree.setId(ug.getId());
					tree.setLevel(ug.getLevel());
					tree.setTitle(ug.getTitle());
					tree.setType(ug.getType());
					if(ug.getType()==0&&ugm.get(ug.getId())!=null){
						tree.setChildren(this.parse(ugm.get(ug.getId()), ugm,language)); 
					} else{
						tree.setUrl(ug.getUrl());
					}
					trees.add(tree);
				}
			}else if(language.equals("en")){
				for(UserGuide ug:list){
					tree = new ResUserGuide();
					tree.setId(ug.getId());
					tree.setLevel(ug.getLevel());
					tree.setTitle(ug.getEnTitle());
					tree.setType(ug.getType());
					if(ug.getType()==0&&ugm.get(ug.getId())!=null){
						tree.setChildren(this.parse(ugm.get(ug.getId()), ugm,language)); 
					} else{
						tree.setUrl(ug.getUrl());
					}
					trees.add(tree);
				}
			}
		}else{
			for(UserGuide ug:list){
				tree = new ResUserGuide();
				tree.setId(ug.getId());
				tree.setLevel(ug.getLevel());
				tree.setTitle(ug.getTitle());
				tree.setType(ug.getType());
				if(ug.getType()==0&&ugm.get(ug.getId())!=null){
					tree.setChildren(this.parse(ugm.get(ug.getId()), ugm,language)); 
				} else{
					tree.setUrl(ug.getUrl());
				}
				trees.add(tree);
			}
		}
		return trees;
	}
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void save(ReqUserGuide userGuide) {
		this.userGuideMapper.saveReq(userGuide);
	}
	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void update(ReqUserGuide userGuide) {
		this.userGuideMapper.updateReqById(userGuide);
	}
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void delete(List<Long> ids) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		for(long id:ids){
			param.put("parentId", id);
			if(this.userGuideClusterMapper.count(param)>0){
				throw new DataException("栏目下存在栏目及指南信息");
			}
		}
		this.userGuideMapper.deleteIds(ids);
	}
	
	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Boolean check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("parentId", reqCheck.getParentId());
		param.put("id", reqCheck.getId());
		
		Boolean flag = true ;
		Integer count = this.userGuideClusterMapper.check(param); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	/**
	 * @Description  查询树桩数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Tree findTree() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("type", 0);
		List<UserGuide> rows = this.userGuideClusterMapper.findList(param); 
		Map<Long,List<UserGuide>> ugm = new HashMap<Long,List<UserGuide>>();
		List<UserGuide> ugs = null;
		for(UserGuide ug:rows){
			ugs = ugm.get(ug.getParentId());
			if(ugs==null){
				ugs = new ArrayList<UserGuide>();
				ugm.put(ug.getParentId(), ugs);
			}
			ugs.add(ug);
		}  
		Tree tree = new Tree();
		tree.setName("用户指南栏目树");
		tree.setId("0");
		tree.setIsParent(true);
		tree.setOpen(true);
		tree.setChildren(this.parseTree(ugm.get(0l), ugm));
		return tree; 
	}
	private List<Tree> parseTree(List<UserGuide> list,Map<Long,List<UserGuide>> ugm){
		List<Tree> trees = null;
		Tree tree = null;
		if(list!=null){
			trees = new ArrayList<Tree>();
			for(UserGuide ug:list){
				tree = new Tree();
				tree.setId(ug.getId().toString());
				tree.setpId(ug.getParentId().toString());
				tree.setName(ug.getTitle());
				if(ugm.get(ug.getId())!=null){
					tree.setChildren(this.parseTree(ugm.get(ug.getId()), ugm));
					tree.setIsParent(true);
					tree.setOpen(true);
				}else{
					tree.setIsParent(false);
				}
				trees.add(tree);
			}
		} 
		return trees;
	}
	
	/**
	 * @Description  查询分页
	 * @Author   GFF 
	 * @Version  v2.0
	 */
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
		Integer count = this.userGuideClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<UserGuide> list = this.userGuideClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
}
