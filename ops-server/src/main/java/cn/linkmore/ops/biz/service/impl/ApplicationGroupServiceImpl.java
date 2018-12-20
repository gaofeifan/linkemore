package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.ops.biz.response.ResPrefecture;
import cn.linkmore.ops.biz.service.ApplicationGroupService;
import cn.linkmore.prefecture.client.ApplicationGroupClient;
import cn.linkmore.prefecture.client.OpsPrefectureClient;
import cn.linkmore.prefecture.request.ReqApplicationGroup;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResPrefectureGroup;
import cn.linkmore.security.request.ReqPerson;
@Service
public class ApplicationGroupServiceImpl implements ApplicationGroupService{

	@Resource
	private ApplicationGroupClient applicationGroupClient;
	@Resource
	private OpsPrefectureClient prefectureClient;
	@Resource
	private CityClient cityClient;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage viewPage = this.applicationGroupClient.list(pageable);
		return viewPage;
	}

	@Override
	public void start(List<Long> ids) {
		this.applicationGroupClient.start(ids);
	}

	@Override
	public void add(ReqApplicationGroup requestBean, ReqPerson person) {
		requestBean.setOperatorId(person.getId());
		this.applicationGroupClient.add(requestBean);
	}

	@Override
	public void down(List<Long> ids) {
		this.applicationGroupClient.down(ids);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck check = new ReqCheck();
		check.setId(id);
		check.setValue(value);
		check.setProperty(property);
		Boolean che = this.applicationGroupClient.check(check);
		return che;
	}

	@Override
	public List<ResPrefectureGroup> getPreGroupSelect() {
		List<cn.linkmore.prefecture.response.ResPrefecture> preList = this.prefectureClient.findPreList();
		return null;
	}

	@Override
	public List<ResCity> getCityList() {
		List<ResCity> list = this.cityClient.findSelectList();
		return list;
	}

	@Override
	public List<ResPrefecture> getPreList(Long cityId) {
		List<Map<String,Object>> list = this.prefectureClient.findListByCityId(cityId);
		return null;
	}

	
}
