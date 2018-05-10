package cn.linkmore.bean.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 通用类 - 响应封装 - 消息
 * @author liwenlong
 * @version 1.0
 *
 */
public class ViewMsg  implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -7253419067522042895L;
	private Boolean success;
	private String content;
	private Map<String,Object> map = new HashMap<String,Object>();
	 
	public ViewMsg(String content,Boolean success){
		this.content = content;
		this.success = success;
	}
	public Boolean getSuccess() {
		return success;
	} 
	public void setSuccess(Boolean success) {
		this.success = success;
	} 
	public String getContent() {
		return content;
	} 
	public void setContent(String content) {
		this.content = content;
	} 
	public Map<String, Object> getMap() {
		return map;
	} 
	public void setMap(Map<String, Object> map) {
		this.map = map;
	} 
	public void add(String key,Object value){
        this.map.put(key, value);
    }
	
}
