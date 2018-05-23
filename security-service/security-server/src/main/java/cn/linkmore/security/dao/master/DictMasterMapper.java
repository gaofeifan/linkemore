package cn.linkmore.security.dao.master;

import java.util.List;

import cn.linkmore.security.entity.Dict;

public interface DictMasterMapper {
    /**
	 * 新增
	 * @param record
	 * @return
	 */
    int save(Dict record);
    /**
	 * 更新
	 * @param record
	 * @return
	 */
    int update(Dict record);
    /**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
}