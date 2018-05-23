package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
		Map<Long, List<cn.linkmore.prefecture.response.ResPrefecture>> map = preList.stream().collect(Collectors.groupingBy(cn.linkmore.prefecture.response.ResPrefecture::getCityId));

		List<ResPreCity> resPreCityList = new ArrayList<ResPreCity>();
		ResPreCity resPreCity = null;
		ResPrefecture resPrefecture = null;
		List<ResPrefecture> resPrefectureList = null;
		for(Long cityId : map.keySet()){
			resPreCity = new ResPreCity();
			resPrefectureList = new ArrayList<ResPrefecture>();
			List<cn.linkmore.prefecture.response.ResPrefecture> resList = map.get(cityId);
			for(int i=0; i< resList.size();i++) {
				resPrefecture = ObjectUtils.copyObject(preList.get(i), new ResPrefecture());
				resPrefectureList.add(resPrefecture);
			}
			resPreCity.setCityId(cityId);
			resPreCity.setPrefectures(resPrefectureList);
			resPreCityList.add(resPreCity);
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
	public List<ResPrefectureList> findPreListByCityId(Long cityId, HttpServletRequest request) {
		cn.linkmore.prefecture.request.ReqCity reqCity = new cn.linkmore.prefecture.request.ReqCity();
		reqCity.setCityId(cityId);
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER + key);
		if(ru!=null) {
			reqCity.setUserId(ru.getId());
		}
		List<cn.linkmore.prefecture.response.ResPrefectureList> preList = this.preClient.findPreListByCityId(reqCity);
		List<ResPrefectureList> resPrefectureList = new ArrayList<ResPrefectureList>();
		ResPrefectureList resPrefecture = null;
		for(int i=0;i<preList.size();i++) {
			resPrefecture = ObjectUtils.copyObject(preList.get(i), new ResPrefectureList());
			resPrefectureList.add(resPrefecture);
		}
		return resPrefectureList;
	}

	@Override
	public Integer findFreeStallCount(Long preId) {
		return this.preClient.findFreeStallCount(preId);
	}

}
