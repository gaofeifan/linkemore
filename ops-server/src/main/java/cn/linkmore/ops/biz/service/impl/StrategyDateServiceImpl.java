package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StrategyDateService;
import cn.linkmore.prefecture.client.OpsStrategyDateClient;
import cn.linkmore.prefecture.request.ReqStrategyDate;
import cn.linkmore.prefecture.response.ResStrategyDate;

@Service
public class StrategyDateServiceImpl implements StrategyDateService {

	
	@Autowired
	private OpsStrategyDateClient opsStrategyDateClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsStrategyDateClient.list(pageable);
	}

	@Override
	public List<ResStrategyDate> findList(Map<String, Object> map) {
		return opsStrategyDateClient.findList(map);
	}

	@Override
	public int save(ReqStrategyDate record) {
		return opsStrategyDateClient.save(record);
	}

	@Override
	public int update(ReqStrategyDate record) {
		return opsStrategyDateClient.update(record);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return opsStrategyDateClient.updateStatus(map);
	}
	@Override
	public int delete(List<Long> ids) {
		return opsStrategyDateClient.delete(ids);
	}
	@Override
	public ResStrategyDate selectByPrimaryKey(Long id) {
		return opsStrategyDateClient.selectByPrimaryKey(id);
	}

	@Override
	public int updatePublic(Map<String, Object> map) {
		return opsStrategyDateClient.updatePublic(map);
		
	}
}
