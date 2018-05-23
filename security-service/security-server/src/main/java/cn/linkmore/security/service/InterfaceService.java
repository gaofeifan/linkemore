package cn.linkmore.security.service;

import java.util.List;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Interface;
import cn.linkmore.security.request.ReqCheck;

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
	void save(Interface inter);

	/**
	 * 更新
	 * 
	 * @param inter
	 * @return
	 */
	Interface update(Interface inter);

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
	Integer check(ReqCheck reqCheck);
	/**
	 * 查询树
	 * @return
	 */ 
	Tree findTree();
}
