package cn.linkmore.account.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.RechargeRecordClientHystrix;
import cn.linkmore.account.request.ReqRechargeRecordExcel;
import cn.linkmore.account.response.ResRechargeRecordExcel;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 储值记录--远程调用
 * 
 * @author GFF
 * @Date 2018年5月18日
 * @Version v2.0
 */
@FeignClient(value = "account-server", path = "/record", fallback = RechargeRecordClientHystrix.class, configuration = FeignConfiguration.class)
public interface RechargeRecordClient {

	/**
	 * @Description 分页查询
	 * @Author GFF
	 * @Version v2.0
	 */
	@RequestMapping(value="/v2.0/page",method=RequestMethod.POST)
	ViewPage findPage(@RequestBody ViewPageable pageable);

	/**
	 * @Description 查询导出数据
	 * @Author GFF
	 * @Version v2.0
	 */
	@RequestMapping(value="/v2.0/export",method=RequestMethod.POST)
	List<ResRechargeRecordExcel> findExportList(@RequestBody ReqRechargeRecordExcel bean);

}
