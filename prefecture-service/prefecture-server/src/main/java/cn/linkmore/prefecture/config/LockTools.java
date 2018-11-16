package cn.linkmore.prefecture.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistoryList;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockMessage;
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
	
	private static final String[] numbers = {"一","二","三","四","五","六","七","八","九","十"};
	
	/**
	 * @Description  根据锁编号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockInfo lockInfo(String lockSn){
		String url = lockProperties.getLinkemoreLockUrl() + lockProperties.getLockInfo();
		long millis = new Date().getTime();
		Map<String, Object> parameters = new TreeMap<>();
		parameters.put("appId", lockProperties.getAppId());
		parameters.put("timestamp", millis);
		parameters.put("serialNumber", lockSn);
		log.info(JsonUtil.toJson(parameters));
		ResLockMessage data = getData(parameters,url);
		if(data != null && data.getCode() == 200) {
			@SuppressWarnings("unchecked")
			ResLockInfo info = ObjectUtils.toBean(ResLockInfo.class, (Map<String, Object>)data.getData());
			return info;
		}
		return null;
	}

	private ResLockMessage getData(Map<String,Object> param,String url) {
		String sign = Sign.getSign(param, lockProperties.getAppSecret());
		param.put("sign", sign);
		log.info(JsonUtil.toJson(param));
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(param));
		log.info(JsonUtil.toJson(resData));
		ResLockMessage message = JsonUtil.toObject(resData, ResLockMessage.class);
		return message;
	}
	
	/**
	 * @Description  查询网关信号强度
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@SuppressWarnings("unchecked")
	public ResSignalHistory lockSignalHistory(String sn) {
//		String url = lockProperties.getLinkemoreLockUrl()+lockProperties.getLockSignalHistory();
		String url = "http://open-api.linkmoreparking.cn/api/v1/lock/lock-signal-history";
		long millis = new Date().getTime();
		Map<String,Object> parameters = new TreeMap<>();
		parameters.put("appId", lockProperties.getAppId());
		parameters.put("timestamp", millis+"");
		parameters.put("serialNumber", sn);
		ResLockMessage lockMessage = getData(parameters,url);
		log.info(JsonUtil.toJson(lockMessage));
		ResSignalHistory signal = new ResSignalHistory();
		ResSignalHistoryList signallist = null;
		List<ResSignalHistoryList> signallists = new ArrayList<>();
		if(lockMessage != null && lockMessage.getCode() == 200) {
			Map<String,Object> map = (Map<String,Object>)lockMessage.getData();
			List<Object> list = (List<Object>) map.get("data");
			if(list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					signallist = new ResSignalHistoryList();
					Map<String,Object> values =(Map<String,Object>) list.get(i);
					if(values != null) {
						signallist.setCode(values.get("name").toString());
						List<Object> objs = (List<Object>) values.get("values");
						signallist.setValues(objs);
						signallist.setName("网关"+numbers[i]);
						signallists.add(signallist);
					}
				}
				signal.setList(signallists);
			}
			signal.setXalas((List<Object>)map.get("xalas"));
		}
		return signal;
	}
	
	/**
	 * @Description  升起锁  （只返回为操作成功或失败）
	 * @Author   GFF 
	 * @Version  v2.0
	 * @return true  false
	 */
	public boolean upLock(String sn) {
		ResLockMessage mes = upLockMes(sn);
		if(mes.getCode() == 200) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description  升起锁(返回具体的信息 用于自定义处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockMessage upLockMes(String sn) {
		return this.optionLock(sn, 1);
	}
	
	/**
	 * @Description  下降锁 （只返回为操作成功或失败）
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Boolean downLock(String sn) {
		ResLockMessage mes = downLockMes(sn);
		if(mes.getCode() == 200) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description  下降锁 （返回操作的具体信息  用于自定义处理）
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockMessage downLockMes(String sn) {
		return this.optionLock(sn, 0);
	}
	
	/**
	 * @return 
	 * @Description  根据分组编号查询锁列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@SuppressWarnings("unchecked")
	public List<ResLockInfo> lockListByGroupCode(String groupCode) {
		String url = lockProperties.getLinkemoreLockUrl()+lockProperties.getLocklist();
		long millis = new Date().getTime();
		Map<String, Object> map = new TreeMap<>();
		map.put("appId", lockProperties.getAppId());
		map.put("timestamp", millis);
		map.put("groupCode", groupCode);
		ResLockMessage message = getData(map,url);
		if(message.getCode() == 200) {
			return (List<ResLockInfo>)message.getData();
		}
		return null;
	
	}
	
	/**
	 * @Description  操作锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	private ResLockMessage optionLock(String sn,int optionType) {
//		String url = "http://open-api.linkmoreparking.cn/api/v1/option";
		String url = lockProperties.getLinkemoreLockUrl()+lockProperties.getLockoption();
		long millis = new Date().getTime();
		Map<String, Object> map = new TreeMap<>();
		map.put("appId", lockProperties.getAppId());
		map.put("timestamp", millis);
		map.put("action", optionType);
		map.put("serialNumber", sn);
		return getData(map,url);
	}
	
}


class Sign{
	private static Logger log = LoggerFactory.getLogger(Sign.class);
	public static String getSign(String lockSn , Long time, LockProperties lockProperties) {
		Map<String,Object> map = new TreeMap<String, Object> ();
		map.put("appId", lockProperties.getAppId());
		map.put("serialNumber", lockSn);
		map.put("timestamp", time);
		return getSign(map, lockProperties.getAppSecret());
	}
	public static String getSign(Map<String, Object> map,String appSecret) {
		StringBuilder sb = new StringBuilder(appSecret);
		Set<Entry<String,Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		log.info(sb.substring(0, sb.length()-1));
		return md5En(sb.substring(0, sb.length()-1).toLowerCase());
	}
	
	public static String md5En(String str) {
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(str.getBytes());
	        String string = new BigInteger(1, md.digest()).toString(16);
	        log.info(string);
	        return string;
	    } catch (Exception e) {
	       e.printStackTrace();
	       return null;
	    }
	}

}
