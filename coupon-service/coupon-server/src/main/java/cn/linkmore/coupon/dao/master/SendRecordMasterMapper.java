package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendRecord;
@Mapper
public interface SendRecordMasterMapper {
	int delete(Long id);

    int save(SendRecord record);

    int update(SendRecord record);
}