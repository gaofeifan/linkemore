package cn.linkmore.security.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Clazz;
import cn.linkmore.security.request.ReqCheck;

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
	 * @param clazz
	 */
	void save(Clazz clazz);

	/**
	 * 更新
	 * 
	 * @param clazz
	 * @return
	 */
	Clazz update(Clazz clazz);

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
	Integer check(ReqCheck reqCheck);
}
