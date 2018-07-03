package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendRecord;
import cn.linkmore.coupon.response.ResSendRecord;
@Mapper
public interface SendRecordClusterMapper {

    ResSendRecord findById(Long id);

	List<ResSendRecord> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<SendRecord> findTaskList();
}