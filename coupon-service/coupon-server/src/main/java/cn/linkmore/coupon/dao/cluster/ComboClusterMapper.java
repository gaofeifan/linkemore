package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import cn.linkmore.coupon.response.ResCombo;

public interface ComboClusterMapper {

    ResCombo findById(Long id);
	List<ResCombo> selectComboListByType(Long comboType);
}