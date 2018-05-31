package cn.linkmore.ops.security.service;
import java.util.List;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqDict;
import cn.linkmore.ops.security.response.ResDict;

/**
 * Service接口 - 字典信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface DictService {
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 新增
	 * @param record
	 */
	int save(ReqDict record);
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int update(ReqDict record);
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 检查字段是否重复
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
	/**
	 * 字典树
	 * @return
	 */
	Tree findTree();
	/**
	 * 根据分组编号查询字典列表
	 * @param code
	 * @return
	 */
	List<ResDict> findByGroupCode(String code);
}
