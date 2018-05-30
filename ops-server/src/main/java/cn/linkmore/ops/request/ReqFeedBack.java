package cn.linkmore.ops.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * 问题反馈请求bean
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@ApiModel("问题反馈请求bean")
public class ReqFeedBack {
	/**
	 * versionID
	 */
	private Long id;
	
	private Long userId;
	
	private String nickname;
	
	private String mobile;
	
	private String content;
	
	private Date createTime;
	
	private String startTime;
	
	private String endTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
