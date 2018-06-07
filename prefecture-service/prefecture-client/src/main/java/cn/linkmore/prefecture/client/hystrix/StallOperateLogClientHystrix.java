package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallOperateLogClient;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;
/**
 * 远程调用实现 - 车位操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallOperateLogClientHystrix implements StallOperateLogClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall_operate list() hystrix");
		return null;
	}

	@Override
	public List<ResStallOperateLog> detail(Long id) {
		log.info("prefecture service stall_operate detail() hystrix");
		return null;
	}

	@Override
	public List<ResStallOperateLog> export(ReqStallOperateLogExcel bean) {
		log.info("prefecture service stall_operate_log export() hystrix");
		return new ArrayList<ResStallOperateLog>();
	}
}
