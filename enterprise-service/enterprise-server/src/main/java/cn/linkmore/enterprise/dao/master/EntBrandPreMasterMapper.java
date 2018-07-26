package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandPre;
@Mapper
public interface EntBrandPreMasterMapper {
    int delete(Long id);

    int save(EntBrandPre record);

    int update(EntBrandPre record);

	int deleteByIds(List<Long> ids);

	int startOrStop(Map<String, Object> param);
}