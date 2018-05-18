package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Template;
@Mapper
public interface TemplateClusterMapper {
	/**
	 * 根据主键查询优惠券套餐
	 * @param id
	 * @return
	 */
    Template findById(Long id);

}