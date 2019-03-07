package cn.linkmore.prefecture.core.lock;

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

import cn.linkmore.prefecture.config.LockProperties;
import cn.linkmore.prefecture.config.StartupRunner;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistoryList;
import cn.linkmore.prefecture.response.ResGatewayDetails;
import cn.linkmore.prefecture.response.ResGatewayGroup;
import cn.linkmore.prefecture.response.ResLockGatewayList;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockMessage;
import cn.linkmore.prefecture.response.ResLocksGateway;
import cn.linkmore.prefecture.response.ResUnBindLock;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * 锁工具类
 * @author   GFF
 * @Date     2018年11月8日
 * @Version  v2.0
 */
public class LockServiceImpl implements LockService{

	private static Logger log = LoggerFactory.getLogger(LockServiceImpl.class);
	private LockProperties lockProperties = StartupRunner.get().getBean(LockProperties.class);
	
//	private LockServiceImpl() {}
	
//	public static LockService getInstance() {
//		return LockServiceImplLazy.lockService;
//	}
//	
//	private static class LockServiceImplLazy{
//		private static LockServiceImpl lockService = new LockServiceImpl();
//	}
	private Map<String,Object> proToTypeMap = new TreeMap<String,Object>();
	
	public LockServiceImpl() {
		proToTypeMap.put("appId", lockProperties.getAppId());
		proToTypeMap.put("timestamp",getTime());
	}

	/**
	 * @Description  根据锁编号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockInfo lockInfo(String lockSn){
		proToTypeMap.put("serialNumber", lockSn);
		log.info(JsonUtil.toJson(proToTypeMap));
		ResLockMessage data = getDataMes(proToTypeMap,getUrl(lockProperties.getLockInfo()));
		if(data != null && data.getCode() == 200) {
			@SuppressWarnings("unchecked")
			ResLockInfo info = ObjectUtils.toBean(ResLockInfo.class, (Map<String, Object>)data.getData());
			return info;
		}
		return null;
	}

	private ResLockMessage getDataMes(Map<String,Object> param,String url) {
		String sign = SignTool.getSign(param, lockProperties.getAppSecret());
		param.put("sign", sign);
		log.info(JsonUtil.toJson(param));
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(param));
		log.info(JsonUtil.toJson(resData));
		ResLockMessage message = JsonUtil.toObject(resData, ResLockMessage.class);
		return message;
	}
	
	private Object getData(Map<String,Object> param,String url) {
		String sign = SignTool.getSign(param, lockProperties.getAppSecret());
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
			log.info("===================查询锁服务失败======================url");
		}
		return null;
	}
	/*private Object getJson(Map<String,Object> param,String url) {
		String sign = SignTool.getSign(param, lockProperties.getAppSecret());
		param.put("sign", sign);
		log.info(JsonUtil.toJson(param));
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(param));
		log.info(JsonUtil.toJson(resData));
		JSONObject fromObject = JSONObject.fromObject(resData);
		if(fromObject != null) {
			if(fromObject.get("code").toString().equals("200")) {
				Object object = fromObject.get("data");
				return object;
			}
			log.info("===================查询锁服务失败======================url");
		}
		return null;
	}*/
	private ResLockMes get(Map<String,Object> param,String url) {
		String sign = SignTool.getSign(param, lockProperties.getAppSecret());
		param.put("sign", sign);
		log.info(JsonUtil.toJson(param));
		log.info("【LOCK URL】 "+url);
		String resData = HttpUtil.sendJson(url, JsonUtil.toJson(param));
		log.info(JsonUtil.toJson(resData));
		Map<String,Object> map = JsonUtil.toObject(resData, Map.class);
		if(map != null) {
			if(map.get("code").toString().equals("200")) {
				Object object = map.get("data");
				return new ResLockMes(true, object);
			}
		}
		return new ResLockMes(false, null);
	}
	
	
	/**
	 * @Description  查询网关信号强度
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@SuppressWarnings("unchecked")
	public ResSignalHistory lockSignalHistory(String sn) {
		proToTypeMap.put("serialNumber", sn);
		ResLockMessage lockMessage = getDataMes(proToTypeMap,getUrl(lockProperties.getLockSignalHistory().trim()));
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
		long millis = new Date().getTime();
		Map<String, Object> map = new TreeMap<>();
		map.put("appId", lockProperties.getAppId());
		map.put("timestamp", millis);
		map.put("groupCode", groupCode);
		ResLockMessage resLockMessage = getDataMes(map,getUrl(lockProperties.getLocklist()));
		List<Map<String,Object>> lockInfos = (List<Map<String, Object>>)resLockMessage.getData();
		List<ResLockInfo> resLockInfos = new ArrayList<>();
		ResLockInfo info = null;
		for (Map<String,Object> obj : lockInfos) {
			info = new ResLockInfo();
			info.setElectricity(Integer.parseInt(obj.get("electricity").toString()));
			info.setGatewaySum(Integer.parseInt(obj.get("gatewaySum").toString()));
			info.setInductionState(Integer.parseInt(obj.get("inductionState").toString()));
			info.setLockCode(obj.get("lockCode").toString());
			info.setLockState(Integer.parseInt(obj.get("lockState").toString()));
			info.setModel(obj.get("model").toString());
			info.setOptionCount(Integer.parseInt(obj.get("optionCount").toString()));
			info.setOptionSuccessCount(Integer.parseInt(obj.get("optionSuccessCount").toString()));
			info.setParkingState(Integer.parseInt(obj.get("parkingState").toString()));
			info.setVersion(obj.get("version").toString());
			if(obj.get("onlineState") != null) {
				info.setOnlineState(Integer.parseInt(obj.get("onlineState").toString()));
			}
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
		proToTypeMap.put("action", optionType);
		proToTypeMap.put("serialNumber", sn);
		return getDataMes(proToTypeMap,getUrl(lockProperties.getLockoption()));
	}
	
	/**
	 * @Description  同步锁平台
	 * @Author   cl 
	 */
	public void setLockName(Map<String, Object> map ) {
		proToTypeMap.put("appId", lockProperties.getAppId());
		Object object = getData(proToTypeMap,getUrl(LockProperties.getSetparkingname()));
	}
	
	/**
	 * @Description   ops的车场用与锁服务的车区同步
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@Override
	public String saveGroup(String groupName,String cityCode,String cityName,Double longitude,Double latitude,Integer positionNum) {
		proToTypeMap.put("groupName", groupName);
		proToTypeMap.put("cityCode", cityCode);
		proToTypeMap.put("cityName", cityName);
		proToTypeMap.put("longitude", longitude);
		proToTypeMap.put("latitude", latitude);
		proToTypeMap.put("positionNum", positionNum);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getSaveGroup()));
		if(lockMes.getStatus()) {
			return lockMes.getObj().toString();
		}
		return null;
	}
	
	/**
	 * @Description  删除车区信息
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@Override
	public Boolean removeGroupCode(String groupCode) {
		proToTypeMap.put("groupCode", groupCode);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getRemoveGroupCode()));
		return lockMes.getStatus();
	}
	
	/**
	 * @Description  绑定网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@Override
	public Boolean bindGroup(String groupCode,String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		proToTypeMap.put("groupCode", groupCode);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getBindGroup()));
		if(lockMes.getStatus()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description  解除绑定
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@Override
	public Boolean unbindGroup(String groupCode,String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		proToTypeMap.put("groupCode", groupCode);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.unBindGroup()));
		if(lockMes.getStatus()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description  加载锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@Override
	public Boolean loadAllLock(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.loadAllLocks()));
		return lockMes.getStatus();
	}
	
	/**
	 * @Description  查询网关详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResGatewayDetails getGatewayDetails(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		Object data = getData(proToTypeMap, getUrl(LockProperties.getSerialNumber()));
		ResGatewayDetails resGatewayDetails = ObjectUtils.mapToBean(ResGatewayDetails.class, (Map<String,Object>)data);
		return resGatewayDetails;
	}
	
	@Override
	public List<ResGatewayGroup> getGatewayGroup(String groupCode) {
		proToTypeMap.put("groupCode", groupCode);
		Object data = getData(proToTypeMap, getUrl(LockProperties.getGatewayGroup()));
		List<Map<String,Object>> list = (List<Map<String,Object>>)data;
		List<ResGatewayGroup> listBean = ObjectUtils.mapsToListBean(ResGatewayGroup.class, list);
		return listBean;
	}

	private String getUrl(String url) {
		return lockProperties.getLinkemoreLockUrl() + url;
	}
	private Long getTime() { 
		return new Date().getTime();
	}

	@Override
	public List<ResUnBindLock> unBindLockList(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		Object data = getData(proToTypeMap, getUrl(LockProperties.getUnbindLockList()));
		List<Map<String,Object>> list = (List<Map<String,Object>>)data;
		List<ResUnBindLock> listBean = ObjectUtils.mapsToListBean(ResUnBindLock.class, list);
		return listBean;
	}

	@Override
	public List<ResUnBindLock> bindLockList(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		Object data = getData(proToTypeMap, getUrl(LockProperties.getBindLockList()));
		List<Map<String,Object>> list = (List<Map<String,Object>>)data;
		List<ResUnBindLock> listBean = ObjectUtils.mapsToListBean(ResUnBindLock.class, list);
		return listBean;
	}

	@Override
	public Boolean bindLock(String gatewaySerialNumbe, String lockSerialNumber) {
		proToTypeMap.put("gatewaySerialNumbe", gatewaySerialNumbe);
		proToTypeMap.put("lockSerialNumber", lockSerialNumber);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getBindLock()));
		return lockMes.getStatus();
	}

	@Override
	public Boolean unBindLock(String gatewaySerialNumbe, String lockSerialNumber) {
		proToTypeMap.put("gatewaySerialNumbe", gatewaySerialNumbe);
		proToTypeMap.put("lockSerialNumber", lockSerialNumber);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getUnBindLock()));
		return lockMes.getStatus();
	}

	@Override
	public List<ResLocksGateway> getLocksGateway(String SerialNumber) {
		proToTypeMap.put("gatewaySerialNumber", SerialNumber);
		Object data = getData(proToTypeMap, getUrl(LockProperties.getLockGateway()));
		List<Map<String,Object>> list = (List<Map<String,Object>>)data;
		List<ResLocksGateway> listBean = ObjectUtils.mapsToListBean(ResLocksGateway.class, list);
		return listBean;
	}

	@Override
	public List<ResLockGatewayList> getLockGatewayList(String SerialNumber,String groupCode) {
		proToTypeMap.put("serialNumber", SerialNumber);
		proToTypeMap.put("groupCode", groupCode);
		Object data = getData(proToTypeMap, getUrl(LockProperties.getLockGatewayList()));
		if(data == null) {
			return new ArrayList<>();
		}
		List<Map<String,Object>> list = (List<Map<String,Object>>)data;
		List<ResLockGatewayList> listBean = ObjectUtils.mapsToListBean(ResLockGatewayList.class, list);
		return listBean;
	}

	@Override
	public Boolean restart(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getRestart()));
		return lockMes.getStatus();
	}

	@Override
	public Boolean batchBindGateway(String lockSerialNumber, String gatewaySerials) {
		proToTypeMap.put("lockSerialNumber", lockSerialNumber);
		proToTypeMap.put("gatewaySerials", gatewaySerials);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getBatchBindGateway()));
		return lockMes.getStatus();
	}

	@Override
	public Boolean removeLock(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getRemoveLock()));
		return lockMes.getStatus();
	}

	@Override
	public Boolean confirm(String serialNumber) {
		proToTypeMap.put("serialNumber", serialNumber);
		ResLockMes lockMes = get(proToTypeMap, getUrl(LockProperties.getConfirm()));
		return lockMes.getStatus();
	}
	
}

class SignTool{
	private static Logger log = LoggerFactory.getLogger(SignTool.class);
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
		return MD5Tool.md5En(sb.substring(0, sb.length()-1).toLowerCase());
	}
}
class MD5Tool{
	private static Logger log = LoggerFactory.getLogger(MD5Tool.class);
	public static String md5En(String str) {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(str);
        log.info("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }
}
