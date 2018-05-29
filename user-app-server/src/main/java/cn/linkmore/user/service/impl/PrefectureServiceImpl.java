package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.request.ReqPrefecture;
import cn.linkmore.user.response.ResPreCity;
import cn.linkmore.user.response.ResPrefecture;
import cn.linkmore.user.response.ResPrefectureList;
import cn.linkmore.user.response.ResPrefectureStrategy;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.PrefectureService;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现 - 车区
 * @author liwenlong
 * @version 2.0
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {
	@Autowired
	private PrefectureClient preClient;
	@Resource
	private RedisService redisService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public List<ResPreCity> list(ReqPrefecture rp, HttpServletRequest request) {
		cn.linkmore.prefecture.request.ReqPrefecture reqPre = new cn.linkmore.prefecture.request.ReqPrefecture();
		reqPre.setLatitude(rp.getLatitude());
		reqPre.setLongitude(rp.getLongitude());
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER + key);
		if(ru!=null) {
			reqPre.setUserId(ru.getId());
		}
		List<cn.linkmore.prefecture.response.ResPrefecture> preList = this.preClient.findPreListByLoc(reqPre);
		log.info("preList total size,{}",preList.size());
		List<ResPrefecture> resPrefectureList = new ArrayList<ResPrefecture>();
		ResPrefecture resPrefecture = null;
		for(int i=0; i< preList.size();i++) {
			resPrefecture = ObjectUtils.copyObject(preList.get(i), new ResPrefecture());
			resPrefectureList.add(resPrefecture);
		}
		Map<Long, List<ResPrefecture>> map = resPrefectureList.stream().collect(Collectors.groupingBy(ResPrefecture::getCityId));
		
		List<ResPreCity> resPreCityList = new ArrayList<ResPreCity>();
		ResPreCity resPreCity = null;
		for(Long cityId : map.keySet()){
			resPreCity = new ResPreCity();
			resPreCity.setCityId(cityId);
			List<ResPrefecture> prefecturelist = map.get(cityId);
			resPreCity.setPrefectures(prefecturelist);
			resPreCityList.add(resPreCity);
			log.info("cityId:{},city pre list size:{}",cityId,prefecturelist.size());
		}
		return resPreCityList;
	}
	
	@Override
	public ResPrefectureStrategy findStrategyById(Long preId, HttpServletRequest request) {
		cn.linkmore.prefecture.response.ResPrefectureStrategy strategy = this.preClient.findPreStrategy(preId);
		if(strategy != null) {
			return ObjectUtils.copyObject(strategy, new ResPrefectureStrategy());
		}
		return null;
	}

	@Override
	public List<ResPrefectureList> refreshFreeStall() {
		List<ResPrefectureList> resPrefectureList = new ArrayList<ResPrefectureList>();
		ResPrefectureList resPrefecture = null;
		List<cn.linkmore.prefecture.response.ResPrefectureList> resPreList = this.preClient.refreshFreeStall();
		if(CollectionUtils.isNotEmpty(resPreList)) {
			for(cn.linkmore.prefecture.response.ResPrefectureList res : resPreList) {
				resPrefecture = new ResPrefectureList();
				resPrefecture.setId(res.getId());
				resPrefecture.setLeisureStall(res.getLeisureStall());
				resPrefectureList.add(resPrefecture);
			}
		}
		return resPrefectureList;
	}

}
