package cn.linkmore.security.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Page;
import cn.linkmore.security.request.ReqCheck;

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
	void save(Page page);

	/**
	 * 更新
	 * @param page
	 * @return
	 */
	Page update(Page page);

	/**
	 * 记录检验
	 * @param property
	 * @param value
	 * @param id
	 * @return
	 */
	Integer check(ReqCheck reqCheck);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids); 

}
