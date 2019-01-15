package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.StallFaultFeedBack;
@Mapper
public interface StallFaultFeedBackMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StallFaultFeedBack record);

    int insertSelective(StallFaultFeedBack record);

    int updateByPrimaryKeySelective(StallFaultFeedBack record);

    int updateByPrimaryKey(StallFaultFeedBack record);
}