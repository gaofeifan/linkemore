package cn.linkmore.enterprise.dao.cluster;

import java.util.List;

import cn.linkmore.enterprise.entity.FixedPlate;

public interface FixedPlateClusterMapper {


    FixedPlate selectByPrimaryKey(Long id);
    
    List<String> selectByFixedId(Long fixedId);
    
    List<String> selectUserNameByFixedId(Long fixedId);
    
}