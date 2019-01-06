package cn.linkmore.enterprise.controller.ops;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntUserPlate;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.enterprise.service.EntUserPlateService;
import cn.linkmore.util.ObjectUtils;

@Controller
@RequestMapping("/ops/ent_user_plate")
public class EntUserPlateController {

	@Resource
	private EntUserPlateService entUserPlateService;
	
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.entUserPlateService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqEntUserPlate ent) {
		EntUserPlate userPlate = ObjectUtils.copyObject(ent, new EntUserPlate());
		this.entUserPlateService.save(userPlate);
	}
	
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqEntUserPlate ent) {
		EntUserPlate userPlate = ObjectUtils.copyObject(ent, new EntUserPlate());
		this.entUserPlateService.update(userPlate);
	}
	
	@RequestMapping(value = "/v2.0/ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids) {
		this.entUserPlateService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/exists", method = RequestMethod.POST)
	@ResponseBody
	public boolean exists(@RequestBody Map<String,Object> checkParam) {
		boolean flag = false;
		int num = entUserPlateService.exists(checkParam);
		if(num > 0) {
			flag = true;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/save-batch", method = RequestMethod.POST)
	@ResponseBody
	public int saveBatch(@RequestBody List<ReqEntUserPlate> plateList) {
		return this.entUserPlateService.saveBatch(plateList);
	}
	
}
