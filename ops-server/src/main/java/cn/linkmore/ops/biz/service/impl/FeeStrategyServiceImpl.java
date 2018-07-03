package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.FeeStrategyService;
import cn.linkmore.prefecture.client.OpsStrategyBaseClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategyBase;
import cn.linkmore.prefecture.response.ResStrategyBase;

@Service
public class FeeStrategyServiceImpl implements FeeStrategyService {

	@Autowired
	private OpsStrategyBaseClient strategyBaseClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.strategyBaseClient.list(pageable);
	}

	@Override
	public int save(ReqStrategyBase reqStrategyBase) {
		return this.strategyBaseClient.save(reqStrategyBase);
	}

	@Override
	public int update(ReqStrategyBase reqStrategyBase) {
		return this.strategyBaseClient.update(reqStrategyBase);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.strategyBaseClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.strategyBaseClient.check(reqCheck);
	}

	@Override
	public List<ResStrategyBase> findList() {
		return this.strategyBaseClient.findList();
	}

}
