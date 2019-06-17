package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.response.ResStaffPreDetails;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.prefecture.response.ResStallType;
/**
 * dao 车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallClusterMapper {
	/**
	 * 查询主键对应的车位信息
	 * @param id 主健
	 * @return Stall车位信息
	 */
	Stall findById (Long id);
	/**
	 * 根据车区id查询车位总数
	 * @param preId 车区id
	 * @return int
	 */
	int findCountByPreId(Long preId);
	/**
	 * 根据状态查询车位列表
	 * @param status
	 * @return List<Stall>
	 */
	List<Stall> findByStatus(int status);
	/**
	 * 根据车位锁序列号查询车位信息
	 * @param lockSn
	 * @return
	 */
	Stall findByLockSn(String lockSn);
	/**
	 * 根据车位锁序列号查询车位信息
	 * @param lockSn
	 * @return
	 */
	Stall findByLockName(String name);
	
	/**
	 * 根据车位id查询正常状态下车位信息
	 * @param preId
	 * @return
	 */
	List<ResStall> findStallsByPreId(Long preId);
	
	
	
	/**
	 * 根据 商业场所，车场分区，临时小分区 取出车位信息
	 * @param param
	 * @return
	 */
	List<ResStall> findListByArea(Map<String, Object> param);
	
	
	/**
	 * 获取所有车区名称
	 * @param preId
	 * @return
	 */
	List<String> findAllAreaByPreId(Long preId);
	
	/**
	 * 查找所有的车位
	 * @return
	 */
	List<Stall> findAll();
	
	List<ResStall> findTreeList(List<Long> stallIds);
	
	int check(Map<String, Object> param);
	
	Integer count(Map<String, Object> param);
	
	List<Stall> findPage(Map<String, Object> param);
	
	List<ResStall> findList(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	
	List<ResStallOps> findListByParam(Map<String, Object> param);
	/**
	 * pengfei
	 * @param param
	 * @return
	 */
	List<ResStall> findPreStallList(Map<String, Object> param);
	
	List<ResStall> findFreeStallList(Map<String, Object> params);
	
	/**
	 * 查询分组内所有车位信息
	 */
	List<ResStall> findAllStallList(Map<String, Object> params);
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStall> findStallsByPreIds(Map<String, Object> map);
	
	Stall findByLockNameAndPreId(@Param("stallName")String stallName,@Param("preId") Long preId);
	
	ResStaffPreDetails findPreStallDetails(@Param("preId")Long preId, @Param("floor")String floor);
	
	List<ResStallType> findStallType(List<Long> preIds);
	
}