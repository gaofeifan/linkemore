package cn.linkmore.coupon.service;

import java.util.List;
import cn.linkmore.coupon.response.ResCombo;

public interface ComboService {

	List<ResCombo> selectComboListByType(Long comboType);
	
}
