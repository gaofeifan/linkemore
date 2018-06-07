package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.PrefectureGroupClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureGroup;
/**
 * 远程调用实现 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class PrefectureGroupClientHystrix implements PrefectureGroupClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("pre group hystrix check");
		return false;
	}

	@Override
	public int save(ReqPrefectureGroup preGroup) {
		log.info("pre group hystrix save");
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("pre group hystrix list");
		return new ViewPage();
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("pre group hystrix delete");
		return 0;
	}

	@Override
	public int start(List<Long> ids) {
		log.info("pre group hystrix start");
		return 0;
	}

	@Override
	public int down(List<Long> ids) {
		log.info("pre group hystrix down");
		return 0;
	}
	
}
