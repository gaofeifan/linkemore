package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.Prefecture;

@Mapper
public interface PrefectureClusterMapper {
	/**
	 * 查询主键对应的车区信息
	 * @param id 主健
	 * @return ResPrefecture车区信息
	 */
    Prefecture findById(Long id);
}