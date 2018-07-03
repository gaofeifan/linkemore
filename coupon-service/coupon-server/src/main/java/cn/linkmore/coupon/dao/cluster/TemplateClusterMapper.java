package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.response.ResTemplate;
@Mapper
public interface TemplateClusterMapper {
	/**
	 * 根据主键查询优惠券套餐
	 * @param id
	 * @return
	 */
    ResTemplate findById(Long id);
    /**
     * 计数
     * @param param
     * @return
     */
	Integer count(Map<String, Object> param);
	/**
	 * 分页
	 * @param param
	 * @return
	 */
	List<ResTemplate> findPage(Map<String, Object> param);
	/**
	 * 校验字段是否重复
	 * @param param
	 * @return
	 */
	Integer check(Map<String, Object> param);
	/**
	 * 任务列表
	 * @return
	 */
	List<ResTemplate> findTaskList();
	/**
	 * 查找专题优惠券列表
	 * @return
	 */
	List<ResTemplate> findSubjectCouponList();
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResTemplate> selectByEnterpriseId(Long entId);

}