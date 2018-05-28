package cn.linkmore.ops.response;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户分页响应Bean
 * @author   GFF
 * @Date     2018年5月26日
 * @Version  v2.0
 */
public class ResPageUser implements Serializable {
	/**
	 * versionID
	 */
	private static final long serialVersionUID = 6304174158724690314L;
	private Long id;
	private String nickName;
	private String mobile;
	private String cityName;
	private int orderCount;
	private Date loginTime;
	private Date ordersTime;
	private int userStatus;
	private String plateNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getOrdersTime() {
		return ordersTime;
	}

	public void setOrdersTime(Date ordersTime) {
		this.ordersTime = ordersTime;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

}
