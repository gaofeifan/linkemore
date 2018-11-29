package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;

public interface H5PayService {

	ResH5Degree wxOpenid( ReqH5Token reqH5Token);
	
	ResH5Degree aliOpenid( ReqH5Token reqH5Token);
	
	ResH5Term	wxpay(ReqH5Term reqH5Term);
	
	ResH5Term	alipay(ReqH5Term reqH5Term);
	
}
