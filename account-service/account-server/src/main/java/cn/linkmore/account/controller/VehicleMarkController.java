package cn.linkmore.account.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

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
	public void create( @RequestBody ReqVehicleMark bean,Long userId) {
		this.vehicleMarkManageService.save(bean,userId);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathParam("id") Long id,Long userId){
		this.vehicleMarkManageService.deleteById(id,userId);
	}
	
	@RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
	public List<ResVechicleMark> list(@PathParam("userId")Long userId){
		return vehicleMarkManageService.selectResList(userId);
	}
	
}
