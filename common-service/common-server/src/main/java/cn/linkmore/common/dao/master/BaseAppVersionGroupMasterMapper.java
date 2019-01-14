package cn.linkmore.common.dao.master;

import java.util.List;

import cn.linkmore.common.entity.BaseAppVersionGroup;

public interface BaseAppVersionGroupMasterMapper {

    int deleteByIds(List<Long> ids);
    
    int insert(BaseAppVersionGroup record);

}