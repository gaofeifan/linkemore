package cn.linkmore.bean;

import java.io.Serializable;

/**
 * 请求过虑 - 多查询条件封装
 * @author liwenlong
 * @version 1.0
 *
 */
public class ViewFilter implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 660051899365827405L;

	/**
	 *  属性
	 **/
	private String property; 

	/**
	 *  值 
	 **/
	private Object value;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	} 
}
