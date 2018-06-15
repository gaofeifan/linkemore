package cn.linkmore.coupon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.coupon.dao.cluster.ValuePackClusterMapper;
import cn.linkmore.coupon.response.ResValuePack;
import cn.linkmore.coupon.service.ValuePackService;

@Service
public class ValuePackServiceImpl implements ValuePackService {
	
	@Autowired
	private ValuePackClusterMapper valuePackClusterMapper;

	@Override
	public List<ResValuePack> selectPackTypeList(Long comboType) {
		return valuePackClusterMapper.selectPackTypeList(comboType);
	}
	
}
