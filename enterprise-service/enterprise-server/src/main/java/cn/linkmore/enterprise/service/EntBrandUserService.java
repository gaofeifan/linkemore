package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandUser;
import cn.linkmore.enterprise.response.ResBrandUser;

public interface EntBrandUserService {

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
	List<ResBrandUser> findList(Map<String, Object> param);

	/**
	 * 保存
	 * 
	 * @param record
	 */
	int save(ReqEntBrandUser record);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(ReqEntBrandUser record);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 检验属性存在
	 * @param record
	 * @return
	 */
	Integer check(ReqEntBrandUser record);

	int insertBatch(List<ReqEntBrandUser> reqUserList);

	Integer checkExist(Long userId, String plateNo);

}
