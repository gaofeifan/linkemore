package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.BluetoothDataClusterMapper;
import cn.linkmore.prefecture.dao.master.BluetoothDataMasterMapper;
import cn.linkmore.prefecture.entity.BluetoothData;
import cn.linkmore.prefecture.response.ResBluetoothData;
import cn.linkmore.prefecture.service.BluetoothDataService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.TokenUtil;

/**
 * 蓝牙上报数据信息
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class BluetoothDataServiceImpl implements BluetoothDataService {
	@Autowired
	private BluetoothDataClusterMapper bluetoothDataClusterMapper;
	
	@Autowired
	private BluetoothDataMasterMapper bluetoothDataMasterMapper;
	
	@Autowired
	private RedisService redisService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty().replaceAll(" ", "")));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.bluetoothDataClusterMapper.count(param);
		List<ResBluetoothData> list = new ArrayList<ResBluetoothData>();
		if(count>0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.bluetoothDataClusterMapper.findPage(param);
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public int save(BluetoothData blue) {
		return this.bluetoothDataMasterMapper.save(blue);
	}

	@Override
	public Boolean saveData(String param, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Boolean flag = false;
		BluetoothData bluetooth = new BluetoothData();
		if(cu != null) {
			bluetooth.setUserId(cu.getId());
		}
		String [] array = param.split("/");
		for(String property : array) {
			System.out.println("========="+property);
			if(property.contains("locksn")) {
				bluetooth.setLockSn(property.split("-")[1]);
			}
			if(property.contains("search")) {
				bluetooth.setSearch(property.split("-")[1].trim());
			}
			if(property.contains("matching")) {
				bluetooth.setMatching(property.split("-")[1].trim());
			}
			if(property.contains("signaling")) {
				bluetooth.setSignaling(property.split("-")[1].trim());
			}
			if(property.contains("connect")) {
				bluetooth.setConnect(property.split("-")[1].trim());
			}
			if(property.contains("send")) {
				bluetooth.setSend(property.split("-")[1].trim());
			}
			if(property.contains("success")) {
				bluetooth.setSuccess(property.split("-")[1].trim());
			}
			if(property.contains("count")) {
				bluetooth.setCount(Integer.valueOf(property.split("-")[1]));
			}
			if(property.contains("disconnect")) {
				bluetooth.setDisconnect(property.split("-")[1]);
			}
			if(property.contains("disconnects")) {
				bluetooth.setDisconnects(property.split("-")[1]);
			}
		}
		
		bluetooth.setCreateTime(new Date());
		log.info("bluetooth = {}",JSON.toJSON(bluetooth));
		int integer = this.bluetoothDataMasterMapper.save(bluetooth);
		if(integer > 0) {
			flag = true;
		}
		return flag;
	}
}
