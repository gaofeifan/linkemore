package cn.linkmore.ops.account.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.CodePreService;
import cn.linkmore.prefecture.client.CodePayPrefectureClient;
import cn.linkmore.prefecture.request.ReqDep;


@Service
public class CodePreServiceImpl implements CodePreService{

	@Autowired
	CodePayPrefectureClient codePayPrefectureClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return codePayPrefectureClient.list(pageable);
	}

	@Override
	public void save(ReqDep reqDep) {
		codePayPrefectureClient.save(reqDep);
	}

	@Override
	public ViewPage findRecordPage(ViewPageable pageable) {
		
		return codePayPrefectureClient.record(pageable);
	}

	@Override
	public List<Map<String, Object>> payList(String orderNo) {
		return codePayPrefectureClient.payList(orderNo);
	}

	@Override
	public void delete(String preId) {
		codePayPrefectureClient.delete(preId);
	}

	@Override
	public Map<String,Object> down(String preId) {
		return codePayPrefectureClient.down(preId);
	}

	@Override
	public List<Map<String, Object>> selectAll() {
		return codePayPrefectureClient.selectAll();
	}
	
	
	
}
