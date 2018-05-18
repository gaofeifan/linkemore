package cn.linkmore.coupon.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateCondition;
@Mapper
public interface TemplateConditionClusterMapper {
	
	TemplateCondition findById(Long id);
	
	List<TemplateCondition> findConditionList(List<Long> ids);
}