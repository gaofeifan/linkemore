package cn.linkmore.ops.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.LockOperateLogService;
import cn.linkmore.prefecture.client.LockOperateLogClient;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;

/**
 * Service实现类 - 车位升降
 * 
 * @author zhaoyufei
 *
 */
@Service
public class LockOperateLogServiceImpl implements LockOperateLogService {
	@Autowired
	private LockOperateLogClient operateLogClient;

	/*
	 * 列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.operateLogClient.list(pageable);
	}

	@Override
	public List<ResLockOperateLog> findListById(Long id) {
		return this.operateLogClient.detail(id);
	}

	/*
	 * 导出
	 */
	@Override
	public List<ResLockOperateLog> exportList(ReqLockOperateLogExcel excelbean) {
		return this.operateLogClient.export(excelbean);
	}

}
