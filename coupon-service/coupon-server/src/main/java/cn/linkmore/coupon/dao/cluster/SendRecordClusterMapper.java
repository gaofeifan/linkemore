package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendRecord;
@Mapper
public interface SendRecordClusterMapper {
	int delete(Long id);

    int save(SendRecord record);

    SendRecord findById(Long id);

    int update(SendRecord record);
}