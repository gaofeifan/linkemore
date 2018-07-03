package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;

/**
 * 企业合同
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EnterpriseDealService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqEnterpriseDeal deal);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqEnterpriseDeal deal);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean check(String property, String value, Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Tree> findTree();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> map();

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId, Integer isCreate);

}
