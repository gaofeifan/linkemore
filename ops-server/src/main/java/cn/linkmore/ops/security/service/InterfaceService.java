package cn.linkmore.ops.security.service;

import java.util.List;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqInterface;
import cn.linkmore.ops.security.response.ResInterface;

/**
 * Service接口 - 接口信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface InterfaceService {
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
	 * @param inter
	 */
	int save(ReqInterface inter);

	/**
	 * 更新
	 * 
	 * @param inter
	 * @return
	 */
	int update(ReqInterface inter);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	
	/**
	 * 异步检查
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
	 * 查询所有接口
	 * @return
	 */
	List<ResInterface> findAll();
}
