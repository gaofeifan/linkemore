package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.TargetMounth;
@Mapper
public interface TargetMonthMasterMapper {
    int delete(Long id);

    int save(TargetMounth record);

    int update(TargetMounth record);

	void batchSave(List<TargetMounth> npmtList);

	void batchUpdate(List<TargetMounth> pmtList);
}