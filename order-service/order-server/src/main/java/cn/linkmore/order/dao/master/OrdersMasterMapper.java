package cn.linkmore.order.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.Orders;
@Mapper
public interface OrdersMasterMapper {
    int delete(Long id);

    int save(Orders record);

    int update(Orders record);

    /**
     * 更新订单降锁状态
     * @param param
     * @return
     */
	int updateLockStatus(Map<String, Object> param);

	/**
	 * 切换车位
	 * @param param
	 */
	void updateSwitch(Map<String, Object> param);

	/**
	 * 预结账更新
	 * @param param
	 */
	void updateConfirm(Map<String, Object> param);
	
	/**
	 * 结账更新
	 * @param param
	 */
	void updatePayment(Map<String, Object> param);
	
}