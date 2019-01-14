package cn.linkmore.common.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseVersionGroupClient;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.common.response.ResBaseAppVersionGroup;

@Component
public class BaseVersionGroupClientHystrix implements BaseVersionGroupClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int insert(ReqBaseAppVersionGroup record) {
		log.info("common service VersionGroup int insert(ReqBaseAppVersionGroup record) hystrix");
		return 0;
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		log.info("common service VersionGroup int deleteByIds(List<Long> ids) hystrix");
		return 0;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("common service VersionGroup ViewPage findPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public List<ResBaseAppVersionGroup> findList(Map<String, Object> param) {
		log.info("common service VersionGroup List<ResBaseAppVersionGroup> findList(Map<String, Object> param) hystrix");
		return null;
	}

}
