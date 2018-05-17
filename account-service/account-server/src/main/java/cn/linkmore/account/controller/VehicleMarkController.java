package cn.linkmore.account.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.VehicleMarkManageService;
	

@RestController
@RequestMapping("/account/vehicle_mark")
public class VehicleMarkController{

	@Resource
	private VehicleMarkManageService vehicleMarkManageService;
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void create( @RequestBody ReqVehicleMark bean) {
		this.vehicleMarkManageService.save(bean);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id){
		this.vehicleMarkManageService.deleteById(id);
	}
	
	@RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
	public List<ResVechicleMark> list(@PathVariable("userId")Long userId){
		return vehicleMarkManageService.selectResList(userId);
	}
	
	
}
