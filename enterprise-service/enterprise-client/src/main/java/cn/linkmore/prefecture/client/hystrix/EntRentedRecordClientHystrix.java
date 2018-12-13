package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
@Component
public class EntRentedRecordClientHystrix implements EntRentedRecordClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void updateDownTime(Long stallId) {
		log.info("ops staffauth service void updateDownTime(Long stallId)) hystrix");
	}
	@Override
	public ResEntRentedRecord findLastPlateNumber(Long stallId) {
		log.info("ops staffauth service ResEntRentedRecord findLastPlateNumber(Long stallId) ) hystrix");
		return null;
	}
	@Override
	public List<ResEntRentedRecord> findLastPlateNumberByPreId(Long preId) {
		log.info("ops staffauth service List<ResEntRentedRecord> findLastPlateNumberByPreId(Long preId) hystrix");
		return null;
	}

	
	
}
