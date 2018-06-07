package cn.linkmore.prefecture.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
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
	
	
	
	
	
	/********************************ops***********************************/
	
	
	
	
	/**
	 * 车区列表分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.preService.findPage(pageable);
	}
	
	/**
	 * 删除专区
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.preService.delete(ids);
	}
	
	/**
	 * 检查名称重复
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.preService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 专区下拉列表
	 */
	@RequestMapping(value = "/v2.0/select_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> selectList() {
		return this.preService.findSelectList();
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureEntity prefecture) {
		return	this.preService.save(prefecture);
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureEntity prefecture) {
		return this.preService.update(prefecture);
	}
	
	/**
	 * 城市专区列表
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/find_city", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>>findListByCityId(@RequestBody Long cityId) {
		return preService.findByCity(cityId);
	}
	
	/**
	 * 查询导出列表
	 * @param reqPreExcel
	 * @return
	 */
	@RequestMapping(value = "/v2.0/export_list", method = RequestMethod.POST)
	public List<ResPreExcel> exportList(@RequestBody ReqPreExcel reqPreExcel) {
		List<ResPreExcel> list = this.preService.exportList(reqPreExcel);
		return list;
	}
	/**
	 * 根据车区名称检验是否存在
	 * @param preName
	 * @return
	 */
	@RequestMapping(value = "/v2.0/check_name", method = RequestMethod.POST)
	@ResponseBody
	public ResPrefectureDetail checkName(@RequestParam("preName") String preName) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name", preName);
		ResPrefectureDetail detail = this.preService.checkName(param);
		return detail;
	}
	
}
