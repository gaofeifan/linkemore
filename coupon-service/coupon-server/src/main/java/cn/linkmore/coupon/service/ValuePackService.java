package cn.linkmore.coupon.service;

import java.util.List;
import cn.linkmore.coupon.response.ResValuePack;

public interface ValuePackService {

	List<ResValuePack> selectPackTypeList(Long comboType);

}
