package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.linkmore.enterprise.entity.BaseDict;

@Mapper
public interface BaseDictMapper {
    BaseDict selectByPrimaryKey(Long id);

	List<BaseDict> selectByCodes(@Param("codes")List<String> codes);
}