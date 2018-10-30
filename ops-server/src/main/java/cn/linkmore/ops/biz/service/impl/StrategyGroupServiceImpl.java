package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StrategyGroupService;
import cn.linkmore.prefecture.client.OpsStrategyGroupClient;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;

@Service
public class StrategyGroupServiceImpl implements StrategyGroupService {

	
	@Autowired
	private OpsStrategyGroupClient opsStrategyGroupClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsStrategyGroupClient.list(pageable);
	}

	@Override
	public int save(ReqStrategyGroup record) {
		return opsStrategyGroupClient.save(record);
	}

	@Override
	public int update(ReqStrategyGroup record) {
		return opsStrategyGroupClient.update(record);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return opsStrategyGroupClient.updateStatus(map);
	}
	@Override
	public int deleteStall(List<Long> ids) {
		return opsStrategyGroupClient.deleteStall(ids);
	}
	@Override
	public int addStall(ReqStrategyGroupDetail reqStrategyGroupDetail) {
		return opsStrategyGroupClient.addStall(reqStrategyGroupDetail);
	}
	
	
	@Override
	public int delete(List<Long> ids) {
		return opsStrategyGroupClient.delete(ids);
	}
	
	@Override
	public ResStrategyGroup selectByPrimaryKey(Long id) {
		return opsStrategyGroupClient.selectByPrimaryKey(id);
	}

	@Override
	public Tree findTree(@RequestBody Map<String, Object> param) {
		return opsStrategyGroupClient.findTree(param);
	}

	@Override
	public List<ResStall> findAreaStall(Map<String, Object> param) {
		return opsStrategyGroupClient.findAreaStall(param);
	}

	@Override
	public List<ResStrategyGroupArea> selectStallByPrimaryKey(Long id) {
		return opsStrategyGroupClient.selectStallByPrimaryKey(id);
	}

	@Override
	public Long existsStall(Map<String, Object> map) {
		return opsStrategyGroupClient.existsStall(map);
	}

	@Override
	public List<ResStrategyGroup> findList(Map<String, Object> param) {
		return opsStrategyGroupClient.findList();
	}
	
}
