package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.StallFaultFeedBack;
@Mapper
public interface StallFaultFeedBackClusterMapper {

    StallFaultFeedBack selectByPrimaryKey(Long id);

}