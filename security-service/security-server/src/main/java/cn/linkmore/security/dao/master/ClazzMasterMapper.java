package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Clazz;
/**
 * 类
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface ClazzMasterMapper {
    /**
     * 新增
     * @param record
     * @return
     */
    int save(Clazz record);
    /**
     * 更新
     * @param record
     * @return
     */
    int update(Clazz record);
    /**
     * 批量删除
     * @param ids
     * @return
     */
	int delete(List<Long> ids);
}