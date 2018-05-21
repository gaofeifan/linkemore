package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.ChargeRecord;
@Mapper
public interface ChargeRecordClusterMapper {

    ChargeRecord findById(Integer id);

}