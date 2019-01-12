package cn.linkmore.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix ="oauth22")
@Component
public class OauthConfig {
	
	/*第一次重定向获取code */
	private String wxCodeUrl;
	
	/*第一次重定向获取code */
	private String zfbCodeUrl;

	/*第二次重定向到H5页面 */
	
	private String h5Url;
	
	private String notifyUrl;
	
    private  String  parkOrder;
    
    private String sendUrl;
    
    private String orderCheckout;
  
    
	/**
	 * @return the orderCheckout
	 */
	public String getOrderCheckout() {
		return orderCheckout;
	}

	/**
	 * @param orderCheckout the orderCheckout to set
	 */
	public void setOrderCheckout(String orderCheckout) {
		this.orderCheckout = orderCheckout;
	}

	/**
	 * @return the sendUrl
	 */
	public String getSendUrl() {
		return sendUrl;
	}

	/**
	 * @param sendUrl the sendUrl to set
	 */
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	/**
	 * @return the parkOrder
	 */
	public String getParkOrder() {
		return parkOrder;
	}

	/**
	 * @param parkOrder the parkOrder to set
	 */
	public void setParkOrder(String parkOrder) {
		this.parkOrder = parkOrder;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getWxCodeUrl() {
		return wxCodeUrl;
	}

	public void setWxCodeUrl(String wxCodeUrl) {
		this.wxCodeUrl = wxCodeUrl;
	}

	public String getZfbCodeUrl() {
		return zfbCodeUrl;
	}

	public void setZfbCodeUrl(String zfbCodeUrl) {
		this.zfbCodeUrl = zfbCodeUrl;
	}

	public String getH5Url() {
		return h5Url;
	}

	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}
	
	
	

	
}