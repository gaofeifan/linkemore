package cn.linkmore.prefecture.controller.feign;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.service.PrefectureService;

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
	
	
}
