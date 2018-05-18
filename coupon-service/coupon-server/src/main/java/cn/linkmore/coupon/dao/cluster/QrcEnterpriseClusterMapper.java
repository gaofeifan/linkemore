package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.QrcEnterprise;
@Mapper
public interface QrcEnterpriseClusterMapper {

    QrcEnterprise findById(Long id);
}