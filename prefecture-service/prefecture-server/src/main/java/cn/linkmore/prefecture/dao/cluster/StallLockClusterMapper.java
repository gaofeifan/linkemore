package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.response.ResStallLock;
@Mapper
public interface StallLockClusterMapper {

    ResStallLock findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResStallLock> findPage(Map<String, Object> param);

	List<ResStallLock> findList(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	List<ResStallLock> findAll(Map<String, Object> param);

	int checkSn(Map<String, Object> param);

	int checkFormerSn(Map<String, Object> param);

}