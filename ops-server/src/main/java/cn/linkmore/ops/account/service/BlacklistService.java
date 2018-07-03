package cn.linkmore.ops.account.service;

import java.util.List;

import cn.linkmore.account.response.ResUserBlacklist;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 权限管理--接口
 * @author   GFF
 * @Date     2018年6月20日
 * @Version  v2.0
 */
public interface BlacklistService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean status();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewMsg open();

	/**
	 * @return 
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewMsg close();

	/**
	 * @return 
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewMsg enable(List<Long> list);

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUserBlacklist> findList();

}
