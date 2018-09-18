package cn.linkmore.enterprise.service;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.dao.cluster.StallExcStatusClusterMapper;
import cn.linkmore.enterprise.dao.master.StallExcStatusMasterMapper;
import cn.linkmore.enterprise.entity.StallExcStatus;
import cn.linkmore.enterprise.response.ResEntExcStallStatus;

/**
 * 车位异常原因实现
 * @author   GFF
 * @Date     2018年8月6日
 * @Version  v2.0
 */
@Service
public class StallExcStatusServiceImpl implements StallExcStatusService {

	@Resource
	private StallExcStatusClusterMapper clusterMapper;
	@Resource
	private StallExcStatusMasterMapper masterMapper;
	
	@Override
	public StallExcStatus findExcStatus(Long stallId) {
		return clusterMapper.findExcStatus(stallId);
	}
	@Override
	public List<StallExcStatus> findExcStatusList(List<Long> stallId) {
		return clusterMapper.findExcStatusList(stallId);
	}
	@Override
	public void updateExcStatus(Map<String,Object> map) {
		this.masterMapper.updateExcStatus(map);
	}
	@Override
	public void saveBatch(List<StallExcStatus> excs) {
		this.masterMapper.saveBatch(excs);
	}
	@Override
	public List<StallExcStatus> findAll() {
		return this.clusterMapper.findAll();
	}
	@Override
	public List<ResEntExcStallStatus> findResAll() {
		return this.clusterMapper.findResAll();
	}
	@Override
	public ResEntExcStallStatus findByStallId(Long stallId) {
		return this.clusterMapper.findByStallId(stallId);
	}
	
	
	
	
}
