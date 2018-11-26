package cn.linkmore.prefecture.controller.feign;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.prefecture.config.LockTools;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockMessage;

@Controller
@RequestMapping("/feign/lock")
public class FeignLockController {

	@Resource
	private LockTools lockTools;
	
	@RequestMapping(value="/lock-info",method=RequestMethod.GET)
	@ResponseBody
	public ResLockInfo lockInfo(@RequestParam("sn") String sn) {
		ResLockInfo info = this.lockTools.lockInfo(sn);
		return info;
	}
	
	@RequestMapping(value="/lock-list",method=RequestMethod.GET)
	@ResponseBody
	public List<ResLockInfo> lockList(@RequestParam("groupCode") String groupCode){
		List<ResLockInfo> list = this.lockTools.lockListByGroupCode(groupCode);
		return list;
	}

	@RequestMapping(value="/down-lock-mes",method=RequestMethod.GET)
	@ResponseBody
	public ResLockMessage downLockMes(@RequestParam("sn") String sn) {
		return this.lockTools.downLockMes(sn);
	}
	
	@RequestMapping(value="/down-lock",method=RequestMethod.GET)
	@ResponseBody
	public Boolean downLock(@RequestParam("sn") String sn) {
		return this.lockTools.downLock(sn);
	}
	
	@RequestMapping(value="/up-lock",method=RequestMethod.GET)
	@ResponseBody
	public Boolean upLock(@RequestParam("sn") String sn) {
		return this.lockTools.upLock(sn);
	}
	
	@RequestMapping(value="/up-lock-mes",method=RequestMethod.GET)
	@ResponseBody
	public ResLockMessage upLockMes(@RequestParam("sn") String sn) {
		return this.lockTools.upLockMes(sn);
	}
}
