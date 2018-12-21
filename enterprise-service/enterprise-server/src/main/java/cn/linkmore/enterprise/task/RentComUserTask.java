package cn.linkmore.enterprise.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.service.RentEntService;
import cn.linkmore.enterprise.service.RentEntUserService;

@Component
public class RentComUserTask {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private RentEntService rentEntService;

	@Resource
	private RentEntUserService rentEntUserService;

	@Scheduled(cron = "0 0/10 * * * ?")
	//@Scheduled(cron = "0 0 1 * * ?")	//每天凌晨1点触发
	public void run() {
		log.info("sync rent com user thread...");
		rentEntUserService.syncRentStall();
		rentEntService.updateOverdueStatus();
		
		//rentEntUserService.syncRentPersonalUserStall();
	}

	
}
