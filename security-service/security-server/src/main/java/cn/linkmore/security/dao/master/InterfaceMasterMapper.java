package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Interface;
/**
 * 接口
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface InterfaceMasterMapper {
    /**
	 * 新增
	 * @param record
	 * @return
	 */
    int save(Interface record);
    /**
	 * 更新
	 * @param record
	 * @return
	 */
    int update(Interface record);
    /**
	 * 批量删除
	 * @param id
	 * @return
	 */
	int delete(List<Long> ids);
}