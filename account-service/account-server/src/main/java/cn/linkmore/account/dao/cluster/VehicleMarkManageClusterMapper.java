package cn.linkmore.account.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.response.ResVechicleMark;
@Mapper
public interface VehicleMarkManageClusterMapper {

    VehicleMarkManage selectById(Long id);

	List<VehicleMarkManage> selectByUserId(@Param("userId")Long userId);

	List<ResVechicleMark> selectResList(@Param("userId")Long userId);

}