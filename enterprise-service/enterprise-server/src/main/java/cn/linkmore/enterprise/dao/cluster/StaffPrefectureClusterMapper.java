package cn.linkmore.enterprise.dao.cluster;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.linkmore.enterprise.controller.staff.response.PrefectureResponseBean;
import cn.linkmore.enterprise.entity.Orders;
import cn.linkmore.enterprise.entity.Stall;

public interface StaffPrefectureClusterMapper {

	Stall selectByPrimaryKey(Long id);
	
	int updateStallByInfo(@Param("record") Stall record);
	
	Orders findStallLatest(Long stallId);
	
	/*
	 * 根据专区id获取各项状态的车位数
	 */
	PrefectureResponseBean findResponseBeanByPreId(Map<String, Object> param);
	
}
