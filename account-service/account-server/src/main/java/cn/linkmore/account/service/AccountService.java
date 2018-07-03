package cn.linkmore.account.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 账户--接口
 * @author   GFF
 * @Date     2018年6月23日
 * @Version  v2.0
 */
public interface AccountService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
