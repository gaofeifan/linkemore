package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandAd;
@Mapper
public interface EntBrandAdMasterMapper {
    int delete(Long id);

    int save(EntBrandAd record);

    int update(EntBrandAd record);

	int deleteByIds(List<Long> ids);

	int startOrStop(Map<String, Object> param);
}