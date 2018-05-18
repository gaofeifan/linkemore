package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Subject;
@Mapper
public interface SubjectClusterMapper {

    Subject findById(Long id);
}