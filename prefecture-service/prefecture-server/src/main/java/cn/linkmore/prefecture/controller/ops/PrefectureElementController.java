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
import cn.linkmore.prefecture.request.ReqPrefectureElement;
import cn.linkmore.prefecture.service.PrefectureElementService;

/**
 * Controller - 车区元素
 * 
 * @author jiaohanbin
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/ops/prefecture_element")
public class PrefectureElementController {
	
	@Autowired
	private PrefectureElementService elementService;
	
	/**
	 * 新增
	 * @param reqPrefectureEle
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureElement reqPrefectureEle) {
		return this.elementService.save(reqPrefectureEle);
	}
	
	/**
	 * 更新
	 * @param reqPrefectureEle
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureElement reqPrefectureEle) {
		return this.elementService.update(reqPrefectureEle);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.elementService.delete(ids);
	}	
	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.elementService.findPage(pageable);
	}
	
}
