package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.response.ResStaffCity;

@Mapper
public interface AdminAuthCityClusterMapper {

	List<ResStaffCity> findStaffCitysByAdminId(Long id);
}