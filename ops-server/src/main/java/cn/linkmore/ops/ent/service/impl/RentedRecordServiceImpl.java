package cn.linkmore.ops.ent.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.ops.ent.service.RentedRecordService;
import cn.linkmore.prefecture.client.OpsRentedRecordClient;

/**
 * 长租用户使用记录--接口实现
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Service
public class RentedRecordServiceImpl implements RentedRecordService {

	@Resource
	private OpsRentedRecordClient rentedRecordClient;

	@Override
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		return this.rentedRecordClient.findList(pageable);
	}

	@Override
	public List<ResRentedRecord> exportList(ReqRentedRecord bean) {
	    return this.rentedRecordClient.exportList(bean);
	}
}
