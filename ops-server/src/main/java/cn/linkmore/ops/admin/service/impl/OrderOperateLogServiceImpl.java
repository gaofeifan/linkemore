package cn.linkmore.ops.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.OrderOperateLogService;
import cn.linkmore.prefecture.client.OrderOperateLogClient;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
/**
 * Service实现类 - 车位订单操作记录
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class OrderOperateLogServiceImpl implements OrderOperateLogService {
	@Autowired
	private OrderOperateLogClient operateLogClient;
	
	/*
	 * 列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.operateLogClient.list(pageable);
	}
	
	@Override
	public List<ResOrderOperateLogEntity> findListById(Long id) {
		return this.operateLogClient.detail(id);
	}
	
	/*
	 * 导出
	 */
	@Override
	public List<ResOrderOperateLogEntity> exportList(
			ReqOrderOperateLogExcel excelBean) {
		return this.operateLogClient.export(excelBean);
	}

}
