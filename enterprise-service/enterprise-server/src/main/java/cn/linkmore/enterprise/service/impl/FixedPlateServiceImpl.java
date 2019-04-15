package cn.linkmore.enterprise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.enterprise.dao.cluster.FixedPlateClusterMapper;
import cn.linkmore.enterprise.response.ResFixedPlate;
import cn.linkmore.enterprise.service.FixedPlateService;

@Service
public class FixedPlateServiceImpl implements FixedPlateService {

	@Autowired
	private FixedPlateClusterMapper fixedPlateClusterMapper;

	@Override
	public ResFixedPlate findPlateNosByStallId(Long stallId) {
		return fixedPlateClusterMapper.findPlateNosByStallId(stallId);
	}

}
