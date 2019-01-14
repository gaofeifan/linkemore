package cn.linkmore.common.controller.feign;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.common.response.ResBaseAppVersionGroup;
import cn.linkmore.common.service.BaseAppVersionGroupService;

/**
 * 灰度用户组管理
 * @author llh
 *
 */
@RestController
@RequestMapping("/feign/version/user_group")
public class FeignBaseVersionGroupController {
	
	@Resource
	private BaseAppVersionGroupService baseAppVersionGroupService;
	
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	public int insert(@RequestBody ReqBaseAppVersionGroup record) {
		return baseAppVersionGroupService.insert(record);
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	public int deleteByIds(@RequestBody List<Long> ids) {
		return baseAppVersionGroupService.deleteByIds(ids);
	}
	
	@RequestMapping(value="/page",method = RequestMethod.POST)
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return baseAppVersionGroupService.findPage(pageable);
	}
	
	@RequestMapping(value="/findList",method = RequestMethod.POST)
	public List<ResBaseAppVersionGroup> findList(@RequestBody Map<String, Object> param){
		return baseAppVersionGroupService.findList(param);
	}
	
	
}
