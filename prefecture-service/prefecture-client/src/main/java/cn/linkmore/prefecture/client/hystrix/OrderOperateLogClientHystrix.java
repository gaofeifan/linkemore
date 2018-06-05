package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OrderOperateLogClient;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class OrderOperateLogClientHystrix implements OrderOperateLogClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service order_operate list() hystrix");
		return null;
	}

	@Override
	public List<ResOrderOperateLogEntity> detail(Long id) {
		log.info("prefecture service order_operate detail() hystrix");
		return null;
	}

	@Override
	public List<ResOrderOperateLogEntity> export(ReqOrderOperateLogExcel bean) {
		log.info("prefecture service order_operate export() hystrix");
		return null;
	}
}
