package cn.linkmore.order.controller.app.request;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌预约请求")
public class ReqBrandBooking {
	@ApiModelProperty(value = "车区ID", required = true)
	@Min(value=0,message="车区ID为大于0的长整数")
	@NotBlank(message="专区ID不能为空") 
	private Long prefectureId;
	
	@ApiModelProperty(value = "车牌ID", required = true)
	@Min(value=0,message="车牌ID为大于0的长整数")
	@NotBlank(message="车牌不能为空") 
	private Long plateId;
	
	@ApiModelProperty(value = "品牌ID", required = true)
	@Min(value=0,message="品牌为大于0的长整数")
	@NotBlank(message="品牌ID不能为空") 
	private Long brandId;
	
	public Long getPrefectureId() {
		return prefectureId;
	}
	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}
	public Long getPlateId() {
		return plateId;
	}
	public void setPlateId(Long plateId) {
		this.plateId = plateId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	 
}
