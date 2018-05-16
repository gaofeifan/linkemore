package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AccessDetail;
@Mapper
public interface AccessDetailClusterMapper {

    AccessDetail selectById(Integer id);
}