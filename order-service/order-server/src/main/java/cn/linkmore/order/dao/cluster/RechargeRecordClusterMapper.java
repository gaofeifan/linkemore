package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.RechargeRecord;
@Mapper
public interface RechargeRecordClusterMapper {

    RechargeRecord findById(Integer id);

}