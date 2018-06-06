package cn.linkmore.common.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.DistrictClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqDistrict;
import cn.linkmore.common.response.ResDistrict;

@Component
public class DistrictClientHystrix implements DistrictClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void delete(List<Long> ids) {
		log.info("common service district delete(List<Long> ids) hystrix");
	}

	@Override
	public void update(ReqDistrict district) {
		log.info("common service district update(ReqDistrict district) hystrix");
		
	}

	@Override
	public void save(ReqDistrict district) {
		log.info("common service district save(ReqDistrict district) hystrix");
		
	}

	@Override
	public Boolean check(ReqCheck object) {
		log.info("common service district check(ReqCheck object) hystrix");
		return null;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("common service district findPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public Tree findTree() {
		log.info("common service district findTree() hystrix");
		return null;
	}

	@Override
	public List<ResDistrict> findSelectListByCityId(Long cityId) {
		log.info("common service district findSelectListByCityId() hystrix");
		return new ArrayList<ResDistrict>();
	}
}
