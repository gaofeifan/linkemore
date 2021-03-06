package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.request.ReqPreDetails;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.response.ResOwnerStallDetails;
import cn.linkmore.enterprise.response.ResOwnerStallReportForms;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.enterprise.response.ResStaffOwnerUseStall;
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
	/**
	 * 根据当前用户id和当前车位id查询是否有使用记录
	 * @param id
	 * @param stallId
	 * @return
	 */
	EntRentedRecord findByUserIdAndStallId(Map<String, Long> param);
	/**
	 * 查找用户所有使用记录
	 * @param id
	 * @return
	 */
	List<EntRentedRecord> findAllByUser(Long id);
	
	List<EntRentedRecord> findLastByStallIds(List<Long> stalls);
	List<EntRentedRecord> findParkingRecord(@Param("pageNo")Integer pageNo,@Param("stallId") Long stallId);

	/**
	 * @Description  查询被授权用户使用车位总次数
	 * @Author   jiaohanbin 
	 * @Version  v2.0
	 */
	Integer findUseCount(Map<String, Long> param);
	
	/**
	 * 根据当前用户id和当前车位id查询最近使用记录，不包含失败记录
	 * @param id
	 * @param stallId
	 * @return
	 */
	EntRentedRecord findLastByUserIdAndStallId(Map<String, Long> param);
	
	/**
	 * @Description  查询用户当月使用车位总次数
	 * @Author   jiaohanbin 
	 * @Version  v2.0
	 */
	Integer findUseCountByMonth(Map<String, Long> param);
	
	List<ResStaffOwnerUseStall> findPreUseNumber(ReqStaffPreOwnerStall reqStaffPreOwnerStall);
	
	ResOwnerStallDetails findPreDetails(ReqPreDetails reqPreDetails);
	ResOwnerStallReportForms findOwnerStallReportForms(ReqPreDetails details);
	
}