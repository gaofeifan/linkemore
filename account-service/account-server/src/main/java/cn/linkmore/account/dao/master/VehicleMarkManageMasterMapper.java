package cn.linkmore.account.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.response.ResVechicleMark;
@Mapper
public interface VehicleMarkManageMasterMapper {
    int deleteById(Long id);

    int insert(VehicleMarkManage record);

    int insertSelective(VehicleMarkManage record);

    int updateByIdSelective(VehicleMarkManage record);

    int updateById(VehicleMarkManage record);
}