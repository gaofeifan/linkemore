package cn.linkmore.ops.ent.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.enterprise.request.ReqRentEntStall;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.ent.service.RentEntService;
import cn.linkmore.prefecture.client.OpsPrefectureClient;
import cn.linkmore.prefecture.client.OpsRentEntClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.DateUtils;
@Service
public class RentEntServiceImpl implements RentEntService {
	
	@Resource
	private PrefectureClient prefectrueClient;
	@Resource
	private StallClient stallClient;
	
	@Resource
	private OpsPrefectureClient prefectureClient;
	
	@Resource
	private OpsRentEntClient rentEntClient;
	
	@Autowired
	private EnterpriseService enterService;
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("property", "id");
		map.put("value", person.getId());
		ResEnterprise enter=enterService.find(map);
		if(enter != null) {
			List<ViewFilter> list = pageable.getFilters();
			ViewFilter vf = new ViewFilter();
			vf.setProperty("createUserId");
			vf.setValue(person.getId());
			list.add(vf);
			//pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"createUserId",getPerson().getId()));
		}
		/*
		
		List<ViewFilter> list = pageable.getFilters();
		ViewFilter vf = new ViewFilter();
		vf.setProperty("createUserId");
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		Long id = person.getId();
		vf.setValue(id);
		list.add(vf);
		*/
		return this.rentEntClient.list(pageable);
	}

	@Override
	public void save(ReqRentEnt ent) {
		ent.setEndTime(DateUtils.convert(ent.getEndTimeStr(), "yyyy-MM-dd"));
		ent.setStartTime(DateUtils.convert(ent.getStartTimeStr(), "yyyy-MM-dd"));
		List<ReqRentEntStall> stalls = new ArrayList<>();
		ReqRentEntStall reqRentEntStall;
		List<Long> list = ent.getStallIds();
		String names = ent.getStallNames();
		String[] stallNames = names.split(",");
		ResPrefectureDetail preDetail = this.prefectrueClient.findById(ent.getPreId());
		if(CollectionUtils.isNotEmpty(list) && preDetail != null) {
			for (int i = 0; i < list.size(); i++) {
				reqRentEntStall = new ReqRentEntStall();
				reqRentEntStall.setStallId(list.get(i));
				reqRentEntStall.setStallName(stallNames[i]);
				reqRentEntStall.setPreId(preDetail.getId());
				reqRentEntStall.setPreName(preDetail.getName());
				reqRentEntStall.setCreateTime(ent.getCreateTime());
				reqRentEntStall.setCreateUserId(ent.getCreateUserId());
				reqRentEntStall.setCreateUserName(ent.getCreateUserName());
				reqRentEntStall.setUpdateTime(ent.getUpdateTime());
				reqRentEntStall.setUpdateUserId(ent.getUpdateUserId());
				reqRentEntStall.setUpdateUserName(ent.getUpdateUserName());
				stalls.add(reqRentEntStall);
			}
			ent.setPreId(preDetail.getId());
			ent.setPreName(preDetail.getName());
		}
		ent.setStalls(stalls);
		this.rentEntClient.save(ent);
	}

	@Override
	public void update(ReqRentEnt ent) {
		ent.setEndTime(DateUtils.convert(ent.getEndTimeStr(), "yyyy-MM-dd"));
		ent.setStartTime(DateUtils.convert(ent.getStartTimeStr(), "yyyy-MM-dd"));
		this.rentEntClient.update(ent);
	}

	@Override
	public void delete(List<Long> ids) {
		this.rentEntClient.delete(ids);
	}

	@Override
	public List<ResStall> stallList(HttpServletRequest request) {
		List<ResStall> list = new ArrayList<ResStall>();
		List<ResStall> notUsedList = new ArrayList<ResStall>();
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		Map<String, Object> param = new HashMap<>();
		param.put("createUserId", person.getId());
		List<ResPreList> preList = prefectureClient.findSelectListByUser(param);
		if(CollectionUtils.isNotEmpty(preList)) {
			param.put("preId", preList.get(0).getId());
			param.put("type", 2);
			list = this.stallClient.findStallList(param);
			List<Long> stallIds = rentEntClient.occuyStallList(param);
			if(CollectionUtils.isNotEmpty(list)) {
				for(ResStall stall: list) {
					if(!stallIds.contains(stall.getId())) {
						notUsedList.add(stall);
					}
				}
			}
			
			/*param.put("preId", preList.get(0).getId());
			param.put("type", 2);
			List<Long> stallIds = rentEntClient.occuyStallList(param);
			log.info("------occupy-----stallIds = {}", JSON.toJSON(stallIds));
			if(CollectionUtils.isNotEmpty(stallIds)) {
				param.put("list", stallIds);
			}
			log.info("-----------params = {}", JSON.toJSON(param));
			list = this.stallClient.findPreStallList(param);
			log.info("------un occupy-----stall-list = {}", JSON.toJSON(list));*/
		}
		return notUsedList;
	}

	@Override
	public ViewPage stallListCompany(ViewPageable pageable) {
		return this.rentEntClient.stallListCompany(pageable);
	}

	@Override
	public List<Tree> tree() {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		return this.rentEntClient.tree(person.getEntId());
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return rentEntClient.updateStatus(map);
	}

	@Override
	public void deleteStall(List<Long> ids) {
		this.rentEntClient.deleteStall(ids);
	}

	@Override
	public void saveStall(ReqRentEnt ent) {
		List<ReqRentEntStall> stalls = new ArrayList<>();
		ReqRentEntStall reqRentEntStall;
		List<Long> list = ent.getStallIds();
		String names = ent.getStallNames();
		String[] stallNames = names.split(",");
		ResPrefectureDetail preDetail = this.prefectrueClient.findById(ent.getPreId());
		if(CollectionUtils.isNotEmpty(list) && preDetail != null) {
			for (int i = 0; i < list.size(); i++) {
				reqRentEntStall = new ReqRentEntStall();
				reqRentEntStall.setRentComId(ent.getId());
				reqRentEntStall.setStallId(list.get(i));
				reqRentEntStall.setStallName(stallNames[i]);
				reqRentEntStall.setPreId(preDetail.getId());
				reqRentEntStall.setPreName(preDetail.getName());
				reqRentEntStall.setCreateTime(ent.getCreateTime());
				reqRentEntStall.setCreateUserId(ent.getCreateUserId());
				reqRentEntStall.setCreateUserName(ent.getCreateUserName());
				reqRentEntStall.setUpdateTime(ent.getUpdateTime());
				reqRentEntStall.setUpdateUserId(ent.getUpdateUserId());
				reqRentEntStall.setUpdateUserName(ent.getUpdateUserName());
				stalls.add(reqRentEntStall);
			}
		}
		ent.setStalls(stalls);
		this.rentEntClient.saveStall(ent);
	}
}
