package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.account.entity.WebSiteUser;
@Mapper
public interface WebSiteUserMasterMapper {
	
    int register(WebSiteUser record);

}