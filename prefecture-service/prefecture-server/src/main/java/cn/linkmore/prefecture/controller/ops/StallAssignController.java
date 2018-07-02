package cn.linkmore.prefecture.controller.ops;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallAssignService;
import cn.linkmore.prefecture.service.StallService;
/**
 * Controller - 车位指定
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/ops/stall_assign")
public class StallAssignController {
	@Resource
	private StallAssignService stallAssignService;
	
	@Autowired
	private PrefectureService prefectureService;
	
	@Autowired
	private StallService stallService;
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.stallAssignService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> prefectureList(){
		return this.prefectureService.findSelectList();
	} 
	
	@RequestMapping(value = "/v2.0/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(@RequestBody Map<String,Object> param){
		return this.stallService.findList(param);
	} 
}
