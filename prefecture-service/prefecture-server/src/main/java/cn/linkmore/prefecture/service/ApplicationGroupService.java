package cn.linkmore.prefecture.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqApplicationGroup;

/**
 * 应用程序--接口
 * 
 * @author GFF
 * @Date 2018年6月23日
 * @Version v2.0
 */
public interface ApplicationGroupService {

	ViewPage findPage(ViewPageable pageable);

	void add(ReqApplicationGroup requestBean);

	void start(List<Long> ids);

	void down(List<Long> ids);

	Integer check(String property, String value, Long id);

}
