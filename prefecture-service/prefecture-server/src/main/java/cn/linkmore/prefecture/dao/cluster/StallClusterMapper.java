package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.Stall;

@Mapper
public interface StallClusterMapper {
	/**
	 * 查询主键对应的车位信息
	 * @param id 主健
	 * @return ResStall车位信息
	 */
	Stall findById (Long id);
}