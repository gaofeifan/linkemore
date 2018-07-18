package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntBrandAd;
import cn.linkmore.enterprise.request.ReqCheck;

public interface EntBrandAdService {

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 根据条件查询
	 * 
	 * @param param
	 * @return
	 */
	List<EntBrandAd> findList(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(EntBrandAd record);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(EntBrandAd record);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(Long id);

	/**
	 * 检验属性存在
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);

}
