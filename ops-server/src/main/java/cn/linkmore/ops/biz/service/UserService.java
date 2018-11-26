package cn.linkmore.ops.biz.service;

import java.util.List;

import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 用户信息接口
 * @author   GFF
 * @Date     2018年5月26日
 * @Version  v2.0
 */
public interface UserService {

	/**
	 * 查询分页
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	List<ResPageUser> export(ViewPageable pageable);

	/**
	 * @Description  查询所有用户
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUser> findAll();
	/**
	 * 删除
	 * @param ids
	 */
	int delete(List<Long> ids);

}
