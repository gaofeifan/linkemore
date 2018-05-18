package cn.linkmore.prefecture.request;
/**
 * 请求城市id
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqCity {

	/**
	 * 城市
	 */
    private Long cityId;

    /**
     * 用户id
     */
    private Long userId;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
}
