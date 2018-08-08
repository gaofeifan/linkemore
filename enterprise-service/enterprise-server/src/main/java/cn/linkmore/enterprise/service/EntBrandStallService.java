package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandStall;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.response.ResBrandStall;

public interface EntBrandStallService {

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
	List<ResBrandStall> findList(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(ReqEntBrandStall record);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(ReqEntBrandStall record);

	/**
	 * 检验属性存在
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);

	int delete(List<Long> ids);

}
