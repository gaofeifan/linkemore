package cn.linkmore.coupon.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateCondition;
@Mapper
public interface TemplateConditionMasterMapper {
	int delete(Long id);

	int save(TemplateCondition record);

	int update(TemplateCondition record);

	int setDefault(Map<String, Object> param);
}