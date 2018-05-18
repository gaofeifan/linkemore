package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Stall;
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
}