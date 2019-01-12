package cn.linkmore.prefecture.dao.master;

import java.util.Map;

import cn.linkmore.prefecture.request.ReqDep;

public interface CodePayPrefectureMasterMapper {
	
	void save(ReqDep reqDep);
	
	 void delete(String preId);
	 
	 void deleteOld(Map<String, Object> map);
	
}
