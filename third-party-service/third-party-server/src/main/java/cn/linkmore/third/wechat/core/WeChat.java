package cn.linkmore.third.wechat.core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.linkmore.third.wechat.vo.Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 微信 API、微信基本接口
 * @author liwl
 * @version 1.0
 */

public class WeChat {

	//token 接口
	private static final String TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	//上传多媒体资料接口
	private static final String UPLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

	//创建菜单
	private static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

	//删除菜单
	private static final String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";

	//获取账号粉丝信息
	private static final String GET_FANS_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

	//获取账号粉丝列表
	private static final String GET_FANS_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";

	//网页授权OAuth2.0获取code
	private static final String GET_OAUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";

	//网页授权OAuth2.0获取token
	private static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	//网页授权OAuth2.0获取用户信息
	private static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	//上传图文消息
	private static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";

	private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

	//群发接口
	private static final String SEND_ALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=%s";

	//ticket接口
	private static final String TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

	//msgType接口
	private static final String MSGTYPE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

	private static final String MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

	private static final String JS_API_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	//获取token接口
	public static String getMsgTypeUrl(String ticket){
		return String.format(MSGTYPE, ticket);
	}

	//获取msgType接口
	public static String getTicketUrl(Token token){
		return String.format(TICKET, token.getAccessToken());
	}

	public static String getJsApiTicket (Token token){
		return String.format(JS_API_TICKET, token.getAccessToken());
	}
	//获取客服消息接口
	public static String getMsgSendUrl(Token token){
		return String.format(MSG_SEND, token.getAccessToken());
	}

	//获取token接口
	public static String getTokenUrl(String appId,String appSecret){
		return String.format(TOKEN, appId, appSecret);
	}

	//获取上传Media接口
	public static String getUploadMediaUrl(String token,String type){
		return String.format(UPLOAD_MEDIA, token, type);
	}

	//获取下载Media接口
	public static String getDownloadMediaUrl(String token,String mediaId){
		return String.format(DOWNLOAD_MEDIA, token, mediaId);
	}

	//获取菜单创建接口
	public static String getMenuCreateUrl(String token){
		return String.format(MENU_CREATE, token);
	}

	//获取菜单删除接口
	public static String getMenuDeleteUrl(String token){
		return String.format(MENU_DELETE, token);
	}

	//获取粉丝信息接口
	public static String getFansInfoUrl(String token,String openid){
		return String.format(GET_FANS_INFO, token, openid);
	}

	//获取粉丝列表接口
	public static String getFansListUrl(String token,String nextOpenId){
		return String.format(GET_FANS_LIST, token, nextOpenId);
	}

	//网页授权OAuth2.0获取code
	public static String getOAuthCodeUrl(String appId ,String redirectUrl ,String scope ,String state){
		return String.format(GET_OAUTH_CODE, appId, urlEnodeUTF8(redirectUrl), "code", scope, state);
	}

	//网页授权OAuth2.0获取token
	public static String getOAuthTokenUrl(String appId ,String appSecret ,String code ){
		return String.format(GET_OAUTH_TOKEN, appId, appSecret, code);
	}

	//网页授权OAuth2.0获取用户信息
	public static String getOAuthUserinfoUrl(String token ,String openid){
		return String.format(GET_OAUTH_USERINFO, token, openid);
	}

	//获取上传图文消息接口
	public static String getUploadNewsUrl(String token){
		return String.format(UPLOAD_NEWS, token);
	}

	//群发接口
	public static String getSendAllUrl(String token){
		return String.format(SEND_ALL, token);
	}

	/**
	 * 获取接口访问凭证
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static Token getToken(String appId, String appSecret) {
		Token token = null;
		String tokenUrl = WeChat.getTokenUrl(appId, appSecret);
		JSONObject jsonObject = httpsRequest(tokenUrl, HttpMethod.GET, null); 
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;//获取token失败
			}
		}else if(null != jsonObject){
			token = new Token();
			token.setErrcode(jsonObject.getInt("errcode"));
		}
		return token;
	}

	/**
	 * 获取OAuth2.0 Token
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static Token getOAuthToken(String appId, String appSecret,String code) {
		Token token = null;
		String tokenUrl = WeChat.getOAuthTokenUrl(appId, appSecret, code);
		JSONObject jsonObject = httpsRequest(tokenUrl, HttpMethod.GET, null);
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
				token.setOpenid(jsonObject.getString("openid"));
				token.setScope(jsonObject.getString("scope"));
			} catch (JSONException e) {
				token = null;//获取token失败
			}
		}else if(null != jsonObject){
			token = new Token();
			token.setErrcode(jsonObject.getInt("errcode"));
		}
		return token;
	}

	/**
	 * 获取OAuth2.0 UserInfo
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static JSONObject getOAuthUserinfo(String appId, String appSecret,String code) {
		String tokenUrl = WeChat.getOAuthTokenUrl(appId, appSecret, code);
		JSONObject jsonObject = httpsRequest(tokenUrl, HttpMethod.GET, null);
		System.out.println(jsonObject);
		if (null != jsonObject && jsonObject.get("errcode") == null) {
			try {
				String openid = jsonObject.getString("openid");
				String token = jsonObject.getString("access_token");
				String url = WeChat.getOAuthUserinfoUrl(token, openid);
				return httpsRequest(url,HttpMethod.GET,null);
			} catch (JSONException e) {
				jsonObject = null;//获取jsonObject失败
			}
		}
		return jsonObject;
	}


	/**
	 * 发送请求
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			TrustManager[] tm = { new WeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 上传Media
	 * @param mediaType
	 * @param filePath
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static JSONObject uploadMedia(String mediaType, String filePath, String appId , String appSecret) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        String requestUrl = getUploadMediaUrl(getTokenUrl(appId,appSecret),mediaType);

        JSONObject jsonObject = null;
		try {
			TrustManager[] tm = { new WeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
		    String boundary = "----------" + System.currentTimeMillis();// 设置边界
		    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ boundary);

		    StringBuilder headBuff = new StringBuilder();
		    headBuff.append("--" + boundary + "\r\n");//必须多两道线
		    headBuff.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
		    headBuff.append("Content-Type:application/octet-stream\r\n\r\n");
	        byte[] head = headBuff.toString().getBytes("utf-8");

	        // 获得输出流
	        OutputStream out = new DataOutputStream(conn.getOutputStream());
	        // 输出表头
	        out.write(head);

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
	        in.close();

	        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义数据分隔线
			out.write(foot);
			out.flush();
			out.close();

			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
			} catch (IOException e) {
				System.out.println("发送POST请求出现异常" + e);
				e.printStackTrace();
				throw new IOException("数据读取异常");
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
    }

	public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

class WeiXinX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}