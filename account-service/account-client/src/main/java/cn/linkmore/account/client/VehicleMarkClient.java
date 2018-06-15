package cn.linkmore.account.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.VehicleMarkClientHystrix;
import cn.linkmore.account.request.ReqVehMarkIdAndUserId;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 车牌号管理
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/feign/vehicle_mark", fallback=VehicleMarkClientHystrix.class,configuration = FeignConfiguration.class)
public interface VehicleMarkClient {
	/**
	 * @Description  列表查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/{userId}", method = RequestMethod.GET)
	public List<ResVechicleMark> list(@PathVariable ("userId")Long userId);
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	public void create( @RequestBody ReqVehicleMark bean);
	
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0", method = RequestMethod.DELETE)
	public void delete(@RequestBody ReqVehMarkIdAndUserId v);
	
	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/by_id/{id}", method = RequestMethod.GET)
	public ResVechicleMark findById(@PathVariable("id") Long id);
	
	/**
	 * @Description	根据用户id和车牌号查询
	 * @Author   jiaohanbin 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/plate_no", method = RequestMethod.POST)
	@ResponseBody
	public ResVechicleMark findByPlateNo(@RequestBody Map<String,Object> param);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqVehicleMark reqMark);
}
