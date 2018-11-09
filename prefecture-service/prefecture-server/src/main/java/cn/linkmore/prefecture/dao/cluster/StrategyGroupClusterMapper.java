package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import cn.linkmore.prefecture.response.ResStrategyGroup;

public interface StrategyGroupClusterMapper {

    ResStrategyGroup selectByPrimaryKey(Long id);
    
    Integer count(Map<String, Object> param);
    
    List<ResStrategyGroup> findPage(Map<String, Object> param);
    
    List<ResStrategyGroup> findList(Map<String, Object> param);
    
    /**
     * 根据车区id查找车区分组列表
     * @param param
     * @return
     */
    List<ResStrategyGroup> findPreGroupList(Map<String, Object> param);
}