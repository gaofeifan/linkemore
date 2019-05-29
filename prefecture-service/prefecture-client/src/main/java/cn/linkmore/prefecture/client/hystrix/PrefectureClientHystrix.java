package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
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
	public List<ResPre> prenames(List<Long> ids) {
		log.info("prefecture service pres prenames(ids) hystrix");
		return null;
	}
	
	@Override
	public List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture) {
		log.info("prefecture service List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture) hystrix");
		return null;
	}

	@Override
	public List<ResPrefectureList> refreshFreeStall() {
		log.info("prefecture service List<ResPrefectureList> refreshFreeStall() hystrix");
		return null;
	}

	@Override
	public ResPrefectureDetail findById(Long id) {
		log.info("prefecture service pres findById(id) hystrix");
		return null;
	}

	@Override
	public List<ResPre> findPreByIds(Map<String, Object> map) {
		log.info("List<ResPre> findPreByIds(Map<String, Object> map) hystrix");
		return null;
	}

	@Override
	public Long findByGroupId(Long stallId, Long preId) {
		log.info("Long findByGroupId(Long stallId, Long preId) hystrix");
		return 0L;
	}

	@Override
	public String nearFreeStallLockSn(Long stallId, Long preId) {
		log.info("String nearFreeStallLockSn(Long stallId, Long preId) hystrix");
		return null;
	}

	@Override
	public List<String> getFloor(Long preId) {
		log.info("List<String> getFloor(Long preId)  hystrix");
		return null;
	}
	
	
	

}
