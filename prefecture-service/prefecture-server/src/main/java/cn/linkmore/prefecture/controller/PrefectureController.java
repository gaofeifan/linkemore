package cn.linkmore.prefecture.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.service.UserService;
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
	private UserService userService;

	/**
	 * 根据主键查询详情
	 * 
	 * @param id Long
	 * @param language String
	 * @return
	 */
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureDetail findById(@PathVariable("id") Long id, @RequestParam("language") String language) {
		return this.preService.find(id, language);
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
	public List<ResPrefecture> findPreListByLoc(@RequestBody ReqPrefecture reqPrefecture,
			@RequestParam("userId") Long userId) {
		User user = userService.getUserCacheKey(userId);
		return this.preService.findPreListByLoc(reqPrefecture, user);
	}

	/**
	 * 根据城市id查询车区卡片列表
	 * 
	 * @param cityId Long
	 * @param userId Long
	 * @param language String
	 * @return
	 */
	@RequestMapping(value = "/v2.0/city", method = RequestMethod.GET)
	@ResponseBody
	public List<ResPrefectureList> findPreListByCityId(@RequestParam("cityId") Long cityId,
			@RequestParam("userId") Long userId, @RequestParam("language") String language) {
		User user = userService.getUserCacheKey(userId);
		List<ResPrefectureList> preList = this.preService.findPreListByCityId(cityId, language, user);
		return preList;
	}

	/**
	 * 根据车区id查询计费策略
	 * 
	 * @param preId Long
	 * @param language String
	 * @return
	 */
	@RequestMapping(value = "/v2.0/strategy", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureStrategy findPreStrategy(@RequestParam("preId")Long preId,@RequestParam("language") String language) {
		ResPrefectureStrategy resPreStrategy = preService.getPreStrategy(preId, language);
		return resPreStrategy;
	}
}
