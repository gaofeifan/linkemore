package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StrategyTimeService;
import cn.linkmore.prefecture.client.OpsStrategyTimeClient;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTime;

@Service
public class StrategyTimeServiceImpl implements StrategyTimeService {

	
	@Autowired
	private OpsStrategyTimeClient opsStrategyTimeClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsStrategyTimeClient.list(pageable);
	}

	@Override
	public List<ResStrategyTime> findList() {
		return opsStrategyTimeClient.findList();
	}

	@Override
	public int save(ReqStrategyTime record) {
		return opsStrategyTimeClient.save(record);
	}

	@Override
	public int update(ReqStrategyTime record) {
		return opsStrategyTimeClient.update(record);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return opsStrategyTimeClient.updateStatus(map);
	}
	@Override
	public int delete(List<Long> ids) {
		return opsStrategyTimeClient.delete(ids);
	}
	
	@Override
	public ResStrategyTime selectByPrimaryKey(Long id) {
		return opsStrategyTimeClient.selectByPrimaryKey(id);
	}
}
