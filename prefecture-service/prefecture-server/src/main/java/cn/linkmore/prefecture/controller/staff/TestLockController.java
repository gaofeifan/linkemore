package cn.linkmore.prefecture.controller.staff;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.prefecture.config.LockTools;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.util.JsonUtil;
import springfox.documentation.annotations.ApiIgnore;
@RestController
@RequestMapping("/feign")
public class TestLockController {
	@Resource
	private LockTools lockTools;
	
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public void lockinfo() {
		ResLockInfo lockInfo = lockTools.lockInfo("0000e3c0ad52855f");
		System.out.println(JsonUtil.toJson(lockInfo));
	}
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void locklist() {
		lockTools.lockListByGroupCode("08860493");
	}
	@RequestMapping(value="/up",method=RequestMethod.GET)
	public void lockup() {
		lockTools.upLock("0000e3c0ad52855f");
	}
	@RequestMapping(value="/down",method=RequestMethod.GET)
	public void down() {
		lockTools.downLock("0000e3c0ad52855f");
	}
	@RequestMapping(value="/hi",method=RequestMethod.GET)
	public void hi() {
		lockTools.lockSignalHistory("0000e3c0ad52855f");
	}
}
