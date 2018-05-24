package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.StallAssign;

@Mapper
public interface StallAssignClusterMapper {  

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
    StallAssign findById(Long id); 
    
    /**
     * 查询最新的实体
     * @param lockSn
     * @return
     */
    StallAssign findByLockSn(String lockSn);
}