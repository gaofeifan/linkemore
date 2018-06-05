package cn.linkmore.ops.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.TargetMonthService;
import cn.linkmore.prefecture.client.TargetMonthClient;

/**
 * Service实现类 - 车区目标
 * @author liwenlong
 *
 */
@Service
public class TargetMonthServiceImpl implements TargetMonthService {
	@Autowired
	private TargetMonthClient targetMonthClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.targetMonthClient.list(pageable);
	}

	@Override
	public Tree findTree() {
		return this.targetMonthClient.tree();
	}
}
