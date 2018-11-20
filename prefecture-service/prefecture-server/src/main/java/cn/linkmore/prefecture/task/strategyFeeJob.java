package cn.linkmore.prefecture.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.linkmore.prefecture.dao.master.StrategyFeeMasterMapper;
import cn.linkmore.prefecture.entity.StrategyFee;
import cn.linkmore.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class strategyFeeJob {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StrategyFeeMasterMapper strategyFeeMasterMapper;
	
	
	//@Value("${strategyFeeURL}")
	@Value("${strategyfee.url.list}")
	private String strategyFeeListURL;//="http://192.168.1.76:8086/charge/api/get_charge_list";
	
	@Value("${strategyfee.fee-code}")
	private String strategyFeeCode;//="987656";
	
	@Value("${strategyfee.fee-secret}")
	private String strategyFeesSecret;//="99876505523";
	
	@Scheduled(cron = "0 0/10 * * * *")
	public void remoteGetFee() {
		//System.out.println("remoteGetFee");
		String jsonRes=httpGetFeeList();
		log.error("strategyFeeListURL={}",strategyFeeListURL);
		log.error("jsonRes={}",jsonRes);
		if(StringUtils.isNotEmpty(jsonRes) ) {
			JSONObject obj = JSONObject.fromObject(jsonRes);
			if(obj !=null) {
				if(obj.has("code")) {
					if(StringUtils.equalsIgnoreCase("200", obj.getString("code")) ) {
						if(obj.has("data")) {
							JSONArray jsonArray = obj.getJSONArray("data");
							List<StrategyFee> listStrategyFee = JSONArray.toList(jsonArray, new StrategyFee(), new JsonConfig());
							if(listStrategyFee.size()>0) {
								strategyFeeMasterMapper.deleteAll();
								for(StrategyFee strategyFee:listStrategyFee ) {
									strategyFee.setStatus((byte) 0);
									strategyFee.setUpdateTime(new Date());
									strategyFeeMasterMapper.insert(strategyFee);
								}
							}
						}
					}
				}
			}
		}
	}

	private String httpGetFeeList() {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=utf-8");
		
		Map<String,String> mapBody=new TreeMap<String, String>();
		mapBody.put("code", strategyFeeCode);
		mapBody.put("timestamp",String.valueOf(new Date().getTime()));
		mapBody.put("sign", "324");
		
		JSONObject json = JSONObject.fromObject(mapBody);
		try {
			HttpResponse r=HttpUtils.doPost(strategyFeeListURL, "", "", headers, null, json.toString());
			return EntityUtils.toString(r.getEntity(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
