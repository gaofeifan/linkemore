package cn.linkmore.third.service;

import java.io.IOException;

import org.jdom.JDOMException;

import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.third.response.ResFans;

public interface AppWechatService {

	/**
	 * 根据code得到粉丝
	 * @param code
	 * @return
	 */
	ResFans getWechatFans(String code);

	/**
	 * 生成微信账单
	 * @param wechat
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	ResAppWechatOrder order(ReqAppWechatOrder wechat) throws JDOMException, IOException;

}
