package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqDep;


public interface CodePayPrefectureService {

	public ViewPage findPage(ViewPageable pageable);
	
	public ViewPage findRecordPage(ViewPageable pageable);
	
	public List<Map<String, Object>> payList(String orderNo);
	
	public void delete(String preId);
	
	public Map<String,Object> down( String preId);
	
	void save(ReqDep reqDep);
	
	public List<Map<String, Object>> selectAll();
	   
}
