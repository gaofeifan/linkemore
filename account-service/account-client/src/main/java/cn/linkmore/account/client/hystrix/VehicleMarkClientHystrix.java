package cn.linkmore.account.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.request.ReqVehMarkIdAndUserId;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;

/**
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class VehicleMarkClientHystrix  implements VehicleMarkClient{
	
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
 
	public List<ResVechicleMark> list(@PathVariable ("userId")Long userId){
		log.info("account service VechicleMark list(Long userId) hystrix");
		return null;
	}
	 
	public void create( @RequestBody ReqVehicleMark bean) {
		log.info("account service VechicleMark create(ReqVehicleMark bean) hystrix");
	};
	 
	@Override
	public void delete(ReqVehMarkIdAndUserId v) {
		log.info("account service VechicleMark delete() Long id) hystrix");
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResVechicleMark findById(Long id) {
		log.info("account service VechicleMark findById() Long id) hystrix");
		return null;
	}

	@Override
	public ResVechicleMark findByPlateNo(Map<String, Object> param) {
		log.info("account service VechicleMark findByPlateNo() param) hystrix");
		return null;
	}

	@Override
	public void update(ReqVehicleMark reqMark) {
		log.info("account service VechicleMark reqMark() reqMark) hystrix");
	}

	@Override
	public int insertByNoRepeat(ReqVehicleMark bean) {
		log.info("account service insert(ReqVehicleMark bean) hystrix");
		return 0;
	}
	
}

