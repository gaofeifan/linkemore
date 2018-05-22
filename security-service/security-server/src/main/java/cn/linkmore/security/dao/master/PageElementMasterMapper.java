package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.PageElement;
/**
 * 页面元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PageElementMasterMapper {
	/**
	 * 新增
	 * @param record
	 * @return
	 */
    int save(PageElement record);
    /**
     * 更新
     * @param record
     * @return
     */
    int update(PageElement record);
    /**
     * 批量删除
     * @param ids
     * @return
     */
	int delete(List<Long> ids);
}