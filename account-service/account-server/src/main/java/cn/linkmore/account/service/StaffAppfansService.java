package cn.linkmore.account.service;

import cn.linkmore.account.entity.StaffAppfans;

/**
 * @author   GFF	用户微信
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface StaffAppfansService {

	/**
	 * @Description	根据id查询  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	StaffAppfans findById(String id);

	/**
	 * @Description  根据用户id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
//	ResStaffAppfans findByUserId(Long userId);

	/**
	 * @Description  新增(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insertSelective(StaffAppfans record);

	/**
	 * @Description  更新(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateByIdSelective(StaffAppfans record);

	/**
	 * @Description  更新状态通过userid
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatusByUserId(Long userId, int i);

	 

	/**
	 * @Description  根据用户id删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteByUserId(Long userId);
	}
