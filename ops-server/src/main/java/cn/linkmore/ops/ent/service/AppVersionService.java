package cn.linkmore.ops.ent.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.request.ReqAppVersion;

/**
 * @author   GFF
 * @Date     2018年9月19日
 * @Version  v2.0
 */
public interface AppVersionService {

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
	void save(ReqAppVersion auth);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqAppVersion auth);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	Boolean check(String property, String value, Long id);

}
