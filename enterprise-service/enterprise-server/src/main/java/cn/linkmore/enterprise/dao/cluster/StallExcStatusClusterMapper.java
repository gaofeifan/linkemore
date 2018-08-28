package cn.linkmore.enterprise.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.StallExcStatus;

/**
 * 车位异常状态
 * @author   GFF
 * @Date     2018年8月6日
 * @Version  v2.0
 */
@Mapper
public interface StallExcStatusClusterMapper {

	/**
	 * @Description  根据车位id查询异常原因状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	StallExcStatus findExcStatus(Long stallId);

	/**
	 * @Description  根据id批量查询车位异常原因状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<StallExcStatus> findExcStatusList(List<Long> stallIds);

	/**
	 * @Description  查询所有
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<StallExcStatus> findAll();


}
