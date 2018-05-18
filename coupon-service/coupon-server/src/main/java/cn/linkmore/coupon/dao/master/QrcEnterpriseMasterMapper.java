package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.QrcEnterprise;
@Mapper
public interface QrcEnterpriseMasterMapper {
    int delete(Long id);

    int save(QrcEnterprise record);

    int update(QrcEnterprise record);
}