package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqSms;
/**
 * Service - 短信接口
 * @author liwenlong
 * @version 2.0
 *
 */
public interface SmsService {

	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param st 模板
	 * @param param  参数
	 * @return
	 */
	boolean send(ReqSms req); 

}
