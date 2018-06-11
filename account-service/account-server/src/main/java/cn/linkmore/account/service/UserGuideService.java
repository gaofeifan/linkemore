package cn.linkmore.account.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserGuideClusterMapper;
import cn.linkmore.account.dao.master.UserGuideMasterMapper;
import cn.linkmore.account.entity.UserGuide;
import cn.linkmore.account.response.ResUserGuide;
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
}
