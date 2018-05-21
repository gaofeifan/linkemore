package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.request.ReqCity;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
/**
 * 远程调用实现 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class PrefectureClientHystrix implements PrefectureClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ResPrefectureDetail findById(Long id) {
		log.info("prefecture service pres findById(Long id) hystrix");
		return new ResPrefectureDetail();
	}
	@Override
	public ResPrefectureStrategy findPreStrategy(Long preId) {
		log.info("prefecture service pres findPreStrategy(Long id) hystrix");
		return new ResPrefectureStrategy();
	}
	@Override
	public List<ResPrefectureList> findPreListByCityId(ReqCity reqCity) {
		log.info("prefecture service pres findPreListByCityId(ReqCity reqCity) hystrix");
		return new ArrayList<ResPrefectureList>();
	}
	@Override
	public List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture) {
		log.info("prefecture service pres findPreListByLoc(ReqPrefecture reqPrefecture) hystrix");
		return new ArrayList<ResPrefecture>();
	}
	@Override
	public List<ResPre> prenames(List<Long> ids) {
		log.info("prefecture service pres prenames(List<Long> ids) hystrix");
		return new ArrayList<ResPre>();
	}

}
