package cn.linkmore.coupon.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Subject;
@Mapper
public interface SubjectMasterMapper {
	int delete(Long id);

    int save(Subject record);

    int update(Subject record);

	int startOrStop(Map<String, Object> param);
}