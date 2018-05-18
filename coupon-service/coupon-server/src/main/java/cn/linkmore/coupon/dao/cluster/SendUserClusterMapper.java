package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendUser;
@Mapper
public interface SendUserClusterMapper {

    SendUser findById(Long id);

}