package cn.linkmore.ops.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqPageElement;
import cn.linkmore.security.response.ResAuthElement;

/**
 * Service接口 - 页面元素信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface PageElementService {
	
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqPageElement reqPageElemet);
	
	/**
	 * 更新信息
	 * @param record
	 * @return
	 */
	int update(ReqPageElement reqPageElemet);
	
	
	/**
	 * 删除信息
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 异步校验
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);

	/**
	 * 查询树
	 * @return
	 */ 
	Tree findTree();
	/**
	 * 集合
	 * @return
	 */
	Map<String, Object> map();
	
	/**
	 * 查询授权的元素集合
	 * @param ids
	 * @return
	 */
	List<ResAuthElement> findResAuthElementList();

}
