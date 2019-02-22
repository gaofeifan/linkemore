package cn.linkmore.prefecture.core.lock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.notice.client.EntSocketClient;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
import cn.linkmore.prefecture.client.EntStaffClient;
import cn.linkmore.prefecture.config.StartupRunner;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.response.ResLockMessage;
import cn.linkmore.task.TaskPool;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.util.JsonUtil;

public class EntLockMessageDecorator extends LockMessageDecorator {

	public EntLockMessageDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService,reqc);
	}
	private EntSocketClient entSocketClient = StartupRunner.get().getBean(EntSocketClient.class);
	private EntStaffClient entStaffClient = StartupRunner.get().getBean(EntStaffClient.class);
	private EntOrderClient entOrderClient = StartupRunner.get().getBean(EntOrderClient.class);
	private SendClient sendClient = StartupRunner.get().getBean(SendClient.class);
	private EntRentedRecordClient entRentedRecordClient = StartupRunner.get().getBean(EntRentedRecordClient.class);;
	private StallClusterMapper stallClusterMapper = StartupRunner.get().getBean(StallClusterMapper.class);
	private StallMasterMapper stallMasterMapper = StartupRunner.get().getBean(StallMasterMapper.class);
	private final Logger log = LoggerFactory.getLogger(this.getClass());


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
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				operate();
			}
		});
		return true;
	}

	private void operate() {
		ReqControlLock reqc = getReqc();
		String uid = String.valueOf(redisService.get(reqc.getKey()));
		Stall stall = stallClusterMapper.findById(reqc.getStallId());
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("downing>>>>>> name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResLockMessage res = null;
			// 1 降下 2 升起
			Stopwatch stopwatch = Stopwatch.createStarted();
			if (reqc.getStatus() == 1) {
				res = downLockMes(stall.getLockSn());
			} else if (reqc.getStatus() == 2) {
				res = upLockMes(stall.getLockSn());
			}
			log.info("res>>>>>>>" + res.getMessage());
			int code = res.getCode();
			stopwatch.stop();
			log.info("usingtime>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
			sendMes(uid, reqc.getStatus(), code);
			if (code == 200) {
				if (reqc.getStatus() == 1) {
					stall.setLockStatus(2);
					if (stall.getType() == 2) {
						if (stall.getStatus() != 4) {
							stall.setStatus(2);
						}
					}
				} else if (reqc.getStatus() == 2) {
					if (stall.getType() == 2) {
						if (stall.getStatus() != 4) {
							stall.setStatus(1);
						}
					}
					stall.setLockStatus(1);
				}
				stallMasterMapper.lockdown(stall);
				if (reqc.getStatus() == 1) {
					downLock(reqc.getStallId(), 1, reqc.getType());
					redisService.remove(reqc.getKey());
				}

			} else {
				if (reqc.getStatus() == 1) {
					downLock(reqc.getStallId(), 0, reqc.getType());
				}
			}
		}
	}

	private void downLock(Long stallId, int status, int type) {
		switch (type) {
		case 0:
			ResUserOrder latest = this.entOrderClient.findStallLatest(stallId);
			Map<String, Object> param = new HashMap<>();
			param.put("lockDownStatus", status);
			param.put("lockDownTime", new Date());
			param.put("orderId", latest.getId());
			break;
		case 2:
			this.entRentedRecordClient.updateDownTime(stallId);
			break;
		}
	}

	public void sendMes(String uid, Integer status, int code) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				try {
					String title = "车位锁操作通知";
					String content = "车位锁" + (status == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
					String bool = (code == 200 ? "true" : "false");
					Token token = (Token) redisService.get(RedisKey.STAFF_ENT_AUTH_TOKEN.key + uid.toString());
					log.info("send>>>", JsonUtil.toJson(token));
					if (token != null) {
						if (token.getClient() == Constants.ClientSource.WXAPP.source) {
							CacheUser cu = (CacheUser) redisService
									.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("title", title);
							map.put("type", PushType.LOCK_CONTROL_NOTICE);
							map.put("content", content);
							map.put("data", token.getAccessToken());
							map.put("alias", cu.getId());
							ResEntStaff staff = entStaffClient.findById(cu.getId());
							entSocketClient.push(JsonUtil.toJson(map), staff.getOpenId());
						} else {
							ReqPush rp = new ReqPush();
							rp.setAlias(uid);
							rp.setTitle(title);
							rp.setContent(content);
							rp.setClient(token.getClient());
							rp.setType(PushType.LOCK_CONTROL_NOTICE);
							rp.setData(token.getAccessToken());
							sendClient.send(rp);
						}
					}
				} catch (Exception e) {
					log.info("sendWYMsg>>>" + uid);
				}
			}
		});
	}

}
