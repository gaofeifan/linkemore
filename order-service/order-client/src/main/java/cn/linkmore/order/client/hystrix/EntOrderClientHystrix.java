package cn.linkmore.order.client.hystrix;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.response.ResCharge;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;

/**
 * @author   GFF
 * @Date     2018年8月3日
 * @Version  v2.0
 */
@Component
public class EntOrderClientHystrix implements EntOrderClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	

	@Override
	public List<ResOrderPlate> findPlateByPreId(Long preId) {
		log.info("feign order List<ResOrderPlate> findPlateByPreId(Long preId) ");
		return null;
	}

	@Override
	public BigDecimal findPreDayIncome(Long authStall) {
		log.info("feign BigDecimal findPreDayIncome(List<Long> authStall)");
		return null;
	}

	@Override
	public Map<String,Object> findTrafficFlow(Map<String, Object> map) {
		log.info("feign Integer findTrafficFlow(Map<String, Object> map");
		return null;
	}

	@Override
	public Map<String,Object> findProceeds(Map<String, Object> map) {
		log.info("feign BigDecimal findProceeds(Map<String, Object> map)");
		return null;
	}

	
	

	@Override
	public List<ResChargeDetail> findChargeDetail(Map<String, Object> param) {
		log.info("feign List<ResChargeDetail> findChargeDetail(Map<String, Object> param)");
		return null;
	}

	@Override
	public ResTrafficFlow findTrafficFlowList(Map<String, Object> param) {
		log.info("feign List<ResTrafficFlowList> findTrafficFlowList(Map<String, Object> param)");
		return null;
	}

	@Override
	public ResIncome findIncomeList(Map<String, Object> param) {
		log.info("feign List<ResIncomeList> findIncomeList(Map<String, Object> param)");
		return null;
	}

	/*@Override
	public List<ResCharge> findChargeDetailNew(Map<String, Object> param) {
		log.info("feign List<ResIncomeList> findChargeDetailNew(Map<String, Object> param)");
		return null;
	}*/
	
	@Override
	public List<ResPreOrderCount> findPreCountByIds(List<Long> ids) {
		log.info("feign order List<ResPreOrderCount> findPreCountByIds(List<Long> ids) ");
		return null;
	}

	@Override
	public BigDecimal findProceedsAmount(Map<String, Object> param) {
		log.info("feign order List<ResPreOrderCount> findProceedsAmount(Map<String, Object> param");
		return null;
	}

	@Override
	public Integer findTrafficFlowCount(Map<String, Object> param) {
		log.info("feign order Integer findTrafficFlowCount(Map<String, Object> param)");
		return null;
	}

	
}
