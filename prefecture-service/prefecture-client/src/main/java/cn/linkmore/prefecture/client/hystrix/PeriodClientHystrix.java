/**
 * 
 */
package cn.linkmore.prefecture.client.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.prefecture.client.PeriodClient;
import cn.linkmore.prefecture.request.ReqAbuttingHourPeriod;
import cn.linkmore.prefecture.request.ReqAbuttingPeriod;

/**
 * @author PPYX
 *
 */
@Component
public class PeriodClientHystrix implements PeriodClient {

	@Override
	public ViewPage list(ReqAbuttingPeriod abuttingPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewPage selectPeriodById(ReqAbuttingPeriod abuttingPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveAbuttingPeriod(ReqAbuttingPeriod abuttingPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateAbuttingPeriod(ReqAbuttingPeriod abuttingPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteAbuttingPeriod(ReqAbuttingPeriod abuttingPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewPage selectHourPeriod(ReqAbuttingPeriod abuttingPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveHourPeriod(ReqAbuttingHourPeriod abuttingHourPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateHourPeriod(ReqAbuttingHourPeriod abuttingHourPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteHourPeriod(ReqAbuttingHourPeriod abuttingHourPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

}
