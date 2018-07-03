package cn.linkmore.prefecture.controller.ops;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqTargetSetting;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.TargetSettingService;
/**
 * Controller - 车区目标设置
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/ops/target_setting")
public class TargetSettingController { 
	@Autowired
	private TargetSettingService preTargetSettingService;
	
	@Autowired
	private PrefectureService prefectureService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(ReqTargetSetting reqTargetSetting){
		return this.preTargetSettingService.save(reqTargetSetting);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(ReqTargetSetting reqTargetSetting){
		return	this.preTargetSettingService.update(reqTargetSetting);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids){ 
		return	this.preTargetSettingService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.preTargetSettingService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.preTargetSettingService.findPage(pageable); 
	} 
	
	@RequestMapping(value = "/v2.0/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> prefectureList(HttpServletRequest request){
		return this.prefectureService.findSelectList();
	} 
}
