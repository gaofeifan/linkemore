package cn.linkmore.ops.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.StallOperateLogService;
import cn.linkmore.prefecture.client.StallOperateLogClient;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;

/**
 * Service实现类 - 车位上下线
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StallOperateLogServiceImpl implements StallOperateLogService {
	@Autowired
	private StallOperateLogClient operateLogClient;

	/*
	 * 列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.operateLogClient.list(pageable);
	}

	@Override
	public List<ResStallOperateLog> findListById(Long id) {
		return this.operateLogClient.detail(id);
	}

	/*
	 * 导出
	 */
	@Override
	public List<ResStallOperateLog> exportList(ReqStallOperateLogExcel excelBean) {
		return this.operateLogClient.export(excelBean);
	}

}
