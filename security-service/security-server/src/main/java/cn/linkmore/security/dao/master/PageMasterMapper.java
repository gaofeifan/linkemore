package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Page;
/**
 * 页面
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PageMasterMapper {
	/**
	 * 新增
	 * @param record
	 * @return
	 */
    int save(Page record);
    /**
     * 更新
     * @param record
     * @return
     */
    int update(Page record);
    /**
     * 批量删除
     * @param ids
     * @return
     */
	int delete(List<Long> ids);
}