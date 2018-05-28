package cn.linkmore.common.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.client.DistrictClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.request.ReqDistrict;
import cn.linkmore.common.response.ResCity;

@Component
public class DistrictClientHystrix implements DistrictClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ReqDistrict district) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(ReqDistrict district) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean check(ReqCheck object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tree findTree() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
