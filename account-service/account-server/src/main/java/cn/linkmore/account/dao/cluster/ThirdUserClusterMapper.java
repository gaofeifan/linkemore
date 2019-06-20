package cn.linkmore.account.dao.cluster;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.account.entity.ThirdUser;

@Mapper
public interface ThirdUserClusterMapper {
	
    ThirdUser findByAccountName(Map<String,Object> param);
    
    ThirdUser findByUserId(Map<String,Object> param);

}