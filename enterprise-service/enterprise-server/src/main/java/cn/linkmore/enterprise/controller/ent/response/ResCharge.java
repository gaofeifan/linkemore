package cn.linkmore.enterprise.controller.ent.response;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("实时收费数据列表")
public class ResCharge {

	@ApiModelProperty(value="月时间")
	private Date date;

	@ApiModelProperty(value="实时收费详情列表")
	private List<ResChargeDetail> charge;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ResChargeDetail> getCharge() {
		return charge;
	}

	public void setCharge(List<ResChargeDetail> charge) {
		this.charge = charge;
	}
	
	
}
