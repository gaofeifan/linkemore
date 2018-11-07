package cn.linkmore.prefecture.dao.master;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.PrefectureStrategy;

public interface PrefectureStrategyMasterMapper {
	
    int deleteByPrimaryKey(Long id);
    
    int delete(List<Long> ids);
    
    int insert(PrefectureStrategy record);

    int insertSelective(PrefectureStrategy record);

    int updateByPrimaryKeySelective(PrefectureStrategy record);

    int updateByPrimaryKey(PrefectureStrategy record);
    
    int updateStatus(Map<String, Object> map);
}