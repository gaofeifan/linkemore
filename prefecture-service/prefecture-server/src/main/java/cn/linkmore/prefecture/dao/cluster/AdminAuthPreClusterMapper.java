package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminAuthPre;

@Mapper
public interface AdminAuthPreClusterMapper {

	/**
	 * @Description  查询权限车区
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<AdminAuthPre> findList(Map<String, Object> map);

	/**
	 * @Description 根据车区id查询  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	AdminAuthPre findById(Long preId);
}