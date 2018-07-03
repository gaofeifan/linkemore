package cn.linkmore.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.DictClusterMapper;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.common.service.DictService;
/**
 * Service实现 - 老字典
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class DictServiceImpl implements DictService {
	
	@Autowired
	DictClusterMapper dictClusterMapper;

	@Override
	public ResOldDict find(Long id) {
		return this.dictClusterMapper.find(id);
	}

	@Override
	public List<ResOldDict> findBillSystemList() {
		return this.dictClusterMapper.findBillSystemList();
	}

	
	

}
