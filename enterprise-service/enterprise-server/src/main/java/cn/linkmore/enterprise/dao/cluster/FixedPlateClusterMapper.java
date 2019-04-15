package cn.linkmore.enterprise.dao.cluster;

import java.util.List;

import cn.linkmore.enterprise.entity.FixedPlate;
import cn.linkmore.enterprise.response.ResFixedPlate;

public interface FixedPlateClusterMapper {


    FixedPlate selectByPrimaryKey(Long id);
    
    List<String> selectByFixedId(Long fixedId);
    
    List<String> selectUserNameByFixedId(Long fixedId);
    
    ResFixedPlate findPlateNosByStallId(Long stallId);
    
}