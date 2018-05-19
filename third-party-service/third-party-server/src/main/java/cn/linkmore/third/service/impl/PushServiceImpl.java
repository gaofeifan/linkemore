package cn.linkmore.third.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
import cn.linkmore.bean.common.Constants;
import cn.linkmore.third.config.BeanFactory;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.service.PushService;

/**
 * Service - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class PushServiceImpl implements PushService {
	
	@Autowired
	private BeanFactory beanFactory;
	
	
	private boolean android(ReqPush rp) {
		boolean flag = false;
		JPushClient jpushClient = this.beanFactory.jPushClient(); 
		Builder android = PushPayload.newBuilder();  
		android.setAudience(Audience.alias(rp.getAlias()));
		android.setMessage(Message.newBuilder()
				.addExtra("title", rp.getTitle())
				.addExtra("content", rp.getContent())
				.addExtra("type",rp.getType().id) 
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
	
	private boolean ios(ReqPush rp) {
		boolean flag = false;
		JPushClient jpushClient = this.beanFactory.jPushClient(); 
		Builder ios = PushPayload.newBuilder(); 
		ios.setAudience(Audience.alias(rp.getAlias()));
		ios.setPlatform(Platform.ios());
		Notification.Builder nfb = Notification.newBuilder();
		nfb.setAlert(rp.getContent());
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
		return flag;
	} 
	
	@Override
	@Async
	public void push(ReqPush rp) { 
		if(rp.getClient().intValue()  ==Constants.ClientSource.ANDROID.source) {
			this.android(rp);
		}else {
			this.ios(rp);
		}
	}
	
	@Override
	@Async
	public void push(List<ReqPush> rps) { 
		for(ReqPush rp:rps) {
			try {
				if(rp.getClient().intValue()  ==Constants.ClientSource.ANDROID.source) {
					this.android(rp);
				}else {
					this.ios(rp);
				}
			}catch(Exception e) {
				
			}
		} 
	} 
}
