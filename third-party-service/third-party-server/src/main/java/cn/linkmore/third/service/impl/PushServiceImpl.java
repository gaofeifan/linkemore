package cn.linkmore.third.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.third.config.BeanFactory;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.service.PushService;

public class PushServiceImpl implements PushService {
	
	@Autowired
	private BeanFactory beanFactory;
	
	
	private boolean push(ReqPush rp) {   
		String content = "账号已在其它设备上登录,请重新登录"; 
		boolean flag = false;
		JPushClient jpushClient = this.beanFactory.jPushClient(); 
		if(Token.OS_ANDROID == rp.getOs()){
			Builder android = PushPayload.newBuilder();  
			android.setAudience(Audience.alias(rp.getAlias()));
			android.setMessage(Message.newBuilder()
					.addExtra("title", rp.getTitle())
					.addExtra("content", rp.getContent())
					.addExtra("type",rp.getType().id) 
					.addExtra("data", rp.getData())
					.setMsgContent(content).build());
			android.setPlatform(Platform.android());
			PushPayload androidppl = android.build();
			try {
				jpushClient.sendPush(androidppl);
				flag = true;
			} catch (Exception e) { }
			
		}else if(Token.OS_IOS==rp.getOs()){
			Builder ios = PushPayload.newBuilder(); 
			ios.setAudience(Audience.alias(rp.getAlias()));
			ios.setPlatform(Platform.ios());
			Notification.Builder nfb = Notification.newBuilder();
			nfb.setAlert(content);
			nfb.addPlatformNotification(AndroidNotification.newBuilder().setTitle(rp.getTitle()).build());
			nfb.addPlatformNotification(IosNotification.newBuilder().incrBadge(1)
					.addExtra("title",rp.getTitle())
					.addExtra("content", rp.getContent())
					.addExtra("data", rp.getData())
					.addExtra("type", rp.getType().id).build());
			ios.setOptions(Options.newBuilder().setApnsProduction(true).build());
			ios.setNotification(nfb.build());
			PushPayload iosppl = ios.build();
			try {
				jpushClient.sendPush(iosppl);
				flag = true;
			} catch (Exception e) { } 
		}  
		return flag;
	}

}
