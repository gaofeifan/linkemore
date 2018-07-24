package cn.linkmore.order.response;
import java.util.Date;
public class ResTrafficFlow {
	private Date date;

	private Integer carDayTotal;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCarDayTotal() {
		return carDayTotal;
	}

	public void setCarDayTotal(Integer carDayTotal) {
		this.carDayTotal = carDayTotal;
	}

}
