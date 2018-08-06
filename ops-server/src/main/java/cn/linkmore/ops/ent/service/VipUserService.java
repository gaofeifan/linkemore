package cn.linkmore.ops.ent.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.request.ReqVipUser;

public interface VipUserService {
	
	/**
	 * @Description  分页查询
	 * @Author   cl 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);
	
	
	/**
	 * @Description  新增
	 * @Author  cl
	 * @Version  v2.0
	 */
	void save(ReqVipUser auth);

	/**
	 * @Description  更新
	 * @Author   cl 
	 * @Version  v2.0
	 */
	void update(ReqVipUser auth);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);
	
	

}
