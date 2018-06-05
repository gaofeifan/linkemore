package cn.linkmore.prefecture.response;
/**
 * 响应 - 车区停车券查询结果
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPreUserAmount {
	/**
	 * 企业ID
	 */
	private Long prefectureId;
	/**
	 * 用户数
	 */
	private Long userCount; 
	
	 
	public Long getPrefectureId() {
		return prefectureId;
	}
	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	} 
	
}	
