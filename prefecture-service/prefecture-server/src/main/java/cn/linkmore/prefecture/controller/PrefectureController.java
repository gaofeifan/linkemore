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
import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.prefecture.request.ReqCity;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPre;
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
@RequestMapping("/pres")
public class PrefectureController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PrefectureService preService;
	

	/**
	 * 根据主键查询详情
	 * 
	 * @param id Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureDetail findById(@PathVariable("id") Long id) {
		return this.preService.findById(id);
	}
	
	/**
	 * 根据主键查询批量车区名称
	 * 
	 * @param id Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/prename", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> prenames(@RequestBody List<Long> ids) {
		return this.preService.findList(ids);
	}

	/**
	 * 根据位置查询车区地图
	 * 
	 * @param reqPrefecture ReqPrefecture
	 * @return
	 */
	@RequestMapping(value = "/v2.0/loc", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefecture> findPreListByLoc(@RequestBody ReqPrefecture reqPrefecture) {
		return this.preService.findPreListByLoc(reqPrefecture);
	}

	/**
	 * 根据车区id查询计费策略
	 * 
	 * @param preId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/strategy/{preId}", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureStrategy findPreStrategy(@PathVariable ("preId") Long preId) {
		ResPrefectureStrategy resPreStrategy = preService.getPreStrategy(preId);
		return resPreStrategy;
	}
	/**
	 * 查询车区空闲车位
	 * 
	 * @param preId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/free_count", method = RequestMethod.GET)
	@ResponseBody
	public List<ResPrefectureList> refreshFreeStall() {
		List<ResPrefectureList> resPrefectureList = preService.getStallCount();
		return resPrefectureList;
	}
	
}
