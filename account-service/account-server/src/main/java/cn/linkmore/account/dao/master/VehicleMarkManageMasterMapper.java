package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.VehicleMarkManage;
@Mapper
public interface VehicleMarkManageMasterMapper {
    int deleteById(Long id);

    int insert(VehicleMarkManage record);

    int insertSelective(VehicleMarkManage record);

    int updateByIdSelective(VehicleMarkManage record);

    int updateById(VehicleMarkManage record);
}