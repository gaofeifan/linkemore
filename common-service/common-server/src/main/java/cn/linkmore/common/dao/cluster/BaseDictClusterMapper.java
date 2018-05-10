package cn.linkmore.common.dao.cluster;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.linkmore.common.entity.BaseDict;

@Mapper
public interface BaseDictClusterMapper {
    BaseDict selectByPrimaryKey(Long id);

	List<BaseDict> selectByCodes(@Param("codes")List<String> codes);
}