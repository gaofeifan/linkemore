package cn.linkmore.ops.account.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqDep;

public interface CodePreService {

	public ViewPage findPage(ViewPageable pageable);
	
	public ViewPage findRecordPage(ViewPageable pageable);
	
	public   List<Map<String,Object>> payList(String orderNo);
	
	public List<Map<String, Object>> selectAll();
	
	public	void save(ReqDep reqDep);
	
	public void delete( String preId);
	
	public Map<String,Object> down(String preId);
	
}
