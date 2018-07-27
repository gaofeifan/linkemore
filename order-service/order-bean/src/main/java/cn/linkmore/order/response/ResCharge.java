package cn.linkmore.order.response;

import java.util.Date;
import java.util.List;

public class ResCharge {

	private Date date;
	
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
