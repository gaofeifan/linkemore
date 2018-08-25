package cn.linkmore.third.service;

import java.util.List;

import cn.linkmore.third.request.ReqPush;

/**
 * Service - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
public interface PushService {

	/**
	 * 推送消息
	 * @param rp 消息体
	 * @return
	 */
	void push(ReqPush rp);

	/**
	 * 批量推送
	 * @param rps 消息集
	 * @return
	 */
	void push(List<ReqPush> rps);
	
	/**
	 * 长租及物业推送
	 * @param rp 消息体
	 * @return
	 */
	void send(ReqPush rp);

}
