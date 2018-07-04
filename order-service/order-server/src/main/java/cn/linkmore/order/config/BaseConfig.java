package cn.linkmore.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "base")
@Component
public class BaseConfig { 
	private Boolean online; 
	private Integer orderNumber = 5;
	private Integer rechargeNumber = 5;
	private Integer tradeNumber = 5;
	public Boolean getOnline() {
		return online;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getRechargeNumber() {
		return rechargeNumber;
	}
	public void setRechargeNumber(Integer rechargeNumber) {
		this.rechargeNumber = rechargeNumber;
	}
	public Integer getTradeNumber() {
		return tradeNumber;
	}
	public void setTradeNumber(Integer tradeNumber) {
		this.tradeNumber = tradeNumber;
	}
	
}
