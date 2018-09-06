package cn.linkmore.ops.ent.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;

/**
 * 长租用户
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
public interface RentUserService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(HttpServletRequest request, ViewPageable pageable);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqRentUser auth);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqRentUser auth);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	/**
	 * @return 
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean check(ReqCheck reqCheck);

}
