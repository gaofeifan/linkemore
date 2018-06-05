package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.response.ResAdminAuth;
@Mapper
public interface AdminAuthClusterMapper {

	List<ResAdminAuth> findList(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResAdminAuth> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	ResAdminAuth findById(Long id);
}