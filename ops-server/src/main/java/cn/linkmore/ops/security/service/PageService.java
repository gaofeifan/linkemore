package cn.linkmore.ops.security.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqPage;

/**
 * Service接口 - 页面信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface PageService {

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * @param page
	 */
	int save(ReqPage page);

	/**
	 * 更新
	 * @param page
	 * @return
	 */
	int update(ReqPage page);

	/**
	 * 记录检验
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

}
