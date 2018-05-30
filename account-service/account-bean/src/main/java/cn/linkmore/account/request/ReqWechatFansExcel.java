package cn.linkmore.account.request;

/**
 * 微信粉导出条件请求bean
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
public class ReqWechatFansExcel {
	private Integer binduid;
	
	private Integer bindnull;
	
	private String startTime;
	
	private String endTime;
	
	private String nickname;
	
	public Integer getBinduid() {
		return binduid;
	}
	public void setBinduid(Integer binduid) {
		this.binduid = binduid;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getBindnull() {
		return bindnull;
	}
	public void setBindnull(Integer bindnull) {
		this.bindnull = bindnull;
	} 
}
