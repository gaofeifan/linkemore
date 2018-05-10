package cn.linkmore.bean.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.exception.StatusEnum;

/**
 * 
 * Bean - API请求的返回结果 
 * @author liwl
 * @version 2.0
 */
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T data; 
	
	private Map<String,Object> message = null; 

	private boolean status;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	} 
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	} 

	public Map<String, Object> getMessage() {
		return message;
	}

	public void setMessage(Map<String, Object> message) {
		this.message = message;
	}
	
	/**
	 * <p>
	 * 创建响应体。
	 * 
	 * @param data
	 *            返回的相关数据
	 * @param message
	 *            提示信息
	 * @param status
	 *            是否成功
	 * @param request
	 *            request对象
	 * @return 响应体
	 */
	public static <E> ResponseEntity<E> success(E data, HttpServletRequest request) {
		ResponseEntity<E> response = new ResponseEntity<>();
		response.data = data;
		response.status = true;
		return response;
	}

	public static <E> ResponseEntity<E> fail(StatusEnum eenum, HttpServletRequest request) {
		ResponseEntity<E> response = new ResponseEntity<>(); 
		Map<String,Object> message =  new HashMap<String,Object>(); 
		message.put("code", eenum.code);
		message.put("content", eenum.label);
		response.setMessage(message);
		response.status = false;
		return response;
	}
}
