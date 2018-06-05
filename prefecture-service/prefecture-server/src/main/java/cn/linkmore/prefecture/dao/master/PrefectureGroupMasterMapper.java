package cn.linkmore.prefecture.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.PrefectureGroup;
@Mapper
public interface PrefectureGroupMasterMapper {
	
    int delete(Long id);

    int save(PrefectureGroup record);

    int update(PrefectureGroup record);

	int startOrDown(Map<String, Object> param);
}