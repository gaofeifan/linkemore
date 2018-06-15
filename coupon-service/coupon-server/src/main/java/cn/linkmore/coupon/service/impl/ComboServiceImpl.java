package cn.linkmore.coupon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.coupon.dao.cluster.ComboClusterMapper;
import cn.linkmore.coupon.entity.Combo;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.service.ComboService;

@Service
public class ComboServiceImpl implements ComboService {
	
	@Autowired
	private ComboClusterMapper comboClusterMapper;

	@Override
	public List<ResCombo> selectComboListByType(Long comboType) {
		return comboClusterMapper.selectComboListByType(comboType);
	}
	
}
