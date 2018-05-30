package cn.linkmore.common.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.TimingSchedule;
import cn.linkmore.common.request.ReqTimingSchedule;

/**
 * 定时调度mapper(写)
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Mapper
public interface TimingScheduleMasterMapper {
	
    /**
     * @Description  根据id删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(TimingSchedule record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(TimingSchedule record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(TimingSchedule record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(TimingSchedule record);

	/**
	 * @Description  批量删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteIds(List<Long> ids);

	/**
	 * @Description  新增数据（请求bean）
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqTimingSchedule timingSchedule);

	/**
	 * @Description  更新(通过请求bean)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqTimingSchedule timingSchedule);

}