package cn.linkmore.ops.account.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 账户
 * @author   GFF
 * @Date     2018年6月23日
 * @Version  v2.0
 */
public interface AccountService {

	ViewPage findPage(ViewPageable pageable);

}
