package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallAssignClient;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallAssignClientHystrix implements StallAssignClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall_assign list() hystrix");
		return null;
	}
	
	@Override
	public List<ResPreList> findPrefectureList() {
		log.info("prefecture service order_operate findPrefectureList() hystrix");
		return new ArrayList<ResPreList>();
	}

	@Override
	public List<ResStall> stallList(Map<String, Object> param) {
		log.info("prefecture service order_operate stallList() hystrix");
		return new ArrayList<ResStall>();
	}
}
