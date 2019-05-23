package cn.linkmore.order.response;

public class ResPreOrderDetails {

	private Double orderIncome;
	
	private int orderNumber;
	
	private int orderOver;
	
	private int orderNotOver;

	public Double getOrderIncome() {
		return orderIncome;
	}

	public void setOrderIncome(Double orderIncome) {
		this.orderIncome = orderIncome;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getOrderOver() {
		return orderOver;
	}

	public void setOrderOver(int orderOver) {
		this.orderOver = orderOver;
	}

	public int getOrderNotOver() {
		return orderNotOver;
	}

	public void setOrderNotOver(int orderNotOver) {
		this.orderNotOver = orderNotOver;
	}
	
	
}
