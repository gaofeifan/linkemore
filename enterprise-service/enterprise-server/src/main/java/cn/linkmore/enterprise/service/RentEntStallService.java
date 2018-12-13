package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.RentEntStall;
import cn.linkmore.enterprise.request.ReqRentEntStall;
/**
 * 企业长租车位
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
public interface RentEntStallService {

    RentEntStall findById(Long rentEntId);

	void saveBatch(List<ReqRentEntStall> list);

	ViewPage stallListCompany(ViewPageable pageable);

	List<RentEntStall> stallListCompany(Long companyid);
	
	void deleteStall(List<Long> ids);

	List<Long> occuyStallList(Map<String, Object> param);

	void deleteByCompanyIds(List<Long> ids);
}