package cn.linkmore.enterprise.controller.app.response;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("长租车位授权列表-可同时降多把车位锁，使用多个车位")
public class ResHaveRentList {

	@ApiModelProperty(value = "可用车位数量")
	private int num = 0;
	
	@ApiModelProperty(value = "长租车区列表")
	private List<ResHaveRentPre> rentPres;
	
	public int getNum() {
		if (CollectionUtils.isNotEmpty(rentPres)) {
			int n = 0;
			for (ResHaveRentPre haveRentPre : rentPres) {
				for (ResHaveRentPreStall stall : haveRentPre.getRentPreStalls()) {
					n++;
				}
			}
			return num = n;
		}
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ResHaveRentPre> getRentPres() {
		return rentPres;
	}

	public void setRentPres(List<ResHaveRentPre> rentPres) {
		this.rentPres = rentPres;
	}
}
