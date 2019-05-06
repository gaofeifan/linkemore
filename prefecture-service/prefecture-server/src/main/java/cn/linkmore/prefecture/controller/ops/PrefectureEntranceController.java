package cn.linkmore.prefecture.controller.ops;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;
import cn.linkmore.prefecture.service.PrefectureEntranceService;

/**
 * Controller - 入口信息
 * 
 * @author jiaohanbin
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/ops/prefecture_entrance")
public class PrefectureEntranceController {
	
	@Autowired
	private PrefectureEntranceService entranceService;
	
	/**
	 * 新增
	 * @param reqPrefectureEle
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureEntrance reqPrefectureEle) {
		return this.entranceService.save(reqPrefectureEle);
	}
	
	/**
	 * 更新
	 * @param reqPrefectureEle
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureEntrance reqPrefectureEle) {
		return this.entranceService.update(reqPrefectureEle);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.entranceService.delete(ids);
	}	
	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.entranceService.findPage(pageable);
	}
	
}
