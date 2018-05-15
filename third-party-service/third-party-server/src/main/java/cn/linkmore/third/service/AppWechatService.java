package cn.linkmore.third.service;

import cn.linkmore.third.response.ResFans;

public interface AppWechatService {

	/**
	 * 根据code得到粉丝
	 * @param code
	 * @return
	 */
	ResFans getWechatFans(String code);

}
