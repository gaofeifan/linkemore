package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.account.entity.WebSiteUser;

@Mapper
public interface WebSiteUserClusterMapper {
	
    WebSiteUser findByMobile(String mobile);

}