package cn.linkmore.prefecture.core.lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.prefecture.config.StartupRunner;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.task.TaskPool;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;
public class StaffLockMessageDecorator extends LockMessageDecorator {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private SendClient sendClient = StartupRunner.get().getBean(SendClient.class);
	
	public StaffLockMessageDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService,reqc);
	}
	@Override
	public boolean upLock(String sn) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				operate();
			}
		});
		return true;
	}

	@Override
	public Boolean downLock(String sn) {
		operate();
		return true;
	}

	
	private void operate() {
		ReqControlLock reqc = getReqc();
		String uid = String.valueOf(redisService.get(reqc.getKey()));
		if (reqc != null && StringUtils.isNotBlank(reqc.getLockSn())) {
			log.info("operating··········sn:{},··········uid:{}", reqc.getLockSn(), uid);
			Boolean res = null;
			// 1 降下 2 升起
			if (reqc.getStatus() == 1) {
				res = super.downLock(reqc.getLockSn());
			} else if (reqc.getStatus() == 2) {
				res = super.upLock(reqc.getLockSn());
			}
			int code = res == false ? 500:200;
			if (code == 200) {
				redisService.remove(reqc.getKey());
			}
			log.info(" operating··············" + res + " code·············" + code);
			sendMsg(uid, reqc.getStatus(), code);
		}
	}
	
	private void sendMsg(String uid, Integer lockstatus, int code) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				String title = "车位锁操作通知";
				String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
				PushType type = PushType.LOCK_CONTROL_NOTICE;
				String bool = (code == 200 ? "true" : "false");
				Token token = (Token) redisService.get(RedisKey.STAFF_STAFF_AUTH_TOKEN.key + uid.toString());

				log.info("sendMsgT   send>>>" + uid+"--content");
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setTitle(title);
				rp.setContent(content);
				rp.setClient(token.getClient());
				rp.setType(type);
				rp.setData(bool);
				sendClient.give(rp);
			}
		});
	}
	
	
}
