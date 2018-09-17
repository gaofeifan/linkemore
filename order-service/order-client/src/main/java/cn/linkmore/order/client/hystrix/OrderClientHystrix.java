package cn.linkmore.order.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResUserOrder;

/**
 * @author   GFF
 * @Date     2018年8月3日
 * @Version  v2.0
 */
@Component
public class OrderClientHystrix implements OrderClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	public ResUserOrder last(@RequestParam("userId") Long userId){
		log.info("latest order :{}",userId);
		return null;
	}

	@Override
	public void downMsgPush(Long orderId, Long stallId) {
		log.info("downMsgPush order :{}",orderId + "," + stallId);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("downMsgPush order :{}",pageable);
		return null;
	}

	@Override
	public List<ResOrderExcel> exportList(ReqOrderExcel bean) {
		log.info("downMsgPush order :{}",bean);
		return null;
	}

	@Override
	public Integer getPlateLastOrderStatus(String carno) {
		log.info("getPlateLastOrderStatus carno :{}",carno);
		return null;
	}

	@Override
	public void updateLockStatus(Map<String, Object> param) {
		log.info("updateLockStatus param :{}",JSON.toJSON(param));
	}
}
