package cn.linkmore.ops.security.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqClazz;

/**
 * Service接口 - 类信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface ClazzService {
	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * 
	 * @param reqClazz
	 */
	int save(ReqClazz reqClazz);

	/**
	 * 更新
	 * 
	 * @param reqClazz
	 * @return
	 */
	int update(ReqClazz reqClazz);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 校验字段
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
}
