package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.TimingSchedule;

/**
 * 定时调度mapper(查询)
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Mapper
public interface TimingScheduleClusterMapper {
   
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    TimingSchedule findById(Long id);

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<TimingSchedule> list(TimingSchedule timingSchedule);

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
	List<TimingSchedule> findPage(Map<String, Object> param);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);


}