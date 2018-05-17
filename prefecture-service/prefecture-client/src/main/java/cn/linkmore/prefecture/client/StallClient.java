package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StallClientHystrix;
/**
 * 远程调用 - 车位操作
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/prefecture/stall", fallback=StallClientHystrix.class,configuration = FeignConfiguration.class)
public interface StallClient {
	
	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * 
	 * @param lockSn String
	 */
	@RequestMapping(value = "/v2.0/order", method=RequestMethod.PUT)
	public void order(@RequestParam("lockSn") String lockSn);
	/**
	 * 取消订单释放车位
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/cancel", method=RequestMethod.PUT)
	public void cancel(@RequestParam("stallId") Long stallId);
	
	/**
	 * 降锁操作
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/downlock", method=RequestMethod.PUT)
	public void downlock(@RequestParam("stallId") Long stallId);
	
	/**
	 * 升锁操作
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/uplock", method=RequestMethod.PUT)
	public void uplock(@RequestParam("stallId") Long stallId);
	
	/**
	 * 结账立场释放车位
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/pay", method=RequestMethod.PUT)
	public void pay(@RequestParam("stallId") Long stallId);
	
	
}
