package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.account.client.VehicleMarkClient;
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
	 
	public void delete(@PathVariable("id") Long id) {
		log.info("account service VechicleMark delete() Long id) hystrix");
	}

	@Override
	public ResVechicleMark findById(@PathVariable("id") Long id) {
		log.info("account service VechicleMark findById() Long id) hystrix");
		return null;
	}
	
}

