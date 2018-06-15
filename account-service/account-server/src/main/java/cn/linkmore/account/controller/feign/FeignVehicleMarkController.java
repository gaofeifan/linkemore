package cn.linkmore.account.controller.feign;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqVehMarkIdAndUserId;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.VehicleMarkManageService;
	
/**
 * 车牌号管理
 * @author   GFF 
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/vehicle_mark")
public class FeignVehicleMarkController{

	@Resource
	private VehicleMarkManageService vehicleMarkManageService;
	
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	public void create( @RequestBody ReqVehicleMark bean) {
		this.vehicleMarkManageService.save(bean);
	}
	
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0", method = RequestMethod.DELETE)
	public void delete(@RequestBody ReqVehMarkIdAndUserId v){
		this.vehicleMarkManageService.deleteById(v);
	}
	
	/**
	 * @Description  列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/{userId}", method = RequestMethod.GET)
	public List<ResVechicleMark> list(@PathVariable("userId")Long userId){
		return vehicleMarkManageService.findResList(userId);
	}
	
	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/by_id/{id}", method = RequestMethod.GET)
	public ResVechicleMark findById(@PathVariable("id") Long id) {
		return this.vehicleMarkManageService.findById(id);
	}
	
	/**
	 * @Description	根据用户id和车牌号查询
	 * @Author   jiaohanbin 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/plate_no", method = RequestMethod.POST)
	@ResponseBody
	public ResVechicleMark findByPlateNo(@RequestBody Map<String,Object> param) {
		return this.vehicleMarkManageService.findByPlateNo(param);
	}
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	public int update(@RequestBody ReqVehicleMark bean) {
		return this.vehicleMarkManageService.update(bean);
	}
	
}
