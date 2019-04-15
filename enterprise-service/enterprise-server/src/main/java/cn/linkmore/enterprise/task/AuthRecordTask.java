package cn.linkmore.enterprise.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.service.AuthRecordService;

@Component
public class AuthRecordTask {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private AuthRecordService authRecordService;

	@Scheduled(cron = "0 0/30 * * * ?")
	//@Scheduled(cron = "0 0 1 * * ?")	//每天凌晨1点触发
	public void run() {
		int num = authRecordService.updateOverdueStatus();
		log.info("sync auth record thread...num :{}",num);
	}
}
