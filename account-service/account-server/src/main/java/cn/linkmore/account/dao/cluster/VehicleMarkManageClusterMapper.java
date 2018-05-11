package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.VehicleMarkManage;
@Mapper
public interface VehicleMarkManageClusterMapper {

    VehicleMarkManage selectById(Long id);

}