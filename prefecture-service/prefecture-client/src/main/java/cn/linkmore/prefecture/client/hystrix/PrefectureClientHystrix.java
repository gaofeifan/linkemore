package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.PrefectureClient;
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
	public List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture) {
		log.info("prefecture service pres findPreListByLoc(ReqPrefecture reqPrefecture) hystrix");
		return new ArrayList<ResPrefecture>();
	}
	@Override
	public List<ResPre> prenames(List<Long> ids) {
		log.info("prefecture service pres prenames(List<Long> ids) hystrix");
		return new ArrayList<ResPre>();
	}
	@Override
	public List<ResPrefectureList> refreshFreeStall() {
		log.info("prefecture service pres refreshFreeStall() hystrix");
		return new ArrayList<ResPrefectureList>();
	}
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service pres list(ViewPageable pageable) hystrix");
		return null;
	}
	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service pres delete(List<Long> ids) hystrix");
		return 0;
	}
	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service pres check(reqCheck) hystrix");
		return false;
	}
	@Override
	public List<ResPreList> selectList() {
		log.info("prefecture service pres selectList() hystrix");
		return new ArrayList<ResPreList>();
	}
	@Override
	public int save(ReqPrefectureEntity prefecture) {
		log.info("prefecture service pres save() hystrix");
		return 0;
	}
	@Override
	public int update(ReqPrefectureEntity prefecture) {
		log.info("prefecture service pres update() hystrix");
		return 0;
	}
	@Override
	public List<Map<String, Object>> findListByCityId(Long cityId) {
		log.info("prefecture service pres findListByCityId() hystrix");
		return new ArrayList<Map<String, Object>>();
	}
	@Override
	public List<ResPreExcel> exportList(ReqPreExcel reqPreExcel) {
		log.info("prefecture service pres exportList() hystrix");
		return new ArrayList<ResPreExcel>();
	}
	@Override
	public List<ResPrefectureDetail> findList(Map<String, Object> param) {
		log.info("prefecture service pres findList() hystrix");
		return null;
	}
	@Override
	public List<ResPrefectureDetail> findAll() {
		log.info("prefecture service pres findAll() hystrix");
		return null;
	}
	@Override
	public ResPrefectureDetail checkName(String preName) {
		log.info("prefecture service pres checkName(preName) hystrix");
		return null;
	}
	
	

}
