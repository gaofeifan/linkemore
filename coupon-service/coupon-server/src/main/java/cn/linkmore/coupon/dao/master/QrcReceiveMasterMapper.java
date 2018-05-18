package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.QrcReceive;
@Mapper
public interface QrcReceiveMasterMapper {
    int delete(Long id);

    int save(QrcReceive record);

    int update(QrcReceive record);
}