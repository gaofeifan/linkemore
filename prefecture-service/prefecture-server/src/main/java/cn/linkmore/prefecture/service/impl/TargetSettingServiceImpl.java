package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.TargetDayClusterMapper;
import cn.linkmore.prefecture.dao.cluster.TargetMounthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.TargetSettingClusterMapper;
import cn.linkmore.prefecture.dao.master.TargetSettingMasterMapper;
import cn.linkmore.prefecture.entity.TargetSetting;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqTargetSetting;
import cn.linkmore.prefecture.response.ResTargetSetting;
import cn.linkmore.prefecture.service.TargetSettingService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 - 车区目标
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class TargetSettingServiceImpl implements TargetSettingService {
	@Autowired
	private TargetSettingClusterMapper targetSettingClusterMapper;
	@Autowired
	private TargetSettingMasterMapper targetSettingMasterMapper;
	
	private Logger log = LoggerFactory.getLogger(getClass()); 
	
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
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty().replaceAll(" ", "")));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.targetSettingClusterMapper.count(param);
		List<ResTargetSetting> list = new ArrayList<ResTargetSetting>();
		if(count>0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.targetSettingClusterMapper.findPage(param);
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}
	
	@Override
	public int save(ReqTargetSetting record) { 
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setProperty("prefecture_id");
		reqCheck.setValue(record.getPrefectureId().toString());
		reqCheck.setId(new Date().getTime());
		int count = this.check(reqCheck);
		if(count>0){
			throw new DataException("数据已经存在");
		}
		record.setCreateTime(new Date());
		TargetSetting target = new TargetSetting();
		target = ObjectUtils.copyObject(record, target);
		return this.targetSettingMasterMapper.save(target);
	}
	
	@Override
	public int update(ReqTargetSetting record) {
		TargetSetting target = new TargetSetting();
		target = ObjectUtils.copyObject(record, target);
		return this.targetSettingMasterMapper.update(target);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.targetSettingMasterMapper.delete(ids); 
	} 
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.targetSettingClusterMapper.check(param); 
	}

	@Override
	public void dayTargetScheduled() {
		
	} 
	
	/*@Override
	public void dayTargetScheduled(){ 
		List<PreTargetSetting> ptsList = this.preTargetSettingMapper.findList(new HashMap<String,Object>());
		Map<Long,PreTargetSetting> map = new HashMap<Long,PreTargetSetting>(); 
		for(PreTargetSetting pts:ptsList){
			map.put(pts.getPrefectureId(), pts);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> param = new HashMap<String,Object>();
		Date today = new Date();
		Date tomorrow =  new Date(new Date().getTime()+1000L*60*60*24);
		param.put("startDay", sdf.format(today));
		param.put("endDay", sdf.format(tomorrow));
		List<PrefectureUserAmount> puas = this.preDayTargetMapper.findPrefectureUserAmount(param);
		Map<Long,Long> puaMap = new HashMap<Long,Long>();
		for(PrefectureUserAmount pua :puas){
			puaMap.put(pua.getPrefectureId(),pua.getUserCount());
		} 
		List<PrefectureOrderAmount> poas = this.preDayTargetMapper.findPrefectureOrderAmount(param);
		Map<Long,Long> poaMap = new HashMap<Long,Long>();
		for(PrefectureOrderAmount poa :poas){
			poaMap.put(poa.getPrefectureId(),poa.getOrderCount());
		}
		String mounth = new SimpleDateFormat("yyyy-MM").format(today); 
		List<PreMounthTarget> pmtList = this.preMounthTargetMapper.findMounthList(mounth); 
		Map<Long,PreMounthTarget> pmtMap = new HashMap<Long,PreMounthTarget>();
		List<Long> mounthIds = new ArrayList<Long>();
		for(PreMounthTarget pmt:pmtList){
			pmtMap.put(pmt.getPrefectureId(),pmt);
			mounthIds.add(pmt.getId());
		}
		List<PreMounthTarget> npmtList = new ArrayList<PreMounthTarget>();
		PreMounthTarget pmt = null;
		Map<Long,PreTargetSetting> ptsMap = new HashMap<Long,PreTargetSetting>(); 
		for(PreTargetSetting pts:ptsList){
			ptsMap.put(pts.getPrefectureId(), pts);
			pmt = pmtMap.get(pts.getPrefectureId());
			if(pmt==null){
				pmt = new PreMounthTarget();
				pmt.setCreateTime(today);
				pmt.setUpdateTime(today);
				pmt.setPrefectureId(pts.getPrefectureId());
				pmt.setTargetOrderCount(pts.getMounthOrderCount()); 
				pmt.setTargetUserCount(pts.getMounthUserCount());
				pmt.setCurrentOrderCount(0);
				pmt.setCurrentUserCount(0);
				pmt.setSettingId(pts.getId());
				pmt.setMounth(mounth);
				npmtList.add(pmt);
			}
		} 
		if(npmtList.size()>0){
			this.preMounthTargetMapper.batchSave(npmtList);
			for(PreMounthTarget p:npmtList){
				pmtMap.put(p.getPrefectureId(), p);
				pmtList.add(p);
				mounthIds.add(p.getId());
			}
		} 
		List<PreDayTarget> pdtList = this.preDayTargetMapper.findDayList(sdf.format(today));
		Long userCount = 0L;
		Long orderCount = 0L;
		PreTargetSetting pts = null;
		for(PreDayTarget pdt:pdtList){
			pts = map.get(pdt.getPrefectureId());
			userCount =puaMap.get(pts.getPrefectureId()) ;
			orderCount = poaMap.get(pts.getPrefectureId());
			pmt = pmtMap.get(pts.getPrefectureId()); 
			pdt.setCurrentOrderCount(orderCount==null?0:orderCount.intValue());
			pdt.setCurrentUserCount(userCount==null?0:userCount.intValue());
			pdt.setUpdateTime(new Date());
			pmt.setCurrentOrderCount(pmt.getCurrentOrderCount()+pdt.getCurrentOrderCount());
			pmt.setCurrentUserCount(pmt.getCurrentUserCount()+pdt.getCurrentUserCount());
			pmt.setUpdateTime(new Date());
			map.remove(pdt.getPrefectureId());
		} 
		if(pdtList!=null&&pdtList.size()>0){
			this.preDayTargetMapper.batchUpdate(pdtList);
		}
		
		PreDayTarget pdt = null; 
		List<PreDayTarget> npdtList = new ArrayList<PreDayTarget>();
		Set<Long> keys = map.keySet();
		for(Long key:keys){
			pts = map.get(key);
			userCount =puaMap.get(pts.getPrefectureId()) ;
			orderCount = poaMap.get(pts.getPrefectureId());
			pmt = pmtMap.get(pts.getPrefectureId()); 
			pdt = new PreDayTarget();
			pdt.setCreateTime(today);
			pdt.setUpdateTime(new Date());
			pdt.setDay(sdf.format(today));
			pdt.setCurrentOrderCount(orderCount==null?0:orderCount.intValue());
			pdt.setCurrentUserCount(userCount==null?0:userCount.intValue());
			pdt.setTargetOrderCount(pts.getDayOrderCount());
			pdt.setTargetUserCount(pts.getDayUserCount());
			pdt.setSettingId(pts.getId());
			pdt.setPrefectureId(pts.getPrefectureId());
			pdt.setMounthId(pmt.getId()); 
			npdtList.add(pdt);
		} 
		if(npdtList!=null&&npdtList.size()>0){
			this.preDayTargetMapper.batchSave(npdtList);
		} 
		
		List<PrefectureMounthAmount> pfas = this.preDayTargetMapper.findMounthAmount(mounthIds);
		Map<Long,PrefectureMounthAmount> pfaMap = new HashMap<Long,PrefectureMounthAmount>();
		for(PrefectureMounthAmount pfa:pfas){
			pfaMap.put(pfa.getMounthId(), pfa);
		}
		PrefectureMounthAmount pfa = null;
		for(PreMounthTarget mt:pmtList){
			pfa = pfaMap.get(mt.getId());
			if(pfa!=null){
				mt.setCurrentOrderCount(pfa.getOrderCount().intValue());
				mt.setCurrentUserCount(pfa.getUserCount().intValue());
				mt.setUpdateTime(new Date());
			}
		}
		if(pmtList!=null&&pmtList.size()>0){
			this.preMounthTargetMapper.batchUpdate(pmtList);
		} 
	}*/
}
