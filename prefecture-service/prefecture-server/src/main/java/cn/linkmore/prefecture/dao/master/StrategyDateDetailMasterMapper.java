package cn.linkmore.prefecture.dao.master;

import java.util.List;

import cn.linkmore.prefecture.entity.StrategyDateDetail;
import cn.linkmore.prefecture.request.ReqStrategyDate;

public interface StrategyDateDetailMasterMapper {
    int deleteByPrimaryKey(Long id);
    
    int delete(List<Long> ids);
    
    int insert(StrategyDateDetail record);

    int insertSelective(StrategyDateDetail record);

    int updateByPrimaryKeySelective(StrategyDateDetail record);

    int updateByPrimaryKey(StrategyDateDetail record);

	int update(ReqStrategyDate reqStrategyDate);
}