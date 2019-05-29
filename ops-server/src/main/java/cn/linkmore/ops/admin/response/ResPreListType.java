package cn.linkmore.ops.admin.response;


public class ResPreListType {

	private int orderConunt = 0;
	
	private double orderIncome = 0D;
	
	private int fixed = 0;
	
	private int fixedNumberUse = 0;

	public int getOrderConunt() {
		return orderConunt;
	}

	public void setOrderConunt(int orderConunt) {
		this.orderConunt = orderConunt;
	}

	public double getOrderIncome() {
		return orderIncome;
	}

	public void setOrderIncome(double orderIncome) {
		this.orderIncome = orderIncome;
	}

	public int getFixed() {
		return fixed;
	}

	public void setFixed(int fixed) {
		this.fixed = fixed;
	}

	public int getFixedNumberUse() {
		return fixedNumberUse;
	}

	public void setFixedNumberUse(int fixedNumberUse) {
		this.fixedNumberUse = fixedNumberUse;
	}
	
}
