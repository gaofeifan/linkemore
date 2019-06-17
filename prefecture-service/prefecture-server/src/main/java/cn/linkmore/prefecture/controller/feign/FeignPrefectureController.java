package cn.linkmore.prefecture.controller.feign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.prefecture.response.ResOpenPres;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StrategyGroupService;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 车区信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/pres")
public class FeignPrefectureController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PrefectureService preService;
	
	@Autowired
	private StrategyGroupService strategyGroupService;
	
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

	@RequestMapping(value = "/v2.0/by-pre-ids", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> findPreByIds(@RequestBody Map<String, Object> map) {
		return this.preService.findPreByIds(map);
	}
	
	@RequestMapping(value = "/v2.0/free-stall-num", method = RequestMethod.GET)
	@ResponseBody
	public Long findByGroupId(@RequestParam("stallId") Long stallId, @RequestParam("preId") Long preId) {
		return this.strategyGroupService.findFreeStall(stallId, preId);
	}
	
	@RequestMapping(value = "/v2.0/near-free-stall", method = RequestMethod.GET)
	@ResponseBody
	public String nearFreeStallLockSn(@RequestParam("stallId") Long stallId, @RequestParam("preId") Long preId) {
		return this.strategyGroupService.nearFreeStallLockSn(stallId, preId);
	}
	
	@ApiOperation(value = "根据车区id获取车位楼层", notes = "根据车区id获取车位楼层", consumes = "application/json")
	@RequestMapping(value = "/v2.0/get-floor", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getFloor(@Validated @RequestParam(value="preId", required=true) Long preId) {
		cn.linkmore.prefecture.response.ResPrefectureDetail detail =  this.preService.findById(preId);
		List<String> floorList = new ArrayList<String>();
		if(detail !=null && StringUtils.isNotBlank(detail.getUnderLayer())) {
			floorList = Arrays.asList(detail.getUnderLayer().split("、"));
		}else {
			floorList.add(Constants.FLOOR_ALL);
		}
		return floorList;
	}
	@RequestMapping(value = "/v2.0/open-list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResOpenPres> openPres(@RequestParam("appId") String appId) {
		List<ResOpenPres> list = this.preService.openPres(appId);
		return list;
	}
	
}
