package cn.linkmore.third.service.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.third.config.BaseConfig;
import cn.linkmore.third.config.BeanFactory;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.service.PushService;
import cn.linkmore.util.JsonUtil;

/**
 * Service - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class PushServiceImpl implements PushService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private BaseConfig baseConfig;
	
	
	class AndroidThread extends Thread{
		private ReqPush rp;
		public AndroidThread(ReqPush rp) {
			this.rp =rp;
		}
		public void run() {
			android(rp);
		}
	}
	
	
	private boolean android(ReqPush rp) {
		boolean flag = false;
		JPushClient jpushClient = this.beanFactory.jPushClient(); 
		Builder android = PushPayload.newBuilder();  
		android.setAudience(Audience.alias("u"+rp.getAlias()));
		android.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
				.addExtra("timestamp", new Date().getTime())
				.addExtra("data", rp.getData())
				.setMsgContent(rp.getContent()).build());
		android.setPlatform(Platform.android());
		PushPayload androidppl = android.build();
		try {
			jpushClient.sendPush(androidppl);
			flag = true;
		} catch (Exception e) { }
		return flag;
	}
	
	class IOSThread extends Thread{
		private ReqPush rp;
		public IOSThread(ReqPush rp) {
			this.rp =rp;
		}
		public void run() {
			ios(rp);
		}
	}
	
	private boolean ios(ReqPush rp) {
		boolean flag = false;
		JPushClient jpushClient = this.beanFactory.jPushClient();
		Builder ios = PushPayload.newBuilder();  
		ios.setAudience(Audience.alias("u"+rp.getAlias()));
		ios.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
				.addExtra("timestamp", new Date().getTime())
				.addExtra("data", rp.getData())
				.setMsgContent(rp.getContent()).build());
		ios.setPlatform(Platform.ios());
		ios.setOptions(Options.newBuilder().setApnsProduction(baseConfig.getOnline()).build());
		PushPayload iosppl = ios.build();
		try {
			jpushClient.sendPush(iosppl);
			flag = true;
		} catch (Exception e) { }
		return flag;
	} 
	
	@Override 
	public void push(ReqPush rp) { 
		log.info("push message:{}",JsonUtil.toJson(rp));
		Thread thread = null;
		if(rp.getClient().intValue()  ==Constants.ClientSource.ANDROID.source) {
			thread = new AndroidThread(rp);
		}else {
			thread = new IOSThread(rp);
		}
		thread.start();
	}
	
	@Override 
	public void push(List<ReqPush> rps) { 
		Thread thread = null;
		for(ReqPush rp:rps) {
			try {
				if(rp.getClient().intValue()  ==Constants.ClientSource.ANDROID.source) {
					thread = new AndroidThread(rp);
				}else {
					thread = new IOSThread(rp);
				}
				thread.start();
			}catch(Exception e) {
				
			}
		} 
	}
	
	
	
	/**
	 *  物业版推送
	 */
	@Override
	public void send(ReqPush rp) {
		log.info("send message:{}",JsonUtil.toJson(rp));
		new IOSSendThread(rp).start();
	} 

	class IOSSendThread extends Thread{
		private ReqPush rp;
		public IOSSendThread(ReqPush rp) {
			this.rp =rp;
		}
		public void run() {
			if(rp.getClient().intValue()  ==Constants.ClientSource.ANDROID.source) {
				androidsend(rp);
			}else {
				iossend(rp);
			}
		}
	}
	
	void iossend(ReqPush rp) {
		JPushClient jSendClient = this.beanFactory.jSendClient();
		Builder ios = PushPayload.newBuilder();
		ios.setAudience(Audience.alias("u"+rp.getAlias()));
		ios.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
				.addExtra("timestamp", new Date().getTime())
				.addExtra("data", rp.getData())
				.setMsgContent(rp.getContent()).build());
		ios.setPlatform(Platform.ios());
		ios.setOptions(Options.newBuilder().setApnsProduction(baseConfig.getOnline()).build());
		PushPayload iosppl = ios.build();
		try {
			jSendClient.sendPush(iosppl);
		} catch (Exception e) { }
	}
	
	void androidsend(ReqPush rp) {
		JPushClient jSendClient = this.beanFactory.jSendClient();
		Builder android = PushPayload.newBuilder();
		android.setAudience(Audience.alias("u"+rp.getAlias()));
		android.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
				.addExtra("timestamp", new Date().getTime())
				.addExtra("data", rp.getData())
				.setMsgContent(rp.getContent()).build());
		android.setPlatform(Platform.android());
		android.setOptions(Options.newBuilder().setApnsProduction(baseConfig.getOnline()).build());
		PushPayload androidppl = android.build();
		try {
			jSendClient.sendPush(androidppl);
		} catch (Exception e) { }
	}
	
	/**
	 * 管理版推送
	 */
	@Override
	public void give(ReqPush rp) {
		log.info("give message:{}",JsonUtil.toJson(rp));
		new IOSGiveThread(rp).start();
	}

	class IOSGiveThread extends Thread{
		private ReqPush rp;
		public IOSGiveThread(ReqPush rp) {
			this.rp =rp;
		}
		public void run() {
			if(rp.getClient().intValue()  ==Constants.ClientSource.ANDROID.source) {
				androidgive(rp);
			}else {
				iosgive(rp);
			}
		}
	}
	
	void iosgive(ReqPush rp) {
		JPushClient jSendClient = this.beanFactory.jGiveClient();
		Builder ios = PushPayload.newBuilder();
		ios.setAudience(Audience.alias("u"+rp.getAlias()));
		ios.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
				.addExtra("timestamp", new Date().getTime())
				.addExtra("data", rp.getData())
				.setMsgContent(rp.getContent()).build());
		ios.setPlatform(Platform.ios());
		ios.setOptions(Options.newBuilder().setApnsProduction(baseConfig.getOnline()).build());
		PushPayload iosppl = ios.build();
		try {
			jSendClient.sendPush(iosppl);
		} catch (Exception e) { }
	}
	
	void androidgive(ReqPush rp) {
		JPushClient jSendClient = this.beanFactory.jGiveClient();
		Builder android = PushPayload.newBuilder();
		android.setAudience(Audience.alias("u"+rp.getAlias()));
		android.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
				.addExtra("timestamp", new Date().getTime())
				.addExtra("data", rp.getData())
				.setMsgContent(rp.getContent()).build());
		android.setPlatform(Platform.android());
		android.setOptions(Options.newBuilder().setApnsProduction(baseConfig.getOnline()).build());
		PushPayload androidppl = android.build();
		try {
			jSendClient.sendPush(androidppl);
		} catch (Exception e) { }
	}
	
	
public static void main(String[] args) {
	
	JPushClient jSendClient =  new JPushClient("148ba7bd213a23a1e360f499","fdde7a74e91b2b0b82172555",3);
	
	Builder android = PushPayload.newBuilder();
	android.setAudience(Audience.alias("u42"));
	android.setMessage(Message.newBuilder()
			.addExtra("title", new Date().getTime())
			.addExtra("content","安卓1")
			.addExtra("type","安卓2") 
			.addExtra("timestamp", new Date().getTime())
			.addExtra("data","安卓3")
			.setMsgContent("安卓4").build());
	android.setPlatform(Platform.android());
	android.setOptions(Options.newBuilder().setApnsProduction(false).build());
	PushPayload androidppl = android.build();
	try {
		jSendClient.sendPush(androidppl);
	} catch (Exception e) { }
	
	Builder ios = PushPayload.newBuilder();
	ios.setAudience(Audience.alias("u47"));
	ios.setMessage(Message.newBuilder()
			.addExtra("title", new Date().getTime())
			.addExtra("content","苹果1")
			.addExtra("type","苹果2") 
			.addExtra("timestamp", new Date().getTime())
			.addExtra("data","苹果3")
			.setMsgContent("苹果4").build());
	ios.setPlatform(Platform.ios());
	ios.setOptions(Options.newBuilder().setApnsProduction(false).build());
	PushPayload iosppl = ios.build();
	try {
		jSendClient.sendPush(iosppl);
	} catch (Exception e) { }
	
}

}
