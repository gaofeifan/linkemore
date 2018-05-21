package cn.linkmore.coupon.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateCondition;
@Mapper
public interface TemplateConditionClusterMapper {
	
	TemplateCondition findById(Long id);
	
	/**
	 * 查询templateId集合对应的条件
	 * @param templateIds
	 * @return
	 */
	List<TemplateCondition> findTemplateList(List<Long> list); 
}