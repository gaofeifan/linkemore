package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.master.OrdersDetailMasterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.entity.Account;
import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.entity.OrdersDetail;
import cn.linkmore.order.entity.RechargeRecord;
import cn.linkmore.order.entity.WalletDetail;
import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.order.service.PayService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StrategyBaseClient;
/**
 * Service实现 - 支付
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class PayServiceImpl implements PayService {
	@Autowired
	private StallClient stallClient; 
	@Autowired
	private OrdersClusterMapper ordersClusterMapper;
	@Autowired
	private OrdersDetailMasterMapper ordersDetailMasterMapper;
	@Autowired
	private OrdersMasterMapper orderMasterMapper; 
	
	@Autowired
	private StrategyBaseClient strategyBaseClient;
	
	private CouponClient couponClient;

	@Override
	public ResOrderCheckout checkout(Long orderId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public ResOrderConfirm confirm(ReqOrderConfirm roc) {
		Coupon coupon = null;
		 
		// 校验参数
		coupon = checkParams(bean, u, coupon);
		Orders order = orderService.getById(bean.getId());
		// 调用计费策略
		Map<String,Object> rm = orderService.checkOut(order, u);
		Orders payOrder =(Orders) rm.get("order");
		// 总金额
		Double amount = payOrder.getTotalAmount();
		
		Double faceAmount = 0d;
		if(coupon!=null){
			faceAmount = coupon.getFaceAmount(); 
			if(coupon.getType().shortValue()==Coupon.TYPE_DISCOUNT){
				BigDecimal discountAmount = new BigDecimal(((100-coupon.getDiscount())/100d)*amount);
				discountAmount = discountAmount.setScale(1,BigDecimal.ROUND_DOWN); 
				if(coupon.getFaceAmount().doubleValue()>discountAmount.doubleValue()){
					faceAmount = discountAmount.doubleValue();
				}
			}
		}  
		// 支付类型1免费2优惠券3账户
		int orderPayType = 3;
		if (amount <= 0) {
			orderPayType = Orders.ORDERS_PAY_FREE;
		} else if ((amount - (null == coupon ? 0.00 : faceAmount)) <= 0) {
			log.info("amount:{},coupon.faceAmount:{}",amount,faceAmount);
			orderPayType = Orders.ORDERS_PAY_COUPON;
		}
		payOrder.setPayType(orderPayType);
		payOrder.setActualAmount(amount - (null == coupon ? 0.00 : faceAmount));
		log.info("payOrder:{}",JsonUtils.toJson(payOrder));
		OrdersDetail od = payOrder.getOrdersDetail();
		if (null != coupon) {
			od.setCouponsId(coupon.getId());
			od.setCouponsMoney(faceAmount);
		} else {
			od.setCouponsId(null);
			od.setCouponsMoney(0.0);
		}
		ordersDetailMapper.updateByPrimaryKey(od);
		// 判断实际支付金额是否为0 停车费-优惠券金额 为0则直接将订单状态改为已支付 做结账处理
		if ((payOrder.getActualAmount() <= 0)) {
			// 修改订单状态为已支付并保存
			payOrder.setActualAmount(0.0d);
			// 结账
			this.checkOutOrder(payOrder, null,null);
			// 删除缓存中的订单
			orderCacheService.deleteCacheOrder(payOrder.getUserId());
			// 返回app信息
			map.put("amount", 0.0d);
			map.put("tradeOrderNO", null);
			map.put("alipay", null);
			map.put("wxPay", null);
			return map;
		}

		Account account = accountMapper.selectByPrimaryKey(u.getId());
		if(account == null){
			account = new Account();
			account.setId(u.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMapper.insert(account);
		}
		
		if(bean.getPayType() == Account.PAY_TYPE_ACCOUNT.intValue() ){ 
			Double usableAmount = account.getAmount();
			if ((payOrder.getActualAmount() <= usableAmount)) {
				// 调起结账接口
				WalletDetail wd = new WalletDetail();
				wd.setAccountAmount(account.getAmount()-payOrder.getActualAmount()); 
				wd.setAmount(payOrder.getActualAmount());
				wd.setAccountId(account.getId()); 
				wd.setType(WalletDetail.TYPE_CONSUME);
				wd.setCreateTime(new Date());
				this.walletDetailWapper.save(wd);
				log.info("order:{}",JsonUtils.toJson(wd));
				this.checkOutOrder(payOrder,null,wd);
				orderCacheService.deleteCacheOrder(payOrder.getUserId());
				map.put("amount", 0.0d);
				map.put("tradeOrderNO", null);
				map.put("alipay", null);
				map.put("wxPay", null);
				return map;
			}else{
				throw new BusinessException(Msg.ORDERS_PAY_ACCOUNT_AMOUNT_LOW);
			}
		} 

		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setAccountId(u.getId());
		rechargeRecord.setStatus((short) 0);
		rechargeRecord.setOrderId(payOrder.getId());
		rechargeRecord.setPayStatus(false);
		rechargeRecord.setCode("CZ" + this.codeService.getRechargeRecordCode());
		double recharge = payOrder.getActualAmount();
		BigDecimal bg = new BigDecimal(recharge);
		recharge = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		rechargeRecord.setPayType((short) bean.getPayType());
		rechargeRecord.setPackageId(null);
		rechargeRecord.setPackageAmount(recharge);
		rechargeRecord.setPaymentAmount(recharge);
		rechargeRecord.setCreateTime(new Date());
		rechargeRecordMapper.insert(rechargeRecord);
		orderCacheService.cacheOrder(payOrder);
		
		
		/**
		 * 1.获取待支付订单 2.生成交易记录 根据订单和交易记录生成移动端请求参数列表
		 */
		try {
			// 支付宝 支付
			if (bean.getPayType() == Account.PAY_TYPE_ALIPAY.intValue()) {
				Map<String, String> m = Alipay.orderPay(rechargeRecord.getCode(), rechargeRecord.getPaymentAmount(),
						PayConstants.BODY_ORDER);
				Map<String, Object> reMap = new HashMap<>();
				reMap.put("amount", rechargeRecord.getPaymentAmount());
				reMap.put("orderInfo", m.get("orderInfo"));
				reMap.put("tradeOrderNO", rechargeRecord.getCode());
				reMap.put("notifyUrl", PayConstants.getAppOrderAlipayUrl());
				map.put("amount", rechargeRecord.getPaymentAmount());
				map.put("tradeOrderNO", rechargeRecord.getCode());
				map.put("alipay", reMap);
				map.put("wxPay", null);
				map.put("applePay", null);
				log.info("alipay:{}",new ObjectMapper().writeValueAsString(map)); 
				return map;
			} else if (bean.getPayType() == Account.PAY_TYPE_WECHAT.intValue() ) {
				Map<String, Object> m = WeixinPay.orderPay(request.getLocalAddr(), rechargeRecord.getCode(),
						PayConstants.BODY_ORDER, rechargeRecord.getPaymentAmount());
				m.put("notify_url", PayConstants.getAppOrderWechatUrl());
				map.put("amount", rechargeRecord.getPaymentAmount());
				map.put("tradeOrderNO", rechargeRecord.getCode());
				map.put("wxPay", m);
				map.put("alipay", null);
				map.put("applePay", null);
				log.info("wxpay:{}",new ObjectMapper().writeValueAsString(map)); 
				return map;
			}else if(bean.getPayType() == Account.PAY_TYPE_UNION_APPLE.intValue() ){
				Map<String, Object> m = new HashMap<String,Object>();
				SDKConfig.init(unionpayConfig);
				log.info(JsonUtils.toJson(unionpayConfig));
				String tn = Unionpay.create(rechargeRecord,unionpayConfig);
				m.put("tn", tn); 
				m.put("verifyUrl", PayConstants.getAppOrderApplePayUrl());
				map.put("amount", rechargeRecord.getPaymentAmount());
				map.put("tradeOrderNO", rechargeRecord.getCode());
				map.put("wxPay", null);
				map.put("alipay", null);
				map.put("applePay", m);
				log.info("wxpay:{}",new ObjectMapper().writeValueAsString(map)); 
				return map;
			}else{
				throw new BusinessException(Msg.ORDERS_UNKNOW_PAY);
			}
		} catch (BusinessException e) { 
			throw e;
		} catch (Exception e) { 
			throw new BusinessException(Msg.ORDERS_PAY_SIGN_ERROR);
		} 
	}

	@Override
	public Boolean verify(Long orderId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
