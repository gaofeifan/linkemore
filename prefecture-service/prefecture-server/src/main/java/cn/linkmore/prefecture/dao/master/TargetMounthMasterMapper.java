package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.TargetMounth;
@Mapper
public interface TargetMounthMasterMapper {
    int delete(Long id);

    int save(TargetMounth record);

    int update(TargetMounth record);
}