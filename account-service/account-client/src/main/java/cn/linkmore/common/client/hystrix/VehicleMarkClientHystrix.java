package cn.linkmore.common.client.hystrix;

import java.util.List;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.VehicleMarkManageService;

@Component
public class VehicleMarkClientHystrix {
	
	@Resource
	private VehicleMarkManageService vehicleMarkManageService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
	public List<ResVechicleMark> list(@PathParam("userId")Long userId) { 
		return vehicleMarkManageService.selectList(userId);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void create( @RequestBody ReqVehicleMark bean,Long userId) {
		this.vehicleMarkManageService.save(bean,userId);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathParam("id") Long id,Long userId){
		this.vehicleMarkManageService.deleteById(id,userId);
	}
}

