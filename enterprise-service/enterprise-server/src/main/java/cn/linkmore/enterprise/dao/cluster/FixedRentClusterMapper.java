package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.enterprise.entity.FixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResFixedRentStall;
import cn.linkmore.enterprise.response.ResStall;

public interface FixedRentClusterMapper {

    FixedRent selectByPrimaryKey(Long id);
    
    List<ResFixedRentStall> findPage(Map<String, Object> param);
    
    Integer count(Map<String, Object> param);

	List<ResStall> freeStallList(Map<String, Object> map);
	
	List<ResStall> stallList(Map<String, Object> map);

}