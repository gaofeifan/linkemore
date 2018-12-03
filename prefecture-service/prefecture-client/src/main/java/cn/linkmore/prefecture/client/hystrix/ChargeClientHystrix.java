/**
 * 
 */
package cn.linkmore.prefecture.client.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.prefecture.client.ChargeClient;
import cn.linkmore.prefecture.request.ReqPeriodCharge;



/**
 * @author zhengpengfei
 *
 */
@Component
public class ChargeClientHystrix implements ChargeClient{

	@Override
	public ViewPage list(ReqPeriodCharge reqPeriodCharge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAbuttingCharge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveAbuttingCharge(ReqPeriodCharge reqPeriodCharge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateAbuttingCharge(ReqPeriodCharge reqPeriodCharge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteAbuttingCharge(ReqPeriodCharge reqPeriodCharge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectChargeById(ReqPeriodCharge reqPeriodCharge) {
		// TODO Auto-generated method stub
		return null;
	}

}
