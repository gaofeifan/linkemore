package cn.linkmore.account.service;

import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserLogin;

/**
 * @author   GFF	用户微信
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface UserAppfansService {

	/**
	 * @Description	根据id查询  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	UserAppfans findById(String id);

	/**
	 * @Description  根据用户id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserAppfans findByUserId(Long userId);

	/**
	 * @Description  新增(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insertSelective(UserAppfans record);

	/**
	 * @Description  更新(null处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateByIdSelective(UserAppfans record);

	/**
	 * @Description  更新状态通过userid
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatusByUserId(Long userId, int i);

	/**
	 * @Description  微信登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserLogin wxLogin(ReqUserAppfans appfans);
	}
