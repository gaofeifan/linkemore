package cn.linkmore.prefecture.controller.feign;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.prefecture.config.LockTools;
import cn.linkmore.prefecture.core.lock.AppLockDecorator;
import cn.linkmore.prefecture.core.lock.AppLockMessageDecorator;
import cn.linkmore.prefecture.core.lock.EntLockDecorator;
import cn.linkmore.prefecture.core.lock.EntLockMessageDecorator;
import cn.linkmore.prefecture.core.lock.LockFactory;
import cn.linkmore.prefecture.core.lock.StaffLockDecorator;
import cn.linkmore.prefecture.core.lock.StaffLockMessageDecorator;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockInfos;
import cn.linkmore.prefecture.response.ResLockMessage;

//@Controller
//@RequestMapping("/feign/lock-new")
public class LockController {

	@Resource
	private LockTools lockTools;
	
	private LockFactory factory = LockFactory.getInstance();
	
	public ResLockInfo lockInfo(@RequestParam("sn") String sn) {
		ResLockInfo info = factory.getLock(null).lockInfo(sn);
		return info;
	}
	
	@RequestMapping(value="/lock-list",method=RequestMethod.GET)
	@ResponseBody
	public List<ResLockInfo> lockList(@RequestParam("groupCode") String groupCode){
		List<ResLockInfo> list = factory.getLock(null).lockListByGroupCode(groupCode);
		return list;
	}

	@RequestMapping(value="/down-lock-mes",method=RequestMethod.GET)
	@ResponseBody
	public ResLockMessage downLockMes(@RequestParam("sn") String sn) {
		ResLockMessage lockMes = factory.getLock(null).downLockMes(sn);
		return lockMes;
	}
	
	@RequestMapping(value="/down-lock",method=RequestMethod.GET)
	@ResponseBody
	public Boolean downLock(@RequestParam("sn") String sn) {
		return factory.getLock(null).downLock(sn);
	}
	
	@RequestMapping(value="/up-lock",method=RequestMethod.GET)
	@ResponseBody
	public Boolean upLock(@RequestParam("sn") String sn) {
		return factory.getLock(null).upLock(sn);
	}
	
	@RequestMapping(value="/up-lock-mes",method=RequestMethod.GET)
	@ResponseBody
	public ResLockMessage upLockMes(@RequestParam("sn") String sn) {
		return this.lockTools.upLockMes(sn);
	}
	
	@RequestMapping(value="/lock-lists",method=RequestMethod.POST)
	@ResponseBody
	public List<ResLockInfos> lockLists( @RequestBody List<String> groups) {
		List<ResLockInfos> infos = new ArrayList<>();
		ResLockInfos info = null;
		for (String string : groups) {
			List<ResLockInfo> list = this.lockTools.lockListByGroupCode(string);
			info = new ResLockInfos();
			info.setGroupId(string);
			info.setInfos(list);
			infos.add(info);
		}
		return infos;
	}
	
	@RequestMapping(value="/up-lock-app",method=RequestMethod.POST)
	@ResponseBody
	public Boolean upLockApp(@RequestBody ReqControlLock reqc) {
		return this.factory.setReqc(reqc).getLock(AppLockDecorator.class).upLock(reqc.getLockSn());
	} 

	@RequestMapping(value="/up-lock-app-mes",method=RequestMethod.POST)
	@ResponseBody
	public Boolean upLockMesApp(@RequestBody ReqControlLock reqc) {
		return this.factory.setReqc(reqc).getLock(AppLockMessageDecorator.class).upLock(reqc.getLockSn());
	} 

	@RequestMapping(value="/up-lock-ent-mes",method=RequestMethod.POST)
	@ResponseBody
	public Boolean upLockMesEnt(@RequestBody ReqControlLock reqc) {
		return this.factory.setReqc(reqc).getLock(EntLockMessageDecorator.class).upLock(reqc.getLockSn());
	} 
	@RequestMapping(value="/up-lock-ent",method=RequestMethod.POST)
	@ResponseBody
	public Boolean upLockEnt(@RequestBody ReqControlLock reqc) {
		return this.factory.setReqc(reqc).getLock(EntLockDecorator.class).upLock(reqc.getLockSn());
	} 
	@RequestMapping(value="/up-lock-staff-mes",method=RequestMethod.POST)
	@ResponseBody
	public Boolean upLockMesStaff(@RequestBody ReqControlLock reqc) {
		return this.factory.setReqc(reqc).getLock(StaffLockMessageDecorator.class).upLock(reqc.getLockSn());
	} 
	@RequestMapping(value="/up-lock-staff",method=RequestMethod.POST)
	@ResponseBody
	public Boolean upLockStaff(@RequestBody ReqControlLock reqc) {
		return this.factory.setReqc(reqc).getLock(StaffLockDecorator.class).upLock(reqc.getLockSn());
	} 
}
