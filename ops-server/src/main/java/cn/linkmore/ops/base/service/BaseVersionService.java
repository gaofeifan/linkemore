package cn.linkmore.ops.base.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqBaseVersion;
import cn.linkmore.ops.request.ReqCheck;

/**
 * 版本管理
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
public interface BaseVersionService {

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqBaseVersion record);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqBaseVersion record);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean check(String property, String value, Long id);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  查询用户版本(分页)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findUserPage(ViewPageable pageable);


}
