package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

public interface CodePayPrefectureClusterMapper {

	List<Map<String, Object>> findPage(Map<String, Object> param);
	
	List<Map<String, Object>> findRecordPage(Map<String, Object> param);
	
	int count(Map<String, Object> param);
	
	int countRecord(Map<String, Object> param);
	
   List<Map<String, Object>> payList(String orderNo);
	
}
