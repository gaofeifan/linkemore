package cn.linkmore.coupon.service;

import cn.linkmore.coupon.response.ResQrc;

public interface QrcService {

	ResQrc findByTempId(Long tempId);

}
