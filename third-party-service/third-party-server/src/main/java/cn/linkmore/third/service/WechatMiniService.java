package cn.linkmore.third.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.response.ResWechatMiniOrder;

public interface WechatMiniService {

	ResMiniSession getSession(String code);

	ResWechatMiniOrder order(ReqWechatMiniOrder wechat);
 
}
