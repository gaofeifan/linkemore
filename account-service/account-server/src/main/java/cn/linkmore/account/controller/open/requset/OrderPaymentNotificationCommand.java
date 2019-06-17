package cn.linkmore.account.controller.open.requset;

import java.util.Map;

import com.everhomes.discover.ItemType;

public class OrderPaymentNotificationCommand {
    private Integer orderType;
    private Long orderId;
    private String bizOrderNum;
    
    private Integer paymentType;
    @ItemType(value=String.class)
    private Map<String, String> paymentParams;
    
    private Long payerUserId;
    private Long amount;
    private Long remainAmount;
    private Integer refundDestination;
    private String payDatetime;
    private String extendInfo;
    
    private Integer paymentStatus;
    private String paymentErrorCode;
    private String paymentMessage;
    
    private String signature;
    
    public OrderPaymentNotificationCommand() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBizOrderNum() {
        return bizOrderNum;
    }

    public void setBizOrderNum(String bizOrderNum) {
        this.bizOrderNum = bizOrderNum;
    }

    public Long getPayerUserId() {
        return payerUserId;
    }

    public void setPayerUserId(Long payerUserId) {
        this.payerUserId = payerUserId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentErrorCode() {
        return paymentErrorCode;
    }

    public void setPaymentErrorCode(String paymentErrorCode) {
        this.paymentErrorCode = paymentErrorCode;
    }

    public String getPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public Long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getRefundDestination() {
        return refundDestination;
    }

    public void setRefundDestination(Integer refundDestination) {
        this.refundDestination = refundDestination;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Map<String, String> getPaymentParams() {
        return paymentParams;
    }

    public void setPaymentParams(Map<String, String> paymentParams) {
        this.paymentParams = paymentParams;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

	@Override
	public String toString() {
		return "OrderPaymentNotificationCommand [orderType=" + orderType + ", orderId=" + orderId + ", bizOrderNum="
				+ bizOrderNum + ", paymentType=" + paymentType + ", paymentParams=" + paymentParams + ", payerUserId="
				+ payerUserId + ", amount=" + amount + ", remainAmount=" + remainAmount + ", refundDestination="
				+ refundDestination + ", payDatetime=" + payDatetime + ", extendInfo=" + extendInfo + ", paymentStatus="
				+ paymentStatus + ", paymentErrorCode=" + paymentErrorCode + ", paymentMessage=" + paymentMessage
				+ ", signature=" + signature + "]";
	}
    
}
