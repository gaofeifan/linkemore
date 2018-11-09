package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyGroupClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyGroupDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.StrategyGroupDetailMasterMapper;
import cn.linkmore.prefecture.dao.master.StrategyGroupMasterMapper;
import cn.linkmore.prefecture.entity.StrategyGroup;
import cn.linkmore.prefecture.entity.StrategyGroupDetail;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;
import cn.linkmore.prefecture.response.ResStrategyGroupDetail;
import cn.linkmore.prefecture.service.StrategyGroupService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class StrategyGroupServiceImpl implements StrategyGroupService {

	@Autowired
	private StrategyGroupMasterMapper strategyGroupMasterMapper;
	
	@Autowired
	private StrategyGroupClusterMapper strategyGroupClusterMapper;
	
	
	@Autowired
	private StrategyGroupDetailMasterMapper strategyGroupDetailMasterMapper;
	
	@Autowired
	private StrategyGroupDetailClusterMapper strategyGroupDetailClusterMapper;
	
	@Autowired
	private StallClusterMapper stallClusterMapper;
	
	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper;

	@Override
	public int save(ReqStrategyGroup reqStrategyGroup) {
		StrategyGroup strategyGroup = new StrategyGroup();
		strategyGroup = ObjectUtils.copyObject(reqStrategyGroup, strategyGroup);
		strategyGroup.setParkingCount(reqStrategyGroup.getStrategyGroupDetail().size());
		
		ResPrefectureDetail resPrefectureDetail = resPrefectureDetail = prefectureClusterMapper.findById(strategyGroup.getPrefectureId());
		if(resPrefectureDetail!=null) {
			strategyGroup.setPrefectureName(resPrefectureDetail.getName());
		}
		
		/*
		Map<String,Object> param= new HashMap<String,Object>();
		param.put("createUserId",strategyGroup.getCreateUserId());
		List<ResPre> preList = prefectureClusterMapper.findTreeList(param);
		if(CollectionUtils.isNotEmpty(preList)&& preList.size()>0){
			strategyGroup.setPrefectureId(preList.get(0).getId());
		}
*/
		int count=strategyGroupMasterMapper.insert(strategyGroup);
		if(reqStrategyGroup.getStrategyGroupDetail()!=null) {
			for (ReqStrategyGroupDetail reqStrategyGroupDetail : reqStrategyGroup.getStrategyGroupDetail()) {
				StrategyGroupDetail strategyGroupDetail = new StrategyGroupDetail();
				strategyGroupDetail = ObjectUtils.copyObject(reqStrategyGroupDetail, strategyGroupDetail);
				strategyGroupDetail.setStrategyGroupId(strategyGroup.getId());
				strategyGroupDetailMasterMapper.insert(strategyGroupDetail);
			}
		}
		return count;
	}

	@Override
	public int update(ReqStrategyGroup reqStrategyGroup) {
		StrategyGroup strategyGroup = new StrategyGroup();
		strategyGroup = ObjectUtils.copyObject(reqStrategyGroup, strategyGroup);
		int count=strategyGroupMasterMapper.updateByPrimaryKey(strategyGroup);
		return count;
	}

	@Override
	public int delete(List<Long> ids) {
		strategyGroupDetailMasterMapper.delete(ids);
		return strategyGroupMasterMapper.delete(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return strategyGroupMasterMapper.updateStatus(map);
	}

	@Override
	public ResStrategyGroup selectByPrimaryKey(Long id) {
		ResStrategyGroup resStrategyGroup = strategyGroupClusterMapper.selectByPrimaryKey(id);
		List<ResStrategyGroupDetail> listResStrategyGroupDetail =new ArrayList<ResStrategyGroupDetail>();
		List<StrategyGroupDetail> findList = strategyGroupDetailClusterMapper.findList(id);
		if(findList!=null) {
			for (StrategyGroupDetail strategyGroupDetail:findList) {
				ResStrategyGroupDetail resStrategyGroupDetail = new ResStrategyGroupDetail();
				resStrategyGroupDetail = ObjectUtils.copyObject(strategyGroupDetail, resStrategyGroupDetail);
				listResStrategyGroupDetail.add(resStrategyGroupDetail);
			}
		}
		resStrategyGroup.setStrategyGroupDetail(listResStrategyGroupDetail);
		return resStrategyGroup;
	}

	@Override
	public List<ResStrategyGroupArea> selectStallByPrimaryKey(Long id) {
		//分组信息
		ResStrategyGroup strategyGroup=strategyGroupClusterMapper.selectByPrimaryKey(id);
		//分组下的车位信息List
		List<StrategyGroupDetail> listGroupDetail = strategyGroupDetailClusterMapper.findList(id);
		//返回的分区信息
		List<ResStrategyGroupArea> listResStrategyGroupArea=new ArrayList<ResStrategyGroupArea>();
		//ResStrategyGroupArea resStrategyGroupArea=new ResStrategyGroupArea();
		
		//取出preid下的所有分区
		List<String> listArea = stallClusterMapper.findAllAreaByPreId(strategyGroup.getPrefectureId());
		for (int i=0;i<listArea.size();i++) {
			ResStrategyGroupArea resStrategyGroupArea=new ResStrategyGroupArea();
			resStrategyGroupArea.setAreaName(listArea.get(i));
			resStrategyGroupArea.setStrategyGroupDetail(new ArrayList<ResStrategyGroupDetail>());
			listResStrategyGroupArea.add(resStrategyGroupArea);
		}
		
		int len=listGroupDetail.size();
		for(int i=0;i<len;i++) {
			int index=findAreaIndex(listResStrategyGroupArea,listGroupDetail.get(i).getAreaName());
			if(index<0) {
				ResStrategyGroupArea resStrategyGroupArea=new ResStrategyGroupArea();
				resStrategyGroupArea.setAreaName(listGroupDetail.get(i).getAreaName());
				resStrategyGroupArea.setStrategyGroupDetail(new ArrayList<ResStrategyGroupDetail>());
				listResStrategyGroupArea.add(resStrategyGroupArea);
				index=listResStrategyGroupArea.size()-1;
			}
			ResStrategyGroupDetail resStrategyGroupDetail = new ResStrategyGroupDetail();
			resStrategyGroupDetail = ObjectUtils.copyObject(listGroupDetail.get(i), resStrategyGroupDetail);
			listResStrategyGroupArea.get(index).getStrategyGroupDetail().add(resStrategyGroupDetail);
		}
		return listResStrategyGroupArea;
	}

	private int findAreaIndex(List<ResStrategyGroupArea> listResStrategyGroupArea,String name) {
		int len=listResStrategyGroupArea.size();
		for (int i=0;i<len;i++) {
			if( StringUtils.equalsIgnoreCase( listResStrategyGroupArea.get(i).getAreaName(),name) ) {
				return i;
			}
		}
		return -1;
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
		
		Integer count = this.strategyGroupClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResStrategyGroup> list = this.strategyGroupClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	
	@Override
	public Tree findTree(Map<String, Object> param) {
		//分组间隔(每组的车位个数)
		int parkingInterval=0;
		if(param !=null && param.get("parkingInterval")!=null) {
			parkingInterval=Integer.parseInt(param.get("parkingInterval").toString());
		}

		ResPrefectureDetail resPrefectureDetail =null;
		if(param !=null && param.get("prefectureId")!=null) {
			resPrefectureDetail = prefectureClusterMapper.findById(Long.parseLong(param.get("prefectureId").toString()));
		}
		
		if (resPrefectureDetail==null ) {
			return  new Tree();
		}
		
		/*
		List<ResPre> preList = prefectureClusterMapper.findTreeList(param);
		if (preList==null || preList.size()<=0) {
			return  new Tree();
		}
		param.put("preId", preList.get(0).getId());
		*/
		
		//根节点
		Tree root = new Tree();
		//一级节点，车位分区节点
		List<Tree> listAreaNode = new ArrayList<Tree>();
		//二级节点,分区下的分组节点
		List<Tree> listAreaBlockNode = new ArrayList<Tree>();
		//车位数据
		List<ResStall> findAreaList = stallClusterMapper.findListByArea(param);

		int len=findAreaList.size();
		
		Map<String,Integer> areaStallCount=new HashMap<String,Integer>();
		
		String startName=null;
		String endName=null;

		for(int i=0;i<len;i++) {
			int tmpStallCount=0;
			if(areaStallCount.get(findAreaList.get(i).getAreaName())!=null ) {
				tmpStallCount=areaStallCount.get(findAreaList.get(i).getAreaName());
			}
			tmpStallCount++;
			areaStallCount.put(findAreaList.get(i).getAreaName(), tmpStallCount);
			if(tmpStallCount % parkingInterval== 1 || parkingInterval==1) {
				startName=findAreaList.get(i).getStallName();
			}
			
			if(tmpStallCount % parkingInterval== 0
				|| 	i>= (len-1 )
				|| 	! StringUtils.equalsIgnoreCase(findAreaList.get(i).getAreaName(),findAreaList.get(i+1).getAreaName())
					) {
				endName=findAreaList.get(i).getStallName();
				Tree tree =  new Tree();
				tree.setId("B" + i);
				tree.setName(startName+"到"+endName+"号车位");
				tree.setT(findAreaList.get(i).getAreaName()+"#"+startName+"#"+endName);
				tree.setIsParent(false);
				tree.setCode("" +i);
				tree.setmId("" +i);
				tree.setOpen(true);
				tree.setChildren(new ArrayList<Tree>());
				
				int index = findAreaNode(listAreaNode, findAreaList.get(i).getAreaName());
				if(index>=0) {
					//分区存在
					listAreaNode.get(index).getChildren().add(tree);
				}else {
					Tree areaNode=new Tree();
					areaNode.setName(findAreaList.get(i).getAreaName());
					areaNode.setId("A"+i);
					areaNode.setIsParent(false);
					areaNode.setCode("1");
					areaNode.setOpen(true);
					areaNode.setmId("1");
					listAreaBlockNode.add(tree);
					areaNode.setChildren(listAreaBlockNode);
					listAreaBlockNode=new ArrayList<Tree>();
					listAreaNode.add(areaNode);
				}
			}
		}
		//根结点
		root.setName( resPrefectureDetail.getName() );
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0");
		root.setChildren(listAreaNode);

		return root;
	}

	/**
	 * 按名称查找分区节点,反回下list下标
	 * @param listAreaNode
	 * @param areaName
	 * @return
	 */
	private int findAreaNode(List<Tree> listAreaNode,String areaName){

		int len=listAreaNode.size();
		for(int i=0;i<len;i++) {
			if(StringUtils.equalsIgnoreCase(listAreaNode.get(i).getName(), areaName)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public List<ResStall> findAreaStall(Map<String, Object> param) {
		/*
		List<ResPre> preList = prefectureClusterMapper.findTreeList(param);
		if (preList==null || preList.size()<=0) {
			return new ArrayList<ResStall>();
		}
		param.put("preId", preList.get(0).getId());
		*/
		
		return stallClusterMapper.findListByArea(param);
	}

	@Override
	public int deleteStall(Map<String, Object> map) {
		Long strategyGroupId=Long.valueOf(String.valueOf(map.get("strategyGroupId")));
		List<Long> ids=null;
		try {
			ids = new ObjectMapper().readValue(String.valueOf(map.get("ids")),new TypeReference<List<Long>>() { });
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int count= strategyGroupDetailMasterMapper.deleteByPrimaryKey(ids);
		strategyGroupMasterMapper.updateStallCount(strategyGroupId);
		return count;
	}

	@Override
	public int addStall(ReqStrategyGroupDetail reqStrategyGroupDetail) {
		StrategyGroupDetail strategyGroupDetail = new StrategyGroupDetail();
		strategyGroupDetail = ObjectUtils.copyObject(reqStrategyGroupDetail, strategyGroupDetail);
		int count=strategyGroupDetailMasterMapper.insert(strategyGroupDetail);
		strategyGroupMasterMapper.updateStallCount(strategyGroupDetail.getStrategyGroupId());
		return count;
	}

	@Override
	public Long existsStall( Map<String, Object> map) {

		Map<String, Object> param=new HashMap<String, Object>();
		param.put("preId", map.get("prefectureId"));
		param.put("startName", map.get("stallName"));
		param.put("endName", map.get("stallName"));
		List<ResStall> findListByArea = stallClusterMapper.findListByArea(param);
		if(findListByArea!=null && findListByArea.size()>0) {
			return findListByArea.get(0).getId();
		}else {
			return 0L;
		}
	}

	@Override
	public List<ResStrategyGroup> findList(Map<String, Object> map) {
		return strategyGroupClusterMapper.findList(map);
	}

}
