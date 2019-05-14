package cn.linkmore.third.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import cn.linkmore.bean.common.Constants.SmsTemplate;
import cn.linkmore.third.config.BeanFactory;
import cn.linkmore.third.config.SmsConfig;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.util.JsonUtil;

/**
 * 阿里云，短信发送
 * @author llh
 *
 */
@Service
public class SmsAliyunImpl {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private SmsConfig smsConfig;
	
	/**
	 * 获取模板信息
	 * @param id 模板ID
	 * @return
	 */ 
	public String getTemplateCode(SmsTemplate st) {
		return SmsServiceImpl.idAndTpl.get(st.id); 
	}
	
	
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param type 消息类型：1注册,2更改密码,3找回密码,4登录,5车位锁授权,6取消授权,7 微信公众号用户登录,8 发送优惠劵,9挂起订单,10;
	 * @param tplId 模板ID
	 * @param param 参数
	 * @return
	 */
	
	public boolean send(ReqSms req) {
		boolean flag = true;
		try {
			IAcsClient client = beanFactory.iAcsClient(); 
			SendSmsRequest request = new SendSmsRequest(); 
	        request.setPhoneNumbers(req.getMobile()); 
	        request.setSignName(smsConfig.getSignName()); 
	        request.setTemplateCode(getTemplateCode(req.getSt())); 
	        request.setTemplateParam(JsonUtil.toJson(req.getParam())); 
	        SendSmsResponse response = client.getAcsResponse(request);  
			log.info("send sms success requestId:{},req:{}",response.getRequestId(),JsonUtil.toJson(req)); 
		} catch (ClientException e) {
			log.info("send sms failure");
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stacks = e.getStackTrace();
	        for (int i = 0; i < stacks.length; i++) {
	            StackTraceElement element = stacks[i];
	            sb.append(element.toString() + "\n");
	        }
			log.info(sb.toString());
			flag = false;
		}
		return flag;
	}
}
