package cn.linkmore.security.dao.master;

import java.util.List;
/**
 * 字典分组
 * @author jiaohanbin
 * @version 2.0
 *
 */

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.security.entity.DictGroup;
@Mapper
public interface DictGroupMasterMapper {
    /**
	 * 新增
	 * @param record
	 * @return
	 */
    int save(DictGroup record);
    /**
	 * 更新
	 * @param record
	 * @return
	 */
    int update(DictGroup record);
    /**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
}