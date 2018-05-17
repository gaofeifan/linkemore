package cn.linkmore.prefecture.controller;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.common.client.hystrix.UserClientHystrix;
import cn.linkmore.prefecture.request.ReqCity;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.service.PrefectureService;

/**
 * Controller - 车区信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/prefecture/pres")
public class PrefectureController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PrefectureService preService;
	@Autowired
	private UserClientHystrix userClient;

	/**
	 * 根据主键查询详情
	 * 
	 * @param id Long
	 * @param language String
	 * @return
	 */
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureDetail findById(@PathVariable("id") Long id) {
		return this.preService.find(id);
	}

	/**
	 * 根据位置查询车区地图
	 * 
	 * @param reqPrefecture ReqPrefecture
	 * @param userId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/loc", method = RequestMethod.GET)
	@ResponseBody
	public List<ResPrefecture> findPreListByLoc(@RequestBody ReqPrefecture reqPrefecture) {
		ResUser resUser = userClient.getUserCacheKey(reqPrefecture.getUserId());
		return this.preService.findPreListByLoc(reqPrefecture, resUser);
	}

	/**
	 * 根据城市id查询车区卡片列表
	 * 
	 * @param cityId Long
	 * @param userId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/city", method = RequestMethod.PUT)
	@ResponseBody
	public List<ResPrefectureList> findPreListByCityId(@RequestBody ReqCity reqCity) {
		ResUser resUser = userClient.getUserCacheKey(reqCity.getUserId());
		List<ResPrefectureList> preList = this.preService.findPreListByCityId(reqCity.getCityId(), resUser);
		return preList;
	}

	/**
	 * 根据车区id查询计费策略
	 * 
	 * @param preId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/strategy/{preId}", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureStrategy findPreStrategy(@PathVariable Long preId) {
		ResPrefectureStrategy resPreStrategy = preService.getPreStrategy(preId);
		return resPreStrategy;
	}
	
	/**
	 * addUser
	 * 
	 * @param username String
	 * @return
	 */
	@RequestMapping(value = "/v2.0/addUser", method = RequestMethod.GET)
	@ResponseBody
	public String findPreStrategy(@RequestParam(value="username") String username) {
		return username;
	}
	
	/**
	 * addUserReq
	 * 
	 * @param name String
	 * @return
	 */
	@RequestMapping(value = "/v2.0/addUserReq", method = RequestMethod.PUT)
	@ResponseBody
	public String addUserReq(@RequestBody String name) {
		return name;
	}
}
