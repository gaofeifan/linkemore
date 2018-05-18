package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.SendUser;
@Mapper
public interface SendUserMasterMapper {
	int delete(Long id);

    int save(SendUser record);

    int update(SendUser record);
}