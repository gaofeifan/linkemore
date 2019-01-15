package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StallClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
/**
 * 远程调用 - 车位操作
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/feign/stall", fallback=StallClientHystrix.class,configuration = FeignConfiguration.class)
public interface StallClient {
	
	/**
	 * 隆锁异常关闭订单
	 * @param id
	 */
	@RequestMapping(value = "/v2.0/close", method = RequestMethod.PUT)
	public void close(@RequestParam("id") Long id) ;
	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * 
	 * @param lockSn String
	 */
	@RequestMapping(value = "/v2.0/order", method=RequestMethod.PUT)
	public void order(@RequestParam("id") Long id);
	/**
	 * 取消订单释放车位
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/cancel", method=RequestMethod.GET)
	@ResponseBody
	public boolean cancel(@RequestParam("stallId") Long stallId);
	
	/**
	 * 降锁操作
	 * @param stall
	 * @return true 车位锁降下成功 false 车位锁降下失败
	 */
	@RequestMapping(value = "/v2.0/downlock", method=RequestMethod.PUT)
	@ResponseBody
	public void downlock(@RequestBody ReqOrderStall stall);
	
	/**
	 * 升锁操作
	 * @param stallId
	 * @return true 车位锁升起成功 false 车位锁升起失败
	 */
	@RequestMapping(value = "/v2.0/uplock", method=RequestMethod.PUT)
	@ResponseBody
	public Boolean uplock(@RequestParam("stallId") Long stallId);
	
	
	/**
	 * 操作锁
	 * @param stall
	 * @return 
	 */
	@RequestMapping(value = "/v2.0/controllock", method=RequestMethod.POST)
	@ResponseBody
	public void controllock(@RequestBody   ReqControlLock  reqc);
	
	/**
	 * 管理版操作锁
	 * @param stall
	 * @return 
	 */
	@RequestMapping(value = "/v2.0/operatelock", method=RequestMethod.POST)
	@ResponseBody
	public void managerlock(@RequestBody   ReqControlLock  reqc);
	
	/**
	 * 管理版操作锁
	 * @param stall
	 * @return 
	 */
	@RequestMapping(value = "/v2.0/operatelockSn", method=RequestMethod.POST)
	@ResponseBody
	public void managerlockSn(@RequestBody   ReqControlLock  reqc);
	
	/**
	 *物业版操作锁 操作锁
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/oper-lock-wy", method = RequestMethod.POST)
	public void operLockWY(@RequestBody   ReqControlLock  reqc);
	
	/**
	 * 查看锁状态
	 * @param stallid
	 * @return 
	 */
	@RequestMapping(value = "/v2.0/watch", method=RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object>  watch(@RequestBody  Long stallId);
	
	/**
	 * 查询锁状态
	 * @param stallis
	 * @return 
	 */
	@RequestMapping(value = "/v2.0/lockstatus", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> lockstatus(@RequestBody  List<String> list);
	
	
	/**
	 * 结账立场释放车位
	 * @param stallId
	 * @return true 车位锁升起成功 false 车位锁升起失败
	 */
	@RequestMapping(value = "/v2.0/checkout", method=RequestMethod.PUT)
	@ResponseBody
	public Boolean checkout(@RequestParam("stallId") Long stallId);
	
	/**
	 * 根据车位id获取车位信息
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/{stallId}", method=RequestMethod.GET)
	@ResponseBody
	public ResStallEntity findById(@PathVariable("stallId") Long stallId);
	/**
	 * 根据锁编号获取车位信息
	 * @param sn 锁编号
	 * @return
	 */
	@RequestMapping(value = "/v2.0/lock/{sn}", method=RequestMethod.GET)
	@ResponseBody
	public ResStallEntity findByLock(@PathVariable("sn") String sn);
	
	
	@RequestMapping(value = "/v2.0/stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findStallList(@RequestBody Map<String, Object> param);
	
	@RequestMapping(value = "/v2.0/pre-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findPreStallList(@RequestBody Map<String, Object> param);
	
	
	/*****************************************************************/
	
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(@RequestBody Map<String,Object> param);

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStall stall) ;

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStall stall);
	
	@RequestMapping(value = "/v2.0/updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody ReqStall stall);

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public int check(@RequestBody ReqCheck reqCheck);

	@RequestMapping(value = "/v2.0/sn", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> sn(@RequestParam("lockId") Long lockId);

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResStallEntity detail(@RequestParam("id") Long id);

	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public int bind(@RequestParam("id") Long id, @RequestParam("sid") Long sid);

	@RequestMapping(value = "/v2.0/changed_up", method = RequestMethod.POST)
	@ResponseBody
	public int changedUp(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/changed_down", method = RequestMethod.POST)
	@ResponseBody
	public int changedDown(@RequestParam("id") Long id);
	
	@RequestMapping(value = "/v2.0/save_bind", method = RequestMethod.POST)
	@ResponseBody
	public void saveAndBind(@RequestParam("preId") Long preId,@RequestParam("stallName") String stallName,@RequestParam("sn") String sn);
	
	@RequestMapping(value = "/v2.0/find-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOps> findListByParam(@RequestBody Map<String, Object> param);
	
	@RequestMapping(value = "/v2.0/update-brand", method = RequestMethod.POST)
	@ResponseBody
	public int updateBrand(@RequestBody Map<String, Object> map);

	/**
	 * @Description  管理版使用
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/watch2", method = RequestMethod.POST)
	public Map<String,Object> watch2(@RequestBody Long stallId);
	
	@RequestMapping(value = "/v2.0/app/control", method = RequestMethod.POST)
	@ResponseBody
	public Boolean appControl(@RequestBody ReqControlLock reqc);
	
}
