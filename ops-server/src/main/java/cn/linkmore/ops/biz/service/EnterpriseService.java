package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.security.request.ReqPerson;

/**
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface EnterpriseService {

	int save(ReqEnterprise record);

	int update(ReqEnterprise record);

	int delete(Long id);

	Boolean check(ReqCheck reqCheck);

	Boolean check(String property, String value, Long id);

	ViewPage findPage(ViewPageable pageable);

	List<ResEnterprise> selectAll();

	void setPassword(ReqPerson person);

	ResEnterprise find(Map<String,Object> param);

}
