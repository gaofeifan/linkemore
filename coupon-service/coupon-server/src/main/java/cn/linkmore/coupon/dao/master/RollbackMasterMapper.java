package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Rollback;
@Mapper
public interface RollbackMasterMapper {
    int delete(Long id);

    int save(Rollback record);

    int update(Rollback record);
}