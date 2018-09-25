package cn.linkmore.common.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseVersionClient;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqStaffAppVersion;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;

/**
 * 版本管理实现类
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@Component
public class BaseVersionClientHystrix implements BaseVersionClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResVersionBean current(@PathVariable("source") Integer source) {
		log.info("common service version  current(Integer source) hystrix");
		return null;
	}

	@Override
	public void report(@RequestBody ReqVersion vrb) {
		log.info("common service version report(ReqVersion vrb) hystrix");
	}

	@Override
	public void saveApp(ReqAppVersion version) {
		log.info("common service AppVersion saveApp(ReqAppVersion version) hystrix");
	}

	@Override
	public void updateApp(ReqAppVersion version) {
		log.info("common service AppVersion updateApp(ReqAppVersion version) hystrix");
	}

	@Override
	public void deleteAppById(@RequestBody List<Long> ids) {
		log.info("common service AppVersion deleteAppById(List<Long> ids) hystrix");
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("common service ViewPage findPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public Boolean check(ReqCheck check) {
		log.info("common service boolean check(ReqCheck check) hystrix");
		return null;
	}

	@Override
	public ViewPage findUserPage(ViewPageable pageable) {
		log.info("common service ViewPage findUserPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public void saveStaff(ReqStaffAppVersion version) {
		// TODO Auto-generated method stub
		log.info("common service void saveStaff(ReqStaffAppVersion version) hystrix");
		
	}

	@Override
	public void updateStaff(ReqStaffAppVersion version) {
		log.info("common service void updateStaff(ReqStaffAppVersion version) hystrix");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStaffById(List<Long> ids) {
		log.info("common service void deleteStaffById(List<Long> ids)) hystrix");
		// TODO Auto-generated method stub
		
	}

	@Override
	public ViewPage findStaffPage(ViewPageable pageable) {
		// TODO Auto-generated method stub
		log.info("common service ViewPage findStaffPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public Boolean checkStaff(ReqCheck check) {
		log.info("common service Boolean checkStaff(ReqCheck check) hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteStaff(List<Long> ids) {
		log.info("common service void deleteStaff(List<Long> ids) hystrix");
		// TODO Auto-generated method stub
		
	}

	
	
	
}
