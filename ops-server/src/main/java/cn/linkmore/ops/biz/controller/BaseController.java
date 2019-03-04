package cn.linkmore.ops.biz.controller;

import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.security.response.ResPerson;


public abstract class BaseController {
	
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	
	public ObjectMapper mapper= new ObjectMapper();
	/**
	 * 获取当前用户
	 * @return
	 */
	public ResPerson getPerson() {
		return (ResPerson)SecurityUtils.getSubject().getSession().getAttribute("person");
	}
	/**
	 * 向jsonFilter增加一个key-vlaue
	 * @param jsonStr
	 * @param name
	 * @param value
	 * @return
	 */
	public String addJSONFilter(String jsonStr,String name ,Object value) {
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("property", name);
			jsonObject.put("value", value);
			
			JSONObject filterObject=null;
			JSONArray myJsonArray=null;
			if(StringUtils.isEmpty(jsonStr)) {
				filterObject=new JSONObject();
				myJsonArray=new JSONArray();
			}else {
				filterObject=new JSONObject(jsonStr);
				myJsonArray = (JSONArray) filterObject.get("filters");
			}
			myJsonArray.put(jsonObject);
			filterObject.put("filters", myJsonArray);
			return filterObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
}
