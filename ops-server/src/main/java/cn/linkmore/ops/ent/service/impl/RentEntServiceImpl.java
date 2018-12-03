package cn.linkmore.ops.ent.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.enterprise.request.ReqRentEntStall;
import cn.linkmore.ops.ent.service.RentEntService;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.prefecture.client.OpsRentEntClient;
import cn.linkmore.prefecture.client.PrefectrueClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.util.DateUtils;
@Service
public class RentEntServiceImpl implements RentEntService {

	@Resource
	private PrefectureClient prefectrueClient;
	@Resource
	private StallClient stallClient;
	@Resource
	private OpsRentEntClient entClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		List<ViewFilter> list = pageable.getFilters();
		ViewFilter vf = new ViewFilter();
		vf.setProperty("createEntId");
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		Long id = person.getEntId();
		vf.setValue(id);
		list.add(vf);
		return this.entClient.list(pageable);
	}

	@Override
	public void save(cn.linkmore.ops.ent.request.ReqRentEnt ent) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		Long id = person.getEntId();
		ReqRentEnt reqent = new ReqRentEnt();
		reqent.setCompanyName(ent.getCompanyName());
		reqent.setEndTime(DateUtils.convert(ent.getEndTime(), null));
		reqent.setStartTime(DateUtils.convert(ent.getStartTime(), null));
		reqent.setCreateEntId(id);
		List<ReqRentEntStall> stalls = new ArrayList<>();
		ReqRentEntStall reqRentEntStall;
		List<Long> list = ent.getStallIds();
		String names = ent.getStallNames();
		List<Long> preIds = ent.getPreIds();
		String[] stallNames = names.split(",");
		Map<String, Object> map = new HashMap<>();
		map.put("preIds", preIds);
		List<ResPre> pres = this.prefectrueClient.findPreByIds(map );
		if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				reqRentEntStall = new ReqRentEntStall();
				reqRentEntStall.setStallId(list.get(i));
				reqRentEntStall.setStallName(stallNames[i]);
				reqRentEntStall.setPreId(preIds.get(i));
				for (ResPre resPre : pres) {
					if(preIds.get(i).equals(resPre.getId())) {
						reqRentEntStall.setPreName(resPre.getName());
						break;
					}
				}
				stalls.add(reqRentEntStall);
			}
		}
		reqent.setStalls(stalls );
		this.entClient.save(reqent);
	}

	@Override
	public void update(ReqRentEnt ent) {
		this.entClient.update(ent);
	}

	@Override
	public void delete(List<Long> ids) {
		this.entClient.delete(ids);
	}

	@Override
	public List<ResStall> stallList(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		Map<String, Object> param = new HashMap<>();
		param.put("preId", 2);
		List<ResStall> list = this.stallClient.findStallList(param );
		return list;
	}

	@Override
	public ViewPage stallListCompany(ViewPageable pageable) {
		return this.entClient.stallListCompany(pageable);
	}

	@Override
	public List<Tree> tree() {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		return this.entClient.tree(person.getEntId());
	}

	
	
	
	
}
