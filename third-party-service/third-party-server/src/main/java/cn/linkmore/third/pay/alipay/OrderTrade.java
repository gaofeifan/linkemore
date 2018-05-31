package cn.linkmore.third.pay.alipay;

import java.util.Map;

public class OrderTrade {

	//out_trade_no
	//外部订单号 集成系统生成的
	private String outTradeNo;
	
	//total_amount
	private String totalAmount;
	
	//timestamp
	private String timestamp;

	
	/**
	 * 校验通知中订单信息正确性
	 * @param paramMap
	 * @return
	 */
	public boolean checkOrder(Map<String, String> reqMap) {
		String outTradeNo = reqMap.get("out_trade_no");
		String totalAmount = reqMap.get("total_amount");
		if(null!=this.outTradeNo && this.outTradeNo.equals(outTradeNo))
			if(null!=this.totalAmount && this.totalAmount.equals(totalAmount))
				return true;
		return false;
	}
	
	
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	
}
