package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.VehicleMarkManage;
@Mapper
public interface VehicleMarkManageMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VehicleMarkManage record);

    int updateByPrimaryKey(VehicleMarkManage record);
}
    