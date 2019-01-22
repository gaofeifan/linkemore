package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.response.ResRentedRecord;
/**
 * 长租用户会用记录--读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentedRecordClusterMapper {

	/**
	 * @Description  查询
	 * @Author   cl
	 * @Version  v2.0
	 */
    EntRentedRecord findById(Long id);
    /**
	 * @Description  查询
	 * @Author   cl 
	 * @Version  v2.0
	 */
    EntRentedRecord findByUser(Long userId);
    
    /**
	 * @Description  查询
	 * @Author   cl 
	 * @Version  v2.0
	 */
    Integer findUsingRecord(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntRentedRecord> findPage(Map<String, Object> param);
	
	/**
	 * @Description  根据车位id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	EntRentedRecord findByStallId(Long id);
	
	List<ResEntRentedRecord> findLastPlateNumberByPreId(Long preId);
	
	List<ResRentedRecord> exportList(ReqRentedRecord bean);

}