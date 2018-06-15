package cn.linkmore.coupon.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendUser;
import cn.linkmore.coupon.response.ResSendUser;
@Mapper
public interface SendUserMasterMapper {
	int delete(Long id);

    int save(SendUser record);

    int update(SendUser record);

	void updateBatch(List<ResSendUser> list);

	void insertBatch(List<SendUser> sendUserList);
}