package cn.linkmore.prefecture.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.PrefectureLockTimeClusterMapper;
import cn.linkmore.prefecture.dao.cluster.PrefectureStrategyClusterMapper;
import cn.linkmore.prefecture.dao.cluster.PrefectureStrategyGroupClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyDateDetailClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyTimeDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureLockTimeMasterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureStrategyGroupMasterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureStrategyMasterMapper;
import cn.linkmore.prefecture.entity.PrefectureLockTime;
import cn.linkmore.prefecture.entity.PrefectureStrategy;
import cn.linkmore.prefecture.entity.PrefectureStrategyGroup;
import cn.linkmore.prefecture.entity.StrategyDateDetail;
import cn.linkmore.prefecture.entity.StrategyTimeDetail;
import cn.linkmore.prefecture.request.ReqPrefectureLockTime;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.request.ReqPrefectureStrategyGroup;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureLockTime;
import cn.linkmore.prefecture.response.ResPrefectureStrategyGroup;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.service.PrefectureStrategyService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class PrefectureStrategyServiceImpl implements PrefectureStrategyService {

	@Autowired
	PrefectureStrategyMasterMapper prefectureStrategyMasterMapper;
	@Autowired
	PrefectureStrategyClusterMapper prefectureStrategyClusterMapper;
	
	@Autowired
	PrefectureStrategyGroupMasterMapper prefectureStrategyGroupMasterMapper;
	@Autowired
	PrefectureStrategyGroupClusterMapper prefectureStrategyGroupClusterMapper;
	
	@Autowired
	PrefectureLockTimeMasterMapper prefectureLockTimeMasterMapper;
	@Autowired
	PrefectureLockTimeClusterMapper prefectureLockTimeClusterMapper;
	
	@Autowired
	StrategyTimeDetailClusterMapper strategyTimeDetailClusterMapper;
	
	@Autowired
	StrategyDateDetailClusterMapper strategyDateDetailClusterMapper;
	
	@Autowired
	PrefectureClusterMapper prefectureClusterMapper;
	
	ObjectMapper mapper = new ObjectMapper();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	@Override
	public int save(ReqPrefectureStrategy reqPrefectureStrategy) {
		PrefectureStrategy prefectureStrategy = new PrefectureStrategy();
		prefectureStrategy = ObjectUtils.copyObject(reqPrefectureStrategy, prefectureStrategy);
		
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("createUserId", reqPrefectureStrategy.getCreateUserId());
		List<ResPre> preList = prefectureClusterMapper.findTreeList(param);
		if (preList != null && preList.size()>0) {
			prefectureStrategy.setPrefectureId(preList.get(0).getId());
		}
		
		int count=prefectureStrategyMasterMapper.insert(prefectureStrategy);
		if(reqPrefectureStrategy.getLockTime()!=null) {
			for (ReqPrefectureLockTime reqPrefectureLockTime:reqPrefectureStrategy.getLockTime()) {
				PrefectureLockTime prefectureLockTime = new PrefectureLockTime();
				prefectureLockTime = ObjectUtils.copyObject(reqPrefectureLockTime, prefectureLockTime);
				prefectureLockTime.setPrefectureStrategyId(prefectureStrategy.getId());
				prefectureLockTimeMasterMapper.insert(prefectureLockTime);
			}
		}
		if(reqPrefectureStrategy.getStrategyGroup()!=null) {
			for (ReqPrefectureStrategyGroup reqPrefectureStrategyGroup:reqPrefectureStrategy.getStrategyGroup()) {
				PrefectureStrategyGroup prefectureStrategyGroup = new PrefectureStrategyGroup();
				prefectureStrategyGroup = ObjectUtils.copyObject(reqPrefectureStrategyGroup, prefectureStrategyGroup);
				prefectureStrategyGroup.setPrefectureStrategyId(prefectureStrategy.getId());
				prefectureStrategyGroupMasterMapper.insert(prefectureStrategyGroup);
			}
		}
		return count;
	}

	@Override
	public int update(ReqPrefectureStrategy reqPrefectureStrategy) {
		PrefectureStrategy prefectureStrategy = new PrefectureStrategy();
		prefectureStrategy = ObjectUtils.copyObject(reqPrefectureStrategy, prefectureStrategy);
		int count=prefectureStrategyMasterMapper.updateByPrimaryKey(prefectureStrategy);
		List<Long> ids=new ArrayList<Long>();
		ids.add(prefectureStrategy.getId());
		prefectureLockTimeMasterMapper.delete(ids);
		if(reqPrefectureStrategy.getLockTime()!=null) {
			for (ReqPrefectureLockTime reqPrefectureLockTime:reqPrefectureStrategy.getLockTime()) {
				PrefectureLockTime prefectureLockTime = new PrefectureLockTime();
				prefectureLockTime = ObjectUtils.copyObject(reqPrefectureLockTime, prefectureLockTime);
				prefectureLockTime.setPrefectureStrategyId(prefectureStrategy.getId());
				prefectureLockTimeMasterMapper.insert(prefectureLockTime);
			}
		}
		prefectureStrategyGroupMasterMapper.delete(ids);
		if(reqPrefectureStrategy.getStrategyGroup()!=null) {
			for (ReqPrefectureStrategyGroup reqPrefectureStrategyGroup:reqPrefectureStrategy.getStrategyGroup()) {
				PrefectureStrategyGroup prefectureStrategyGroup = new PrefectureStrategyGroup();
				prefectureStrategyGroup = ObjectUtils.copyObject(reqPrefectureStrategyGroup, prefectureStrategyGroup);
				prefectureStrategyGroup.setPrefectureStrategyId(prefectureStrategy.getId());
				prefectureStrategyGroupMasterMapper.insert(prefectureStrategyGroup);
			}
		}
		
		return count;
	}

	@Override
	public int delete(List<Long> ids) {
		prefectureStrategyGroupMasterMapper.delete(ids);
		prefectureLockTimeMasterMapper.delete(ids);
		return prefectureStrategyMasterMapper.delete(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return prefectureStrategyMasterMapper.updateStatus(map);
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
		Integer count = this.prefectureStrategyClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<PrefectureStrategy> list = this.prefectureStrategyClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);

	}

	@Override
	public ResPrefectureStrategyNew selectByPrimaryKey(Long id) {
		
		PrefectureStrategy prefectureStrategy = prefectureStrategyClusterMapper.selectByPrimaryKey(id);
		ResPrefectureStrategyNew resPrefectureStrategy =new ResPrefectureStrategyNew();
		resPrefectureStrategy = ObjectUtils.copyObject(prefectureStrategy, resPrefectureStrategy);
		
		List<PrefectureStrategyGroup> listPrefectureStrategyGroup = prefectureStrategyGroupClusterMapper.findList(id);
		List<ResPrefectureStrategyGroup> listResPrefectureStrategyGroup = new ArrayList<ResPrefectureStrategyGroup>();
		for(PrefectureStrategyGroup prefectureStrategyGroup:listPrefectureStrategyGroup) {
			ResPrefectureStrategyGroup resPrefectureStrategyGroup =new ResPrefectureStrategyGroup();
			resPrefectureStrategyGroup = ObjectUtils.copyObject(prefectureStrategyGroup, resPrefectureStrategyGroup);
			listResPrefectureStrategyGroup.add(resPrefectureStrategyGroup);
		}
		resPrefectureStrategy.setStrategyGroup(listResPrefectureStrategyGroup);

		List<PrefectureLockTime> listPrefectureLockTime = prefectureLockTimeClusterMapper.findList(id);
		List<ResPrefectureLockTime> listResPrefectureLockTime=new ArrayList<ResPrefectureLockTime>();
		for(PrefectureLockTime prefectureLockTime:listPrefectureLockTime) {
			ResPrefectureLockTime resPrefectureLockTime =new ResPrefectureLockTime();
			resPrefectureLockTime = ObjectUtils.copyObject(prefectureLockTime, resPrefectureLockTime);
			listResPrefectureLockTime.add(resPrefectureLockTime);
		}
		resPrefectureStrategy.setLockTime(listResPrefectureLockTime);
		
		return resPrefectureStrategy;
	}

	@Override
	public int validateTime(Map<String, String> map) {
		String jsonLockTime=map.get("p");
		//[{"lockStatus":"1","strategyTimeId":"14"},{"lockStatus":"2","strategyTimeId":"15"}]
	    JSONArray myJsonArray = JSONArray.parseArray(jsonLockTime);
	    List<Long> ids=new ArrayList<Long>();
	    for(int i=0 ; i < myJsonArray.size() ;i++){
	    	JSONObject myObject = myJsonArray.getJSONObject(i);
	    	ids.add(myObject.getLong("strategyTimeId"));
	    }
	    List<StrategyTimeDetail> listStrategyTimeDetail = strategyTimeDetailClusterMapper.findListByIds(ids);
	    int len=listStrategyTimeDetail.size();
	    
	    for (int i=0;i<len;i++) {
	    	for (int j=0;j<len;j++) {
	    		if(i!=j) {
   	    			
	    			if(  (myTimeDiff(listStrategyTimeDetail.get(i).getBeginTime(),listStrategyTimeDetail.get(j).getBeginTime())<=0 
							&& myTimeDiff(listStrategyTimeDetail.get(i).getBeginTime(),listStrategyTimeDetail.get(j).getEndTime() )>=0 )
						|| 	(myTimeDiff(listStrategyTimeDetail.get(i).getEndTime(),listStrategyTimeDetail.get(j).getEndTime())<=0 
						   && myTimeDiff(listStrategyTimeDetail.get(i).getEndTime(),listStrategyTimeDetail.get(j).getEndTime())>=0 )
					){
	    				//System.out.println("时刻出现交叉!");
						return 1;
					}
	    			
	    		}
	    	}
	    }
		return 0;
	}

	private long myTimeDiff(String beginTime,String endTime) {
	    LocalDateTime d_beginTime = LocalDateTime.parse("2018-01-01 "+beginTime, dtf);
	    LocalDateTime d_endTime = LocalDateTime.parse("2018-01-01 "+endTime, dtf);
	   // System.out.printf("beginTime:%s,endTime:%s,diff=%s\n","2018-01-01 "+beginTime,"2018-01-01 "+endTime,Duration.between(beginDate, endDate).getSeconds());
		return Duration.between(d_beginTime, d_endTime).getSeconds();
	}
	
	private long myDateDiff(String beginDate,String endDate) {
		LocalDate d_beginDate = LocalDate.parse(beginDate);
		LocalDate d_endDate = LocalDate.parse(endDate);
		//System.out.printf("beginDate:%s,endDate:%s,diff=%s\n","2018-01-01 "+beginDate,"2018-01-01 "+endDate,ChronoUnit.DAYS.between(d_beginDate, d_endDate));

		return ChronoUnit.DAYS.between(d_beginDate, d_endDate);
	}
	
	@Override
	public int validateDate(Map<String, String> map) {
		String strategyGroupArray=map.get("p");
		int datetype=Integer.parseInt(map.get("datetype"));

		//System.out.println(strategyGroupArray);
		//System.out.println(222222);
		JSONArray myJsonArray = JSONArray.parseArray(strategyGroupArray);
		List<Long> ids=new ArrayList<Long>();
		for(int i=0 ; i < myJsonArray.size() ;i++){
			JSONObject myObject = myJsonArray.getJSONObject(i);
			ids.add(myObject.getLong("strategyDateId"));
		}
		List<StrategyDateDetail> listStrategyDateDetail = strategyDateDetailClusterMapper.findListByIds(ids);
		int len=listStrategyDateDetail.size();
		
		if (datetype==1) {
			//按日期
			 for (int i=0;i<len;i++) {
			    	for (int j=0;j<len;j++) {
			    		if(i!=j){
							if(  (myDateDiff(listStrategyDateDetail.get(i).getBeginDate(),listStrategyDateDetail.get(j).getBeginDate())<=0 
									&& myDateDiff(listStrategyDateDetail.get(i).getBeginDate(),listStrategyDateDetail.get(j).getEndDate() )>=0 )
								|| 	(myDateDiff(listStrategyDateDetail.get(i).getEndDate(),listStrategyDateDetail.get(j).getBeginDate())<=0 
								   && myDateDiff(listStrategyDateDetail.get(i).getEndDate(),listStrategyDateDetail.get(j).getEndDate())>=0 )
							){
								//System.out.println("日期出现交叉!");
								return 1;
							}
						}
			    	}
			 }
		}
		return 0;
	}
	
}
