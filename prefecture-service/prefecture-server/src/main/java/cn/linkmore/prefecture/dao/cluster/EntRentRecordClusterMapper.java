package cn.linkmore.prefecture.dao.cluster;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.EntRentRecord;
/**
 * 长租用户会用记录--读
 * @author   cl
 * @Version  v2.0
 */
@Mapper
public interface EntRentRecordClusterMapper {

	/**
	 * @Description  查询
	 * @Author   cl
	 * @Version  v2.0
	 */
    EntRentRecord findById(Long id);
    /**
	 * @Description  查询
	 * @Author   cl 
	 * @Version  v2.0
	 */
    EntRentRecord findByUser(Long userId);
    
    /**
	 * @Description  查询
	 * @Author   cl 
	 * @Version  v2.0
	 */
    Integer findUsingRecord(Map<String, Object> param);


}