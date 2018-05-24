package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.StallAssign;

@Mapper
public interface StallAssignMasterMapper { 
	/**
	 * 持久化实体
	 * @param record
	 * @return
	 */
    int save(StallAssign record);   

    /**
     * 取消更新
     * @param record
     * @return
     */
    int cancelUpdate(StallAssign record);

    /**
     * 下单更新
     * @param sa
     * @return
     */
	int orderUpdate(StallAssign sa);
}