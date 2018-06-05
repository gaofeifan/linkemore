package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.TargetDay;
@Mapper
public interface TargetDayMasterMapper {
    int delete(Long id);

    int save(TargetDay record);

    int update(TargetDay record);
}