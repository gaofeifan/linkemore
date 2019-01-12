package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.CodePayPrefectureClient;
import cn.linkmore.prefecture.request.ReqDep;

@Component
public class CodePayPrefectureClientHystrix implements CodePayPrefectureClient {

	@Override
	public ViewPage list(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ReqDep reqDep) {
		// TODO Auto-generated method stub
	}

	@Override
	public ViewPage record(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> payList(String orderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String preId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String,Object> down(String preId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
