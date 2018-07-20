package cn.linkmore.enterprise.controller.app.response;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("城市品牌车区分类")
public class ResEntBrandPreCity {
	
	@ApiModelProperty(value = "城市ID")
	private Long cityId;
	
	@ApiModelProperty(value = "品牌车区列表")
	private List<ResEntBrandPre> prefectures;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public List<ResEntBrandPre> getPrefectures() {
		return prefectures;
	}

	public void setPrefectures(List<ResEntBrandPre> prefectures) {
		this.prefectures = prefectures;
	} 
}
