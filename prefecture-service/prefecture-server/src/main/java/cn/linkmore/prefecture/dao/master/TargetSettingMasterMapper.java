package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.TargetSetting;
@Mapper
public interface TargetSettingMasterMapper {
    int delete(Long id);

    int save(TargetSetting record);

    int update(TargetSetting record);

	int delete(List<Long> ids);
}