package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.TargetSetting;
import cn.linkmore.prefecture.response.ResTargetSetting;
@Mapper
public interface TargetSettingClusterMapper {

    TargetSetting findById(Long id);
    
    Integer count(Map<String, Object> param);

	List<ResTargetSetting> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

}