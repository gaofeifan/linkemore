package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntVipUser;
import cn.linkmore.enterprise.entity.EntVipUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.request.ReqVipUser;

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
	
	/**
	 * @Description  新增
	 * @Author   cl
	 * @Version  v2.0
	 */
	void save(ReqAddEntVipUser reqAddEntVipUser );

	/**
	 * @Description  更新
	 * @Author   cl 
	 * @Version  v2.0
	 */
	void update(ReqAddEntVipUser reqAddEntVipUser);

	/**
	 * @Description  删除
	 * @Author   cl 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

}
