package cn.linkmore.order.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
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
public class OrderClientHystrix implements OrderClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	public ResUserOrder last( Long userId){
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
	public ResUserOrder findStallLatest(Long stallId) {
		log.info("ResUserOrder findStallLatest  :{}",stallId);
		return null;
	}

	@Override
	public ResOrder findById(Long id) {
		log.info("ResOrder findById(Long id)");
		return null;
	}
	
	
	public void updateLockStatus(Map<String, Object> param) {
		log.info("updateLockStatus param :{}",JSON.toJSON(param));
	}

	@Override
	public ResUserOrder findOrderById(Long id) {
		log.info("findOrderById :{}",id);
		return null;
	}

	@Override
	public void updateClose(Map<String, Object> param) {
		log.info("updateClose :{}",param);
	}
	
	@Override
	public void updateDetail(Map<String, Object> param) {
		log.info("updateDetail :{}",param);
	}

	@Override
	public void savel(ResOrderOperateLog resOrderOperateLog) {
		log.info("savel :{}",resOrderOperateLog);
		
	}

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
	
	
	
	
}
