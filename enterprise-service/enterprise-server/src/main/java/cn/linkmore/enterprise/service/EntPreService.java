package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntPrefecture;

public interface EntPreService {

	int saveEntPre(Long preId,Long entId, String preName);

	int deleteEntPre(Long id);

	int updateEntPre(Long id, Long preId, String preName);

	/**
	 * @Description  查询企业车区
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntPrefecture> findList(Map<String,Object> map);

	/**
	 * @Description 分页查询 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
