package cn.linkmore.ops.security.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqDictGroup;

/**
 * Service接口 - 字典分类信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface DictGroupService {
	/**
	 * 分页
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 校验字段
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int update(ReqDictGroup record);
	/**
	 * 新增
	 * @param record
	 */
	int save(ReqDictGroup record);
}
