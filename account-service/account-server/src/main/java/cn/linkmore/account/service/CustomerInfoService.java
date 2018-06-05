package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 用户数据录入--接口
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public interface CustomerInfoService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(ViewPageable pageable);

	/**
	 * @Description  根据条件查询导出数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResCustomerInfoExport> getExportList(ReqCustomerInfoExport pageable);

}
