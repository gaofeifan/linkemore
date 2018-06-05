package cn.linkmore.ops.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.ops.request.ReqCity;
import cn.linkmore.ops.service.CityService;
import cn.linkmore.util.ObjectUtils;

@Service
public class CityServiceImpl implements CityService {

	@Resource
	private CityClient cityClient;
	
	@Override
	public void save(ReqCity record) {
		cn.linkmore.common.request.ReqCity city = ObjectUtils.copyObject(record, new cn.linkmore.common.request.ReqCity(),new String[]{"adcode","cityName"},new String[]{"code","name"});
		this.cityClient.save(city);
	}

	@Override
	public void update(ReqCity record) {
		cn.linkmore.common.request.ReqCity city = ObjectUtils.copyObject(record, new cn.linkmore.common.request.ReqCity(),new String[]{"adcode","cityName"},new String[]{"code","name"});
		this.cityClient.update(city);
		
	}

	@Override
	public void delete(List<Long> ids) {
		this.cityClient.deleteIds(ids);
	}

	@Override
	public Boolean check(cn.linkmore.ops.request.ReqCheck check ) {
		ReqCheck object = ObjectUtils.copyObject(check, new ReqCheck());
		return this.cityClient.check(object);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.cityClient.findPage(pageable);
	}
	

}
