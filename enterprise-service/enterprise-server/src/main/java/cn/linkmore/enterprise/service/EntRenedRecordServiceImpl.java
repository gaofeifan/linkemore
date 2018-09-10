package cn.linkmore.enterprise.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentedRecordMasterMapper;
import cn.linkmore.enterprise.entity.EntRentedRecord;

@Service
public class EntRenedRecordServiceImpl implements EntRenedRecordService {

	@Resource
	private EntRentedRecordMasterMapper entRentedRecordMasterMapper;
	@Resource
	private EntRentedRecordClusterMapper entRentedRecordClusterMapper;
	@Override
	public void updateDownTime(Long stallId) {
		EntRentedRecord id = entRentedRecordClusterMapper.findByStallId(stallId);
		Map<String,Object> map = new HashMap<>();
		map.put("id", id.getId());
		map.put("date", new Date());
		entRentedRecordMasterMapper.updateDownTime(map);
			
	}

	
}
