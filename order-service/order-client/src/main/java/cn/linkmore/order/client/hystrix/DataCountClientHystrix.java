package cn.linkmore.order.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.client.DataCountClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqDataCount;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrder;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderOperateLog;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResUserOrder;

/**
 * @author   GFF
 * @Date     2018年8月3日
 * @Version  v2.0
 */
@Component
public class DataCountClientHystrix implements DataCountClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Override
	public void saveVirtualData(ReqDataCount copyObject) {
		log.info("saveVirtualData(ReqDataCount copyObject) ");
		
	}

	@Override
	public void stop() {
		log.info("void stop() ");
	}

	@Override
	public void start() {
		log.info("void start() ");		
	}

	@Override
	public void delete(Long ids) {
		log.info("void delete(Long ids) ");				
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("ViewPage list(ViewPageable pageable)  ");				
		return null;
	}
	
	
	
	
}
