package cn.linkmore.coupon.controller.ops;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;
import cn.linkmore.coupon.service.TemplateConditionService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.redis.RedisService;

@Controller
@RequestMapping("/ops/coupon_template_condition")
public class TemplateConditionController {

	@Autowired
	private TemplateConditionService templateConditionService;
	
	@Resource
	private RedisService redisService;
	
	@Autowired
	private PrefectureClient preClient;
	
	@Resource
	private CityClient cityClient;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplateCondition record) {
		return this.templateConditionService.save(record);
	}

	/**
	 * 禁用和启用
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplateCondition record) {
		return	this.templateConditionService.update(record);
	}

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return	this.templateConditionService.delete(ids.get(0));
	}
	
	@RequestMapping(value = "/v2.0/setDefault", method = RequestMethod.GET)
	@ResponseBody
	public int setDefault(@RequestParam("id") Long id) {
		return this.templateConditionService.setDefault(id);
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.templateConditionService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.templateConditionService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/condition_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResTemplateCondition> conditionList(@RequestParam("tempId") Long tempId) {
		List<ResTemplateCondition> list = this.templateConditionService.findConditionList(tempId);
		return list;
	}
	
	public String getXqEn(int xqNum){
		Map<Integer,String> xqMap = new HashMap<Integer,String>();
		xqMap.put(1,"星期一"); 
		xqMap.put(2,"星期二"); 
		xqMap.put(3,"星期三"); 
		xqMap.put(4,"星期四"); 
		xqMap.put(5,"星期五"); 
		xqMap.put(6,"星期六"); 
		xqMap.put(7,"星期七"); 
		String xqEn = xqMap.get(xqNum);
		return xqEn;
	}
	
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResTemplateCondition detail(@RequestParam("id") Long id){
		ResTemplateCondition condition = this.templateConditionService.findById(id);
		String useTimeJson = "";
		String useTime = "";
		String preId = "";
		String preName ="";
		Map<Long,Object> cityMap = new HashMap<Long,Object>();
		List<ResCity> cityList = this.cityClient.findSelectList();
		for(ResCity city: cityList){
			cityMap.put(city.getId(), city.getCityName());
		}
		
		if(condition.getAvailableTime().equals(2)){
			useTimeJson = (String) redisService.get(Constants.RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key + condition.getId());
			JSONObject jsonObject = JSONObject.parseObject(useTimeJson);
			if (jsonObject != null) {
				String startDate = (String) jsonObject.get("zdStartDate");
				String endDate = (String) jsonObject.get("zdEndDate");
				String startTime = (String) jsonObject.get("zdStartTime");
				String endTime = (String) jsonObject.get("zdEndTime");
				useTime = "开始日期时间：" + startDate + " "+ startTime +"<br>" + "结束日期时间：" + endDate + " "+ endTime +"<br>";;
			}
		}
		if(condition.getAvailableTime().equals(1)){
			useTimeJson = (String) this.redisService.get(Constants.RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key + condition.getId());
			JSONObject jsonObject = JSONObject.parseObject(useTimeJson);
			if (jsonObject != null) {
				String start = (String) jsonObject.get("start");
				String end = (String) jsonObject.get("end");
				String time = (String) jsonObject.get("time");
				useTime = getXqEn(Integer.valueOf(start)) + " - " + getXqEn(Integer.valueOf(end)) +"<br>";
				JSONArray jsonArray = JSONArray.parseArray(time);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					String startTime = (String) json.get("startTime");
					String endTime = (String) json.get("endTime");
					useTime += startTime + "    " +endTime +"<br>";
				}
			}
		}
		if(!condition.getAvailablePrefecture().equals(0)){
			preId = (String) this.redisService.get(Constants.RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key + condition.getId());
			String [] preIds = preId.split(",");
			for(int i=0;i<preIds.length;i++){
				ResPrefectureDetail pre = this.preClient.findById(Long.valueOf(preIds[i]));
				preName += cityMap.get(pre.getCityId()) +" - "+ pre.getName() + "<br>";
			}
		}
		condition.setPreName(preName);
		condition.setUseTime(useTime);
		return condition;
	}

}
