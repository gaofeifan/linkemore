package cn.linkmore.order.entity;

import java.io.Serializable;

public class ResStaffDataCountVo<T> implements Serializable{

	/**
	 *  
	 */ 
	private static final long serialVersionUID = 1L;

	private String floor;
	
	private Integer type;

	private T t;

	public ResStaffDataCountVo(String floor, Integer type, T t) {
		super();
		this.floor = floor;
		this.type = type;
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
