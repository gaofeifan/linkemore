package cn.linkmore.enterprise.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface EntVipUserService {

	/**
	 * 保存企业vip用户
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int saveEntVipUser(Long entId, Long entPreId, String mobile, String realname, String plate);

	/**
	 * 删除企业vip用户
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int deleteEntVipUser(Long id);

	/**
	 * 修改企业vip用户
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int updateEntVipUser(Long id, String mobile, String realname, String plate);
	
	
	/**
	 * @Description  分页查询
	 * @Author   cl
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
