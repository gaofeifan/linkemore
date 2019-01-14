package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.third.client.H5PayClient;
import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;

@Component
public class H5PayClientHystrix implements H5PayClient{

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ResH5Degree wxopenid(ReqH5Token reqH5Token) {
		log.info("wxopenid-H5PayClientHystrix");
		return null;
	}

	@Override
	public ResH5Degree aliopenid(ReqH5Token reqH5Token) {
		log.info("aliopenid-H5PayClientHystrix");
		return null;
	}

	@Override
	public ResH5Term wxpay(ReqH5Term reqH5Term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String alipay(ReqH5Term reqH5Term) {
		// TODO Auto-generated method stub
		return null;
	}
}
