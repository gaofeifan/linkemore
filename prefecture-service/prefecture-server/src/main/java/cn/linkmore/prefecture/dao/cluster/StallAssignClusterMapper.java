package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.response.ResStallAssign;

@Mapper
public interface StallAssignClusterMapper {
	List<ResStallAssign> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);
}