package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.OrderOperateLog;
@Mapper
public interface OrderOperateLogMasterMapper {
    int delete(List<Long> ids);

    int save(OrderOperateLog record);

    int update(OrderOperateLog record);
}