package cn.linkmore.enterprise.service;

import java.util.List;

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

    RentEntStall selectByPrimaryKey(Long rentEntId);

	void saveBatch(List<ReqRentEntStall> list);

	ViewPage stallListCompany(ViewPageable pageable);
}