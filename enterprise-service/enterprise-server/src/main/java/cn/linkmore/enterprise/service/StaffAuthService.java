package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;

/**
 * 
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
public interface StaffAuthService {

	/**
	 * @Description  绑定用户权限
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void bind(ReqStaffAuthBind staff);

	/**
	 * @return 
	 * @Description  查询树桩数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Tree> tree();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> resouce(Long staffId);

}
