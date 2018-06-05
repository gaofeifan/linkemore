package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.response.ResAdminUserAuth;
@Mapper
public interface AdminUserAuthClusterMapper {

	List<ResAdminUserAuth> findList(Map<String, Object> param);
}