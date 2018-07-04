package cn.linkmore.third.ws;
  
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
 * @author liwenlong
 * @version 2.0
 *
 */
@ServerEndpoint(value = "/ws/{token}") 
@Component
public class WebSocketServer { 
	
	private String token;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
    private static int onlineCount = 0; 
    
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<String,WebSocketServer>(); 
    
    private Session session;
 
    class PushThread extends Thread{
    	private WebSocketServer server;
    	public PushThread(WebSocketServer server) {
    		this.server = server;
    	}
    	
    	public void run() {
    		try {
    			int time = 0;
    			while(time++<10) {
    				this.server.sendMessage("Hello Kitty");
    				try {
						Thread.sleep(1000l*30);
					} catch (InterruptedException e) { 
						e.printStackTrace();
					}
    			}
				
			} catch (IOException e) { 
				e.printStackTrace();
			}
    	}
    }
    
    @OnOpen
    public void onOpen(@PathParam("token") String token,Session session) {
//    	RedisService redisService = SpringUtil.getBean(RedisService.class); 
    	Boolean success = false;
    	log.info("A websokcet connceted:{}",token);  
//    	if(redisService.exists(RedisKey.USER_APP_AUTH_USER.key+token)) {
//    		this.token = token;
//            this.session = session;
//            webSocketMap.put(this.token,this); 
//            addOnlineCount();  
//            log.info("new user added！current user count :{}" ,getOnlineCount());
//            success = true;
//    	} 
    	this.token = token;
        this.session = session;
        webSocketMap.put(this.token,this); 
        addOnlineCount();  
        log.info("new user added！current user count :{}" ,getOnlineCount());
        success = true;
    	try {
       	 sendMessage(success.toString());
       	new PushThread(this).start();
        } catch (IOException e) {
           log.error("websocket IO异常");
        }
    	try {
       	 sendMessage(success.toString());
        } catch (IOException e) {
           log.error("websocket IO异常");
        }
    	
    } 
 
    
    @OnClose
    public void onClose() {
    	webSocketMap.remove(this.token); 
        subOnlineCount(); 
        log.info("user closed socket！current user count:{}" , getOnlineCount());
    }
 
     
    @OnMessage
    public void onMessage(String message, Session session) {
    	log.info("client socket message:{}" , message); 
       
    }
 
	 
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("socket error");
        error.printStackTrace();
    }
 
 
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
 
  
    public static void send(String key,String message) throws IOException { 
    	WebSocketServer wss = webSocketMap.get(key);
    	if(wss!=null) {
    		wss.sendMessage(message);
    	} 
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
 
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
