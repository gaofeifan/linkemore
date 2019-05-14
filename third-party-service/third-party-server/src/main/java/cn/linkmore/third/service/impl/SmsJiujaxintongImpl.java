package cn.linkmore.third.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.linkmore.third.request.ReqSms;
import cn.linkmore.util.HttpUtils;
import net.sf.json.JSONObject;

/**
 * 久佳信通-发送短信类
 * @author llh
 *
 */
@Service
public class SmsJiujaxintongImpl {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${jiujaxintongsms.uri-send}")
	private String uriSend="http://ah.jj-mob.com:8000/v2/sms/send";
	
	@Value("${jiujaxintongsms.account}")
	private String account="lmhy01";
	
	@Value("${jiujaxintongsms.password}")
	private String password="q1E00h7E";
	//private String account="lmyx01";
	//private String password="c8yyY6d1";
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 久佳信通-发送内容短信
	 * @return
	 */
	public  boolean jiujaxintongSendHttp(String dest,String content) {
		Map<String,String> headers=new HashMap<String,String>();
		Map<String,String> bodys=new HashMap<String,String>();
		String res=null;
		try {
			String ts=df.format(new Date());
			String token=DigestUtils.sha1Hex(account + password + ts);
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			bodys.put("account", account);
			bodys.put("token", token);
			bodys.put("ts", ts);
			bodys.put("dest", dest);
			bodys.put("content", content);
			bodys.put("ref", String.valueOf(System.currentTimeMillis()));
			bodys.put("ext", "");
			HttpResponse doPost = HttpUtils.doPost(uriSend, "", "", headers, null, bodys);
			res=EntityUtils.toString(doPost.getEntity(),"UTF-8");
			log.info("send sms res:{}",res); 
			if(StringUtils.isNotEmpty(res) ) {
				JSONObject obj = JSONObject.fromObject(res);
				if(obj !=null) {
					if(obj.has("status")) {
						if(obj.getInt("status")==0) {
							log.info("send sms success"); 
							return true;
						}else {
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("send sms failure");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 久佳信通-发送模版短信
	 * @param req
	 * @return
	 */
	public boolean send(ReqSms req) {
		String messageTpl = SmsServiceImpl.idAndMessage.get(req.getSt().id);
		if(req.getParam()!=null && StringUtils.isNotBlank(messageTpl)) {
			for(Map.Entry<String,String> e: req.getParam().entrySet()) {
				messageTpl=messageTpl.replace("${"+e.getKey()+"}", e.getValue());
			}
		}
		log.info(messageTpl);
		return StringUtils.isNotBlank(messageTpl)? jiujaxintongSendHttp(req.getMobile(),messageTpl):false;
	}
	
	
}
