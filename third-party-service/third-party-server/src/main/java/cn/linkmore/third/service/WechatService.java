package cn.linkmore.third.service;

import org.springframework.stereotype.Service;

/**
 * 微信 服务处理，消息转换等
 * @author liwl
 * @version 1.0
 */
@Service
public interface WechatService {

	/**
	 * 获取ticket
	 * @param actionName
	 * @param sceneId
	 * @return
	 */
	public String getTicket(String actionName,Long sceneId);
}



