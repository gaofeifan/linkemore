package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Prefecture;

@Mapper
public interface PrefectureMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Prefecture record);

    int updateByPrimaryKey(Prefecture record);
    
}