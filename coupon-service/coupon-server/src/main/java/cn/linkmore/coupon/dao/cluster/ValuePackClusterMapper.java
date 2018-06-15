package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import cn.linkmore.coupon.response.ResValuePack;

public interface ValuePackClusterMapper {

	List<ResValuePack> selectPackTypeList(Long comboType);
}