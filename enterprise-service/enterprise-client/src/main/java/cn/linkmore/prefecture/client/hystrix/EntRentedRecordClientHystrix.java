package cn.linkmore.prefecture.client.hystrix;

import org.springframework.stereotype.Component;

import cn.linkmore.prefecture.client.EntRentedRecordClient;
@Component
public class EntRentedRecordClientHystrix implements EntRentedRecordClient {

	@Override
	public void updateDownTime(Long stallId) {
		// TODO Auto-generated method stub

	}

}
