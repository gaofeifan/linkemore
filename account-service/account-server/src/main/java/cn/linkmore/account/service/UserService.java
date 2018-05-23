package cn.linkmore.account.service;


import java.util.Map;

import cn.linkmore.account.entity.User;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;

/**
 * 用户接口
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface UserService {
	
	/**
	 * @Description  更新用户车牌
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateVehicle(ReqUpdateVehicle req);

	/**
	 * @Description  查询用户详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserDetails detail(Long userId);

	/**
	 * @Description  更新手机号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateMobile(ReqUpdateMobile bean);

	/**
	 * @Description  删除微信号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void removeWechat(Long userId);

	/**
	 * @Description  根据手机号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	User selectByMobile(String mobile);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	User selectById(Long userId);

	/**
	 * @Description  更新用户昵称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateNickname(ReqUpdateNickname nickname);

	/**
	 * @Description  更新性别
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateSex(ReqUpdateSex sex);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insertSelective(User user);

	/**
	 * @Description  更新登录时间
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateLoginTime(Map<String, Object> param);

	/**
	 * @Description  app登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserLogin appLogin(String mobile);

	/**
	 * @Description  更新用户微信
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateAppfans(ReqUserAppfans bean);

}
