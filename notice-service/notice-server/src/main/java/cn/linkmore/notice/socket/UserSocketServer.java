package cn.linkmore.notice.socket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.SpringUtil;

/**
 * Websocket
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@ServerEndpoint(value = "/ws/user/{openid}")
@Component
public class UserSocketServer {

	private String openid;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static int onlineCount = 0;

	private static ConcurrentHashMap<String, UserSocketServer> webSocketMap = new ConcurrentHashMap<String, UserSocketServer>();

	private Session session;

	@OnOpen
	public void onOpen(@PathParam("openid") String openid, Session session) {
		RedisService redisService = SpringUtil.getBean(RedisService.class);
		Boolean success = false;
		log.info("A websokcet connceted:{}", openid);
		if (redisService.exists(RedisKey.USER_WXAPP_AUTH_TOKEN.key + openid)) {  
			this.openid = openid;
			this.session = session;
			webSocketMap.put(openid, this);
			addOnlineCount();
			log.info("new user added！current user count :{}", getOnlineCount());
			success = true;
			try {
				sendMessage(success.toString());
			} catch (IOException e) {
				log.error("websocket IO异常");
			}
		}else { 
			/*this.openid = openid;
			this.session = session;
			webSocketMap.put(openid, this); 
			addOnlineCount();
			log.info("new user added！current user count :{}", getOnlineCount());
			success = true;*/
			return;
		} 
	}

	@OnClose
	public void onClose() {
		webSocketMap.remove(this.openid);
		subOnlineCount();
		log.info("user closed socket！current user count:{}", getOnlineCount());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("client socket message:{}", message);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("socket error");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static void send(String key, String message) throws IOException {
		UserSocketServer wss = webSocketMap.get(key);
		if (wss != null) {
			wss.sendMessage(message);
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		UserSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		UserSocketServer.onlineCount--;
	}

}
