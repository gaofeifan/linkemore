package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.response.ResAdminUser;

@Mapper
public interface AdminUserClusterMapper {

	Integer count(Map<String, Object> param);

	List<ResAdminUser> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	ResAdminUser findById(Long id);

	List<ResAdminUser> findAll();
	
}