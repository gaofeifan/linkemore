package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;
@Mapper
public interface TemplateConditionClusterMapper {
	
	ResTemplateCondition findById(Long id);
	
	/**
	 * 查询templateId集合对应的条件
	 * @param templateIds
	 * @return
	 */
	List<TemplateCondition> findTemplateList(List<Long> list);
	/**
	 * 根据templateId 查询所有的条件列表
	 * @param templateId
	 * @return
	 */
	List<ResTemplateCondition> findConditionList(Long templateId);

	Integer count(Map<String, Object> param);

	List<ResTemplateCondition> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param); 
}