package cn.linkmore.prefecture.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * 锁工具类
 * @author   GFF
 * @Date     2018年11月8日
 * @Version  v2.0
 */
@Component
public class LockTools {

	private static Logger log = LoggerFactory.getLogger(LockTools.class);
	@Autowired
	private LockProperties lockProperties;
	
	/**
	 * @Description  根据锁编号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockInfo lockInfo(String lockSn){
		String url = lockProperties.getLinkemoreLockUrl() + lockProperties.getLockInfo();
		long millis = new Date().getTime();
		String sign = Sign.getSign(lockSn, millis);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("sign", sign);
		parameters.put("appId", Sign.appId);
		parameters.put("timestamp", millis+"");
		parameters.put("serialNumber", lockSn);
		log.info(JsonUtil.toJson(parameters));
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(parameters) );
		log.info(resData);
		Object object = getData(resData);
		if(object != null) {
			ResLockInfo info = ObjectUtils.toBean(ResLockInfo.class, (Map<String, Object>)object);
			return info;
		}
		return null;
	}

	private Object getData(String resData) {
		Map<String,Object> map = JsonUtil.toObject(resData, Map.class);
		if(map != null) {
			if(map.get("code").toString().equals("200")) {
				Object object = map.get("data");
				return object;
			}
		}
		return null;
	}
	
	public ResSignalHistory lockSignalHistory(String sn) {
//		String url = lockProperties.getLinkemoreLockUrl()+lockProperties.getLockSignalHistory();
		String url = "http://open-api.linkmoreparking.cn/api/v1/lock/lock-signal-history";
		long millis = new Date().getTime();
		String sign = Sign.getSign(sn, millis);
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("sign", sign);
		parameters.put("appId", Sign.appId);
		parameters.put("timestamp", millis+"");
		parameters.put("serialNumber", sn);
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(parameters));

		Object object = getData(resData);
		System.out.println(resData);
		ResSignalHistory signal = new ResSignalHistory();
		if(object != null) {
			Map<String,Object> map = (Map<String,Object>)object;
			List<Object> list = (List<Object>) map.get("data");
			if(list != null && list.size() != 0) {
				Map<String,Object> values =(Map<String,Object>) list.get(0);
				if(values != null) {
					signal.setName(values.get("name").toString());
					List<Object> objs = (List<Object>) values.get("values");
					signal.setValues(objs);
				}
			}
			signal.setXalas((List<Object>)map.get("xalas"));
		}
		return signal;
	}
	
}

class Sign{
	private static String appSecret = "1e2e90b4f56d58352bfb6119190535d7";
	public static String appId = "1d46bc15df4c4b6a9a5710183da916c1";
	
	public static String getSign(String lockSn , Long time) {
		StringBuilder sb = new StringBuilder(appSecret);
		sb.append("appId=").append(appId).append("&");
		sb.append("serialNumber=").append(lockSn).append("&");
		sb.append("timestamp=").append(time);
		return md5En(sb.toString().toLowerCase());
	}
	
	public static String md5En(String str) {
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(str.getBytes());
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	       e.printStackTrace();
	       return null;
	    }
	}

}
