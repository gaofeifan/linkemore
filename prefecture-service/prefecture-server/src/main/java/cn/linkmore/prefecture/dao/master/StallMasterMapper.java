package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Stall;
/**
 * dao 车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallMasterMapper {
	/**
	 * 删除id对应的信息
	 * @param id 主键
	 * @return  删除条数
	 */
	int delete(Long id);

	/**
	 * 保存信息
	 * @param stall 车位信息
	 * @return 保存条数
	 */
	int save(Stall stall);  

	/**
	 * 更新信息
	 * @param stall 车位信息
	 * @return 更新条数
	 */
	int update(Stall stall); 
	/**
	 * 预约分配车位
	 * @param stall
	 */
	void order(Stall stall);
	/**
	 * 取消订单释放车位
	 * @param stall
	 */
	void cancel(Stall stall);
	/**
	 * 降锁操作
	 * @param stall
	 */
	void lockdown(Stall stall);
	/**
	 * 结账立场释放车位
	 * @param stall
	 */
	void pay(Stall stall);
}