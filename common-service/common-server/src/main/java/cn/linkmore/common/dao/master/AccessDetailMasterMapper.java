package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AccessDetail;
@Mapper
public interface AccessDetailMasterMapper {
    int deleteById(Integer id);

    int insert(AccessDetail record);

    int insertSelective(AccessDetail record);

    int updateByIdSelective(AccessDetail record);

    int updateById(AccessDetail record);
}