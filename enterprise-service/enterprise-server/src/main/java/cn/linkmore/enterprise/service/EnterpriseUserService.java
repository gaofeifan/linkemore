package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EnterpriseUser;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;

public interface EnterpriseUserService {

	/**
	 * 批量添加
	 * 
	 * @param enterpriseUser
	 */
	void saveAll(List<ReqEnterpriseUser> all);

	/**
	 * 添加
	 * 
	 * @param enterpriseUser
	 */
	int save(ReqEnterpriseUser enterpriseUser);

	/**
	 * 删除
	 * 
	 * @param entUserId
	 */
	int delete(Long id);

	/**
	 * 修改
	 * 
	 * @param enterpriseUser
	 */
	int update(ReqEnterpriseUser enterpriseUser);

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	ResEnterpriseUser getEnterpriseUser(Long id);

	/**
	 * 分页
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable,Long personId);

	/**
	 * 查询导出数据
	 * 
	 * @param filterJson
	 * @return
	 */
	List<ResEnterpriseUser> findExcel(Map<String,Object>param);

}
