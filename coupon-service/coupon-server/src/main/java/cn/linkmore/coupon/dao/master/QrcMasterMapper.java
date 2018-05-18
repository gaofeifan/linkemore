package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Qrc;
@Mapper
public interface QrcMasterMapper {
    int delete(Long id);

    int save(Qrc record);

    int update(Qrc record);
}