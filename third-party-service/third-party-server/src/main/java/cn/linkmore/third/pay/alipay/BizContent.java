package cn.linkmore.third.pay.alipay;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BizContent {

	/**
	 * 否
	 * 128
	 * 对一笔交易的具体描述信息。
	 * 如果是多种商品，请将商品描述字符串累加传给body。
	 */
	@JsonProperty("body")
	private String body="凌猫停车";
	/**
	 * 是
	 * 256
	 * 商品的标题/交易标题/订单标题/订单关键字等。
	 */
	@JsonProperty("subject")
	private String subject="凌猫停车";
	/**
	 * 
	 * 商户网站唯一订单号
	 * 
	 */
	@JsonProperty("out_trade_no")
	private String outTradeNo;
	/**
	 * 该笔订单允许的最晚付款时间，逾期将关闭交易。
	 * 取值范围：1m～15d。
	 * m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
	 * 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
	 */
	@JsonProperty("timeout_express")
	private String timeoutExpress="20m";
	
	/**
	 * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
	 */
	@JsonProperty("total_amount")
	private String totalAmount;
	
	/**
	 * 
	 * 收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
	 * 
	 * 2088102147948060
	 * 
	 */
	@JsonInclude(Include.NON_NULL) 
	@JsonProperty("seller_id")
	private String sellerId;
	/**
	 *
	 * 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
	 * QUICK_MSECURITY_PAY
	 * 
	 */
	@JsonProperty("product_code")
	private String productCode="QUICK_MSECURITY_PAY";
	/**
	 * 商品主类型：0—虚拟类商品，1—实物类商品
	 * 注：虚拟类商品不支持使用花呗渠道
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("goods_type")
	private String goodsType;//="0"
	
	/**
	 * 
	 * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。
	 * 支付宝会在异步通知时将该参数原样返回。
	 * 本参数必须进行UrlEncode之后才可以发送给支付宝
	 * merchantBizType%3d3C%26merchantBizNo%3d2016010101111
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("passback_params")
	private String passbackParams;
	/**
	 * 
	 * 优惠参数
	 * 注：仅与支付宝协商后可用
	 * {"storeIdType":"1"}
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("promo_params")
	private String promoParams;
	/**
	 * 
	 * 业务扩展参数，详见下面的“业务扩展参数说明”
	 * {"sys_service_provider_id":"2088511833207846"}
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("extend_params")
	private String extendParams;
	
	/**
	 * 可用渠道，用户只能在指定渠道范围内支付
	 * 当有多个渠道时用“,”分隔
	 * 注：与disable_pay_channels互斥
	 * pcredit,moneyFund,debitCardExpress
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("enable_pay_channels")
	private String enablePayChannels;
	
	/**
	 * 禁用渠道，用户不可用指定渠道支付
	 * 当有多个渠道时用“,”分隔
	 * 注：与enable_pay_channels互斥
	 * 
	 * pcredit,moneyFund,debitCardExpress
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disable_pay_channels")
	private String disablePayChannels;
	
	public BizContent(String outTradeNo, String totalAmount) {
		this.outTradeNo = outTradeNo;
		this.totalAmount = totalAmount;
	}

	public BizContent(String outTradeNo, String totalAmount,String body) {
		this.body=body;
		this.outTradeNo = outTradeNo;
		this.totalAmount = totalAmount;
	}
	
	public BizContent(String outTradeNo, String totalAmount,String body ,String subject) {
		this.body=body;
		this.subject =subject;
		this.outTradeNo = outTradeNo;
		this.totalAmount = totalAmount;
	}
	
	public BizContent() {
		super();
	}
}
