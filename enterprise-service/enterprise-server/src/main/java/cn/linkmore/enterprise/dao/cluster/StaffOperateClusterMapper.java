package cn.linkmore.enterprise.dao.cluster;

import cn.linkmore.enterprise.entity.MobileMessage;

public interface StaffOperateClusterMapper {

	MobileMessage findLatest(Long mobile);
	
}
