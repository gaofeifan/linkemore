package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;

public interface H5PayService {

	ResH5Degree wxOpenid( ReqH5Token reqH5Token);
	
	ResH5Degree aliOpenid( ReqH5Token reqH5Token);
	
}
