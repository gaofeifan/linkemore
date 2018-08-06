package cn.linkmore.enterprise.request;

import java.io.Serializable;

/**
 * @author   cl
 * @Date     2018年8月1日
 * @Version  v2.0
 */
public class ReqVipUser implements Serializable{

	
	private Long entId;

    private Long entPreId;
	
    private Long preId;
	
    private String mobile;
	
    private String realname;
	
    private String plate;
    
    private Long userId;


	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public Long getEntPreId() {
		return entPreId;
	}

	public void setEntPreId(Long entPreId) {
		this.entPreId = entPreId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
