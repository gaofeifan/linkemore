package cn.linkmore.prefecture.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StallOperateLogClientHystrix;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;
/**
 * 远程调用 - 车位操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/stall_operate", fallback=StallOperateLogClientHystrix.class,configuration = FeignConfiguration.class)
public interface StallOperateLogClient {
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOperateLog> detail(@RequestBody Long id);
	/**
	 * 导出
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOperateLog> export(@RequestBody ReqStallOperateLogExcel bean);
	
}
