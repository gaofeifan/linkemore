package cn.linkmore.account.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void delete(@RequestParam("id") Long id,Long userId){
		this.vehicleMarkManageService.deleteById(id,userId);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ResVechicleMark> list(Long userId){
		return vehicleMarkManageService.selectList(userId);
	}
	
}
