package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminAuthCity;
import cn.linkmore.prefecture.response.ResStaffCity;

@Mapper
public interface AdminAuthCityClusterMapper {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffCity> findStaffCitysByAdminId(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<AdminAuthCity> findList(Map<String, Object> map);
}