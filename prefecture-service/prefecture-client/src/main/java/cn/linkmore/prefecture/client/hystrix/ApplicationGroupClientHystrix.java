package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.ApplicationGroupClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.request.ReqApplicationGroup;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
/**
 * 远程调用实现 - 应用程序
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class ApplicationGroupClientHystrix implements ApplicationGroupClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service pres list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public void add(ReqApplicationGroup requestBean) {
		log.info("prefecture service pres add(ReqApplicationGroup requestBean hystrix");
	}

	@Override
	public void start(List<Long> ids) {
		log.info("prefecture service void delete(List<Long> ids) hystrix");
	}

	@Override
	public void down(List<Long> ids) {
		log.info("prefecture service void down(List<Long> ids) hystrix");
		
	}

	@Override
	public Boolean check(ReqCheck check) {
		log.info("prefecture serviceBoolean check(ReqCheck check)  hystrix");
		return null;
	}
	

}

