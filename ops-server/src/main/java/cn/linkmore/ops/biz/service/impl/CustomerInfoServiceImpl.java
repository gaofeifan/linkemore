package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.CustomerInfoClient;
import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.CustomerInfoService;
/**
 * 用户数据录入--接口实现
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Resource
	private CustomerInfoClient customerInfoClient;

	@Override
	public ViewPage findList(ViewPageable pageable) {
		ViewPage page = this.customerInfoClient.list(pageable);
		return page;
	}

	@Override
	public List<ResCustomerInfoExport> getExportList(ReqCustomerInfoExport pageable) {
		List<ResCustomerInfoExport> list = this.customerInfoClient.getExportList(pageable);
		return list;
	}
	
	
	
	
}
