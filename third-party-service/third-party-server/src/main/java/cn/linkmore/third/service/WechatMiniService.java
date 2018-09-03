package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.response.ResWechatMiniOrder;

public interface WechatMiniService {

	ResMiniSession getSession(String code);
	
	ResMiniSession getSessionPlus(String code,Integer alias);

	ResWechatMiniOrder order(ReqWechatMiniOrder wechat);
 
}
