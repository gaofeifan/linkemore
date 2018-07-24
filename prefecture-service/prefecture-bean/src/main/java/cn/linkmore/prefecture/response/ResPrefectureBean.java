package cn.linkmore.prefecture.response;

import java.io.Serializable;
import java.util.List;

/**
 * 专区列表响应
 * @author jiaohanbin
 * @version 2.0
 *
 */

public class ResPrefectureBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 车区名称
	 */
	private String name;
	/**
	 * 车位总数
	 */
	private int stallTotal;
	/**
	 * 预约车位数
	 */
	private int occupyStall;
	
	/**
	 * 空闲车位数
	 */
	private int freeStall;
	/**
	 * 异常车位数
	 */
	private int exceptionStall;
	/**
	 * 故障车位数
	 */
	private int faultStall;
	/**
	 * 车位列表
	 */
	//private List<StallStateResponseBean> list;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStallTotal() {
		return stallTotal;
	}

	public void setStallTotal(int stallTotal) {
		this.stallTotal = stallTotal;
	}

	public int getOccupyStall() {
		return occupyStall;
	}

	public void setOccupyStall(int occupyStall) {
		this.occupyStall = occupyStall;
	}

	public int getFreeStall() {
		return freeStall;
	}

	public void setFreeStall(int freeStall) {
		this.freeStall = freeStall;
	}

	public int getExceptionStall() {
		return exceptionStall;
	}

	public void setExceptionStall(int exceptionStall) {
		this.exceptionStall = exceptionStall;
	}

	public int getFaultStall() {
		return faultStall;
	}

	public void setFaultStall(int faultStall) {
		this.faultStall = faultStall;
	}
	
}
