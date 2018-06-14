package cn.linkmore.third.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.linkmore.third.service.TokenService;
import cn.linkmore.third.service.WechatService;
import cn.linkmore.third.wechat.core.ErrCode;
import cn.linkmore.third.wechat.core.HttpMethod;
import cn.linkmore.third.wechat.core.WeChat;
import cn.linkmore.third.wechat.vo.Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 微信 服务处理，消息转换等
 * @author liwl
 * @version 1.0
 */
@Service
public class WechatServiceImpl implements WechatService {
	private  Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private TokenService tokenService; 
	
	/**
	 * 发布菜单
	 * @param menus
	 * @param accessToken
	 * @return
	 */
	public  JSONObject publishWxMenu(String menus,String accessToken){
		String url = WeChat.getMenuCreateUrl(accessToken);
		return WeChat.httpsRequest(url, HttpMethod.POST, menus);
	}

	/**
	 * 删除菜单
	 * @param accessToken
	 * @return
	 */
	public  JSONObject deleteWxMenu(String accessToken){
		String url = WeChat.getMenuDeleteUrl(accessToken);
		return WeChat.httpsRequest(url, HttpMethod.POST, null);
	}

	/**
	 * 群发接口
	 * @param mediaId
	 * @param msgType
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public  JSONObject sendAll(String mediaId,String msgType,String appId,String appSecret){
		JSONObject postObj = new JSONObject();
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", true);
		postObj.put("filter", filter);
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		postObj.put("mpnews", mpnews);
		postObj.put("msgtype", msgType);
		String token = WeChat.getTokenUrl(appId, appSecret);
		return WeChat.httpsRequest(WeChat.getUploadNewsUrl(token), HttpMethod.POST, postObj.toString());
	}

	/**
	 * 获取ticket
	 * @param actionName
	 * @param sceneId
	 * @return
	 */
	public String getTicket(String actionName,Long sceneId) {
		Token token = this.tokenService.getToken();
		String ticket = null;
		String ticketUrl = WeChat.getTicketUrl(token);
		JSONObject postObj = new JSONObject();
		postObj.put("action_name", actionName);
		Map<String,Object> sceneMap = new HashMap<String,Object>();
		Map<String,Object> idMap = new HashMap<String,Object>();
		idMap.put("scene_id", sceneId);
		sceneMap.put("scene", idMap);
		postObj.put("action_info", sceneMap);
		JSONObject jsonObject = WeChat.httpsRequest(ticketUrl, HttpMethod.POST, postObj.toString()); 
		if (null != jsonObject&&jsonObject.containsKey("errcode")) { 
			int errorCode = jsonObject.getInt("errcode");
			log.info("获取用户二维码tikcet errcode:{} errmsg:{}", errorCode, ErrCode.errMsg(errorCode));
			if(errorCode==40001){
				log.info("40001 access_token is invalid and reget access_token "); 
				token = tokenService.resetToken();
				ticketUrl = WeChat.getTicketUrl(token);
				jsonObject = WeChat.httpsRequest(ticketUrl, HttpMethod.POST, postObj.toString()); 
			} 
			 
		}
		log.info(jsonObject.toString());
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				ticket = jsonObject.getString("ticket");
			} catch (JSONException e) { }
		} 
		log.info("create scan ticket:{}",ticket);
		return ticket;
	}
	/**
	 * 获取js api ticket
	 * @param actionName
	 * @param sceneId
	 * @return
	 */
	public  String getJsApiTicket() {
		Token token = this.tokenService.getToken();
		String ticket = null;
		String ticketUrl = WeChat.getJsApiTicket(token);
		JSONObject jsonObject = WeChat.httpsRequest(ticketUrl, HttpMethod.POST,null);
		if (jsonObject!=null && jsonObject.containsKey("ticket")) {
			try {
				ticket = jsonObject.getString("ticket");
			} catch (JSONException e) {
				ticket = null;	// 获取ticket失败
			}
		}else if(null != jsonObject){
//			ticket = jsonObject.getInt("errcode");
		}
		return ticket;
	}
	public Boolean sendMsg(Map<String,Object> param){
		Token token = this.tokenService.getToken();
		String url = WeChat.getMsgSendUrl(token);
		JSONObject json = WeChat.httpsRequest(url, HttpMethod.POST, JSONObject.fromObject(param).toString());
		Boolean flag = false; 
		if (null != json &&json.get("errcode")!=null&& "0".equals(json.get("errcode").toString()) ){
			flag = true;
		}else if(null != json){
			flag = false;
		}
		 
		log.info("send msg :"+JSONObject.fromObject(param).toString());
		log.info("return msg:"+json.toString()); 
		log.info("success:"+flag); 
		return flag;
	}
	 /**
     * 以http方式发送请求,并将请求响应内容输出到文件
     * @param path    请求路径
     * @param method  请求方法
     * @param body    请求数据
     * @return 返回响应的存储到文件
     */
    public  File httpRequestToFile(String fileName,String path, String method, String body) {
        if(fileName==null||path==null||method==null){
            return null;
        }

        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            if(inputStream!=null){
                file = new File(fileName);
            }else{
                return file;
            }

            //写入到文件
            fileOut = new FileOutputStream(file);
            if(fileOut!=null){
                int c = inputStream.read();
                while(c!=-1){
                    fileOut.write(c);
                    c = inputStream.read();
                }
            }
        } catch (Exception e) {

        }finally{
            if(conn!=null){
                conn.disconnect();
            }

            /*
             * 必须关闭文件流
             * 否则JDK运行时，文件被占用其他进程无法访问
             */
            try {
                inputStream.close();
                fileOut.close();
            } catch (IOException execption) {

            }
        }
        return file;
    } 
}



