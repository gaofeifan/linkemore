package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.security.response.ResPerson;

/**
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EnterpriseUserService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable, ResPerson person);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqEnterpriseUser enterpriseUser);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResEnterpriseUser getEnterpriseUser(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveAll(List<ReqEnterpriseUser> entAll);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResEnterpriseUser> findExcel(Map<String, Object> param);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(ResEnterpriseUser enterpriseUser);

}
