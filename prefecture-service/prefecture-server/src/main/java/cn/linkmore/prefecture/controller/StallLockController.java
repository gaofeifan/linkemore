package cn.linkmore.prefecture.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.service.StallLockService;

/**
 * 车位锁
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/stall_lock")
public class StallLockController {

	@Autowired
	private StallLockService stallLockService;


	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStallLock record) {
		return this.stallLockService.save(record);
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStallLock record) {
		return this.stallLockService.update(record);
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id) {
		return	this.stallLockService.delete(id);
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.stallLockService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.stallLockService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/all", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> findAll(@RequestParam("lockId") Long lockId){
		return this.stallLockService.findAll(lockId);
	}

}
