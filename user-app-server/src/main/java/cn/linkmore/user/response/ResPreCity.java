package cn.linkmore.user.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("城市车区分类")
public class ResPreCity {
	
	@ApiModelProperty(value = "城市ID")
	private Long cityId;
	
	@ApiModelProperty(value = "车区列表")
	private List<ResPrefecture> prefectures;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public List<ResPrefecture> getPrefectures() {
		return prefectures;
	}

	public void setPrefectures(List<ResPrefecture> prefectures) {
		this.prefectures = prefectures;
	} 
}
