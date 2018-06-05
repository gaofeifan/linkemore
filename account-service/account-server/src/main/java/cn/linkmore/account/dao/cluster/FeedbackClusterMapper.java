package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Feedback;
import cn.linkmore.account.response.ResFeedBack;

/**
 * 问题反馈mapper（读）
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Mapper
public interface FeedbackClusterMapper {
	
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    Feedback findById(Long id);

	/**
	 * @Description	查询总数  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResFeedBack> findPage(Map<String, Object> param);

	/**
	 * @Description  查询导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResFeedBack> findExportList(Map<String, Object> param);

}