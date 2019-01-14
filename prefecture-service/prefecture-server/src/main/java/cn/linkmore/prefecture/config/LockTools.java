package cn.linkmore.prefecture.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
@Component(value="lockTools")
@Primary
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
		ResLockMessage data = getDataMes(parameters,url);
		if(data != null && data.getCode() == 200) {
			@SuppressWarnings("unchecked")
			ResLockInfo info = ObjectUtils.toBean(ResLockInfo.class, (Map<String, Object>)data.getData());
			return info;
		}
		return null;
	}

	private ResLockMessage getDataMes(Map<String,Object> param,String url) {
		String sign = Sign.getSign(param, lockProperties.getAppSecret());
		param.put("sign", sign);
		log.info(JsonUtil.toJson(param));
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(param));
		log.info(JsonUtil.toJson(resData));
		ResLockMessage message = JsonUtil.toObject(resData, ResLockMessage.class);
		return message;
	}
	
	private Object getData(Map<String,Object> param,String url) {
		String sign = Sign.getSign(param, lockProperties.getAppSecret());
		param.put("sign", sign);
		log.info(JsonUtil.toJson(param));
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(param));
		log.info(JsonUtil.toJson(resData));
		Map<String,Object> map = JsonUtil.toObject(resData, Map.class);
		if(map != null) {
			if(map.get("code").toString().equals("200")) {
				Object object = map.get("data");
				return object;
			}
		}
		return null;
	}
	
	
	/**
	 * @Description  查询网关信号强度
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@SuppressWarnings("unchecked")
	public ResSignalHistory lockSignalHistory(String sn) {
		String url = lockProperties.getLinkemoreLockUrl()+lockProperties.getLockSignalHistory().trim();
		long millis = new Date().getTime();
		Map<String,Object> parameters = new TreeMap<>();
		parameters.put("appId", lockProperties.getAppId());
		parameters.put("timestamp", millis+"");
		parameters.put("serialNumber", sn);
		ResLockMessage lockMessage = getDataMes(parameters,url);
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
						String name = values.get("name").toString();
						signallist.setCode(name);
						List<Object> objs = (List<Object>) values.get("values");
						signallist.setValues(objs);
						signallist.setName(name != null && name.length() >=4 ? name.substring(name.length() - 4) : null );
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
		ResLockMessage resLockMessage = getDataMes(map,url);
		List<Map<String,Object>> lockInfos = (List<Map<String, Object>>)resLockMessage.getData();
		List<ResLockInfo> resLockInfos = new ArrayList<>();
		ResLockInfo info = null;
		for (Map<String,Object> obj : lockInfos) {
			info = new ResLockInfo();
			info.setElectricity(Integer.decode(obj.get("electricity").toString()));
			info.setGatewaySum(Integer.decode(obj.get("gatewaySum").toString()));
			info.setInductionState(Integer.decode(obj.get("inductionState").toString()));
			info.setLockCode(obj.get("lockCode").toString());
			info.setLockState(Integer.decode(obj.get("lockState").toString()));
			info.setModel(obj.get("model").toString());
			info.setOptionCount(Integer.decode(obj.get("optionCount").toString()));
			info.setOptionSuccessCount(Integer.decode(obj.get("optionSuccessCount").toString()));
			info.setParkingState(Integer.decode(obj.get("parkingState").toString()));
			info.setVersion(obj.get("version").toString());
			resLockInfos.add(info);
		}
		return resLockInfos;
	
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
		return getDataMes(map,url);
	}
	
	/**
	 * @Description  同步锁平台
	 * @Author   cl 
	 */
	public void setLockName(Map<String, Object> map ) {
		String url = lockProperties.getLinkemoreLockUrl()+lockProperties.getSetparkingname();
		long millis = new Date().getTime();
		map.put("appId", lockProperties.getAppId());
		map.put("timestamp", millis);
		Object object = getData(map,url);
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
		return MD5.md5En(sb.substring(0, sb.length()-1).toLowerCase());
	}
}

class MD5{
	private static Logger log = LoggerFactory.getLogger(MD5.class);
	public static String md5En(String str) {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(str);
        log.info("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }
}
