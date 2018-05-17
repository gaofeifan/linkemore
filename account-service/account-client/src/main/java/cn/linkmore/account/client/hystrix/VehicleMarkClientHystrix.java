package cn.linkmore.account.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;

@Component
public class VehicleMarkClientHystrix  implements VehicleMarkClient{
	
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	public List<ResVechicleMark> list(@PathVariable ("userId")Long userId){
		return new ArrayList<ResVechicleMark>();
	}
	
	public void create( @RequestBody ReqVehicleMark bean) {
		
	}
	
	public void delete(@PathVariable Long id) {
		
	}
	
}

