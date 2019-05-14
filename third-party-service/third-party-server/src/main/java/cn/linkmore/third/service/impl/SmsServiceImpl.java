package cn.linkmore.third.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.service.SmsService;
/**
 * Service实现 - 短信
 * @author liwenlong
 * @update llh
 * @version 2.0
 *
 */
@Service
public class SmsServiceImpl implements SmsService {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	public  static Map<String, String> idAndMessage =new HashMap<String,String>();
	public  static final Map<String, String> idAndTpl =new HashMap<String,String>() {
		private static final long serialVersionUID = 1L;
		{ 
			InputStream is = null;
			try {
				is = this.getClass().getClassLoader().getResourceAsStream("config.xml");
				Document document = new SAXReader().read(is);
				@SuppressWarnings("unchecked")
				List<org.dom4j.Element> elements = document.selectNodes("/config/sms"); 
				for (org.dom4j.Element element : elements) {
					put(element.attributeValue("id"), element.attributeValue("tpl"));
					idAndMessage.put(element.attributeValue("id"), element.attributeValue("message"));
				}
			} catch (Exception e) { 
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} 
		}
	}; 
	
	@Autowired
	private SmsAliyunImpl smsAliyunImpl;
	
	@Autowired
	private SmsJiujaxintongImpl smsJiujaxintongImpl;
	
	@Value("${sms.channel}") //短信渠道  1 阿里云,2久佳信通
	private Integer smsChannel=2;
	
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param type 消息类型：1注册,2更改密码,3找回密码,4登录,5车位锁授权,6取消授权,7 微信公众号用户登录,8 发送优惠劵,9挂起订单,10;
	 * @param tplId 模板ID
	 * @param param 参数
	 * @return
	 */
	@Override
	public boolean send(ReqSms req) {
		if(smsChannel==1) {
			return smsAliyunImpl.send(req);
		}else {
			return smsJiujaxintongImpl.send(req);
		}
	}
	
}
