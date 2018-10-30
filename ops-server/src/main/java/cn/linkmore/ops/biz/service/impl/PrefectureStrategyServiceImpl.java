package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PrefectureStrategyService;
import cn.linkmore.prefecture.client.OpsPrefectureStrategyClient;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.response.ResStrategyFee;
@Service
public class PrefectureStrategyServiceImpl implements PrefectureStrategyService {
	
	@Autowired
	private OpsPrefectureStrategyClient opsPrefectureStrategyClient;
	
	@Override
	public int save(ReqPrefectureStrategy reqPrefectureStrategy) {
		return opsPrefectureStrategyClient.save(reqPrefectureStrategy);
	}

	@Override
	public int update(ReqPrefectureStrategy reqPrefectureStrategy) {
		return opsPrefectureStrategyClient.update(reqPrefectureStrategy);
	}

	@Override
	public int delete(List<Long> ids) {
		return opsPrefectureStrategyClient.delete(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return opsPrefectureStrategyClient.updateStatus(map);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsPrefectureStrategyClient.list(pageable);
	}

	@Override
	public ResPrefectureStrategyNew selectByPrimaryKey(Long id) {
		return opsPrefectureStrategyClient.selectByPrimaryKey(id);
	}

	@Override
	public List<ResStrategyFee> findList() {
		return opsPrefectureStrategyClient.findList();
	}

	@Override
	public int validateTime(Map<String, String> map) {
		return opsPrefectureStrategyClient.validateTime(map);
	}

	@Override
	public int validateDate(Map<String, String> map) {
		return opsPrefectureStrategyClient.validateDate(map);
	}

}
