package cn.linkmore.coupon.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendRecord;
import cn.linkmore.coupon.entity.SendUser;
@Mapper
public interface SendRecordMasterMapper {
	int delete(Long id);

    int save(SendRecord record);

    int update(SendRecord record);

	void insertBatch(List<SendUser> sendUserList);
}