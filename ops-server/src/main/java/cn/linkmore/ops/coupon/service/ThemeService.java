package cn.linkmore.ops.coupon.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTheme;
import cn.linkmore.coupon.response.ResEnterpriseBean;

public interface ThemeService {
	/**
	 * 检查属性可用性
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
	/**
	 * 删除信息
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 更新信息
	 * @param record
	 * @return
	 */
	int update(ReqTheme record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqTheme record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 查询企业
	 * @return
	 */
	List<ResEnterpriseBean> findEnterpriseList(); 
}
