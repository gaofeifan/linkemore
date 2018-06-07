package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.common.Constants.CouponStatus;
import cn.linkmore.bean.common.Constants.CouponType;
import cn.linkmore.bean.common.Constants.OrderPayType;
import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.bean.common.Constants.TradeType;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.order.dao.cluster.AccountClusterMapper;
import cn.linkmore.order.dao.cluster.AccountHistoryClusterMapper;
import cn.linkmore.order.dao.cluster.CompanyTradeRecordClusterMapper;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.cluster.OrdersDetailClusterMapper;
import cn.linkmore.order.dao.cluster.RechargeRecordClusterMapper;
import cn.linkmore.order.dao.cluster.TradeRecordClusterMapper;
import cn.linkmore.order.dao.cluster.WalletDetailClusterMapper;
import cn.linkmore.order.dao.master.AccountHistoryMasterMapper;
import cn.linkmore.order.dao.master.AccountMasterMapper;
import cn.linkmore.order.dao.master.CompanyTradeRecordMasterMapper;
import cn.linkmore.order.dao.master.OrdersDetailMasterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.dao.master.RechargeRecordMasterMapper;
import cn.linkmore.order.dao.master.TradeRecordMasterMapper;
import cn.linkmore.order.dao.master.WalletDetailMasterMapper;
import cn.linkmore.order.entity.Account;
import cn.linkmore.order.entity.AccountHistory;
import cn.linkmore.order.entity.CompanyTradeRecord;
import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.entity.OrdersDetail;
import cn.linkmore.order.entity.RechargeRecord;
import cn.linkmore.order.entity.TradeRecord;
import cn.linkmore.order.entity.WalletDetail;
import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.order.response.ResOrderWeixin;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.PayService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.AppAlipayClient;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.client.ApplePayClient;
import cn.linkmore.third.client.DockingClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.request.ReqAppAlipay;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.request.ReqApplePay;
import cn.linkmore.third.request.ReqOrder;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.util.JsonUtil;
/**
 * Service实现 - 支付
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class PayServiceImpl implements PayService {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StallClient stallClient; 
	@Autowired
	private OrdersClusterMapper ordersClusterMapper;
	@Autowired
	private OrdersDetailMasterMapper ordersDetailMasterMapper;
	
	@Autowired
	private OrdersDetailClusterMapper ordersDetailClusterMapper;
	@Autowired
	private OrdersMasterMapper orderMasterMapper; 
	
	@Autowired
	private DockingClient dockingClient;
	
	@Autowired
	private TradeRecordMasterMapper tradeRecordMasterMapper; 
	
	@Autowired
	private TradeRecordClusterMapper tradeRecordClusterMapper; 
	
	@Autowired
	private RechargeRecordMasterMapper rechargeRecordMasterMapper; 
	
	@Autowired
	private RechargeRecordClusterMapper rechargeRecordClusterMapper; 
	
	@Autowired
	private AccountMasterMapper accountMasterMapper; 
	
	@Autowired
	private AccountClusterMapper accountClusterMapper; 
	
	
	@Autowired
	private WalletDetailMasterMapper walletDetailMasterMapper; 
	
	@Autowired
	private WalletDetailClusterMapper walletDetailClusterMapper;
	
	@Autowired
	private AccountHistoryMasterMapper accountHistoryMasterMapper;
	@Autowired
	private AccountHistoryClusterMapper accountHistoryClusterMapper;
	
	@Autowired
	private CompanyTradeRecordMasterMapper companyTradeRecordMasterMapper;
	@Autowired
	private CompanyTradeRecordClusterMapper companyTradeRecordClusterMapper;
	
	
	@Autowired
	private StrategyBaseClient strategyBaseClient;
	
	@Autowired
	private PrefectureClient prefectureClient;
	 
	
	@Autowired
	private CouponClient couponClient;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private AppAlipayClient appAlipayClient;
	@Autowired
	private AppWechatClient appWechatClient;
	
	@Autowired
	private ApplePayClient applePayClient;
	
	@Autowired
	private PushClient pushClient;
	 

	@Override
	public ResOrderCheckout checkout(Long orderId, Long userId) {
		ResUserOrder order = this.ordersClusterMapper.findUserLatest(userId);
		log.info("order:{}",JsonUtil.toJson(order));
		log.info("orderId:{},userId:{}",orderId,userId);
		log.info("order==null:{},order.getUserId()!=userId:{}",order==null,order.getUserId()!=userId);
		if(order==null||order.getUserId().longValue()!=userId.longValue()) {
			return null;
		}
		Account account = this.accountClusterMapper.findById(order.getUserId());
		ResOrderCheckout roc = new ResOrderCheckout();
		roc.setAccountAmount(account.getUsableAmount());
		List<ResCoupon> rcs = this.couponClient.order(userId, orderId);
		roc.setStartTime(order.getCreateTime());
		roc.setEndTime(new Date());
		if(order.getStatus()==OrderStatus.SUSPENDED.value) {
			roc.setEndTime(order.getStatusTime());
		}
		roc.setCouponCount(rcs!=null?rcs.size():0);
		roc.setParkingTime(new Long((roc.getEndTime().getTime()-roc.getStartTime().getTime())/(1000L*60)).intValue());
		roc.setOrderId(orderId);
		roc.setPayType((short)TradePayType.WECHAT.type);
		roc.setPlateNumber(order.getPlateNo());
		ResPrefectureDetail pre = this.prefectureClient.findById(order.getPreId());
		if(pre!=null) {
			roc.setPrefectureName(pre.getName());
		}
		ResStallEntity stall = this.stallClient.findById(order.getStallId());
		if(stall!=null) {
			roc.setStallName(stall.getStallName());
		}
		ReqStrategy strategy = new ReqStrategy();
		strategy.setBeginTime(roc.getStartTime().getTime());
		strategy.setEndTime(roc.getEndTime().getTime());
		strategy.setStrategyId(pre.getStrategyId());
		Map<String,Object> map = this.strategyBaseClient.fee(strategy); 
		String totalStr = map.get("totalAmount").toString();
		String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr)); 
		roc.setTotalAmount(new BigDecimal(Double.valueOf(totalAmountStr))); 
		return roc;
	}
	private Account initAccount(Long userId) {
		Account account = new Account();
		account.setId(userId);
		account.setAmount(new BigDecimal(0.00d));
		account.setUsableAmount(new BigDecimal(0.00d));
		account.setFrozenAmount(new BigDecimal(0.00d));
		account.setRechagePaymentAmount(new BigDecimal(0.00d));
		account.setRechargeAmount(new BigDecimal(0.00d));
		account.setAccType(1);
		account.setStatus((short) 1);
		account.setOrderAmount(new BigDecimal(0.00d));
		account.setOrderPaymentAmount(new BigDecimal(0.00d));
		account.setCreateTime(new Date());
		accountMasterMapper.save(account);
		return account;
	}
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public ResOrderConfirm confirm(ReqOrderConfirm roc) {
		ResOrderConfirm confirm = null;
		ResCoupon coupon = null; 
		if(roc.getCouponId()!=null) {
			coupon = this.couponClient.get(roc.getCouponId());
			if(coupon!=null&&coupon.getStatus()!=CouponStatus.FREE.status) {
				coupon = null;
			}
		} 
		Orders order = this.ordersClusterMapper.findById(roc.getOrderId());
		// 调用计费策略
		ReqStrategy reqStrategy = new ReqStrategy();
		reqStrategy.setBeginTime(order.getCreateTime().getTime());
		reqStrategy.setStrategyId(order.getStrategyId());
		reqStrategy.setEndTime(new Date().getTime());
		if(order.getStatus()==OrderStatus.SUSPENDED.value) {
			reqStrategy.setEndTime(order.getStatusTime().getTime());
		}
		Map<String,Object> rm = strategyBaseClient.fee(reqStrategy);
		String totalStr = rm.get("totalAmount").toString();
		String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr)); 
		order.setTotalAmount(new BigDecimal(Double.valueOf(totalAmountStr)));
		// 总金额
		Double amount =order.getTotalAmount().doubleValue(); 
		Double faceAmount = 0d;
		if(coupon!=null){
			faceAmount = coupon.getFaceAmount().doubleValue(); 
			if(coupon.getType().shortValue()==CouponType.DISCOUNT.type){
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
			orderPayType = OrderPayType.FREE.type;
		} else if ((amount - (null == coupon ? 0.00 : faceAmount)) <= 0) { 
			orderPayType = OrderPayType.COUPON.type;
		}
		order.setPayType(orderPayType);
		order.setActualAmount(new BigDecimal(amount - (null == coupon ? 0.00 : faceAmount))); 
		OrdersDetail od = this.ordersDetailClusterMapper.findByOrderId(order.getId());
		if (null != coupon) {
			od.setCouponsId(coupon.getId());
			od.setCouponsMoney(new BigDecimal(faceAmount));
		} else {
			od.setCouponsId(null);
			od.setCouponsMoney(new BigDecimal(0.0));
		}
		ordersDetailMasterMapper.update(od);
		// 判断实际支付金额是否为0 停车费-优惠券金额 为0则直接将订单状态改为已支付 做结账处理
		if ((order.getActualAmount().doubleValue() <= 0)) {
			// 修改订单状态为已支付并保存
			order.setActualAmount(new BigDecimal(0.0d));
			// 结账
			this.checkOutOrder(order, null,null); 
			// 返回app信息
			confirm = new ResOrderConfirm();
			confirm.setAmount(new BigDecimal(0.0D)); 
			confirm.setNumber(null); 
			confirm.setPayType((short)orderPayType);
			return confirm;
		}

		Account account = accountClusterMapper.findById(order.getUserId());
		if(account == null){
			account = initAccount(order.getUserId());
		} 
		if(roc.getPayType()== OrderPayType.ACCOUNT.type ){ 
			Double usableAmount = account.getAmount().doubleValue();
			if ((order.getActualAmount().doubleValue() <= usableAmount)) {
				// 调起结账接口
				WalletDetail wd = new WalletDetail();
				wd.setAccountAmount(new BigDecimal(account.getAmount().doubleValue()-order.getActualAmount().doubleValue())); 
				wd.setAmount(order.getActualAmount());
				wd.setAccountId(account.getId()); 
				wd.setType(WalletDetail.TYPE_CONSUME);
				wd.setCreateTime(new Date());
				this.walletDetailMasterMapper.save(wd);
				log.info("order:{}",JsonUtil.toJson(wd));
				this.checkOutOrder(order,null,wd);
				confirm = new ResOrderConfirm();
				confirm.setAmount(new BigDecimal(0.0D)); 
				confirm.setNumber(null); 
				confirm.setPayType((short)orderPayType);
				return confirm;
			}else{
				throw new BusinessException(StatusEnum.ORDER_PAY_ACCOUNT_AMOUNT_LOW);
			}
		} 

		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setAccountId(order.getUserId());
		rechargeRecord.setStatus((short) 0);
		rechargeRecord.setOrderId(order.getId());
		rechargeRecord.setPayStatus(false);
		rechargeRecord.setCode(this.getRechargeNumer());
		double recharge = order.getActualAmount().doubleValue();
		BigDecimal bg = new BigDecimal(recharge);
		recharge = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		rechargeRecord.setPayType((short) roc.getPayType());
		rechargeRecord.setPackageId(null);
		rechargeRecord.setPackageAmount(new BigDecimal(recharge));
		rechargeRecord.setPaymentAmount(new BigDecimal(recharge));
		rechargeRecord.setCreateTime(new Date());
		rechargeRecordMasterMapper.save(rechargeRecord);   
		/**
		 * 1.获取待支付订单 2.生成交易记录 根据订单和交易记录生成移动端请求参数列表
		 */
		try {
			// 支付宝 支付
			if (roc.getPayType() ==TradePayType.ALIPAY.type) {
				ReqAppAlipay alipay = new ReqAppAlipay();
				alipay.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				alipay.setNumber(rechargeRecord.getCode());
				String info = appAlipayClient.order(alipay);  
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount()); 
				confirm.setNumber(rechargeRecord.getCode()); 
				confirm.setPayType((short)TradePayType.ALIPAY.type); 
				confirm.setAlipay(info);
				return confirm; 
			} else if (roc.getPayType() == TradePayType.WECHAT.type) {
				ReqAppWechatOrder reqawo = new ReqAppWechatOrder(); 
				reqawo.setAddress(roc.getAddress());
				reqawo.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				reqawo.setNumber(rechargeRecord.getCode());
				ResAppWechatOrder rawo = this.appWechatClient.order(reqawo);
				log.info("get wechat order:{}",JsonUtil.toJson(rawo));
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount()); 
				confirm.setNumber(rechargeRecord.getCode()); 
				confirm.setPayType((short)TradePayType.WECHAT.type); 
				ResOrderWeixin row = new ResOrderWeixin();
				row.setAppid(rawo.getAppid());
				row.setPartnerid(rawo.getPartnerid()); 
				row.setPrepayid(rawo.getPrepayid());  
				row.setTimestamp(rawo.getTimestamp());  
				row.setNoncestr(rawo.getNoncestr());
				row.setSign(rawo.getSign());
				confirm.setWeixin(row); 
				return confirm;
			}else if(roc.getPayType() == TradePayType.APPLE.type){
				ReqApplePay rap = new ReqApplePay();
				rap.setTimestramp(new Date().getTime());
				rap.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				rap.setNumber(rechargeRecord.getCode());
				String tn = this.applePayClient.order(rap);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount()); 
				confirm.setNumber(rechargeRecord.getCode()); 
				confirm.setPayType((short)TradePayType.APPLE.type); 
				confirm.setApple(tn);
			}else{
				throw new BusinessException(StatusEnum.ORDER_UNKNOW_PAY);
			}
		} catch (BusinessException e) { 
			throw e;
		} catch (Exception e) { 
			e.printStackTrace();
			throw new BusinessException(StatusEnum.ORDER_PAY_SIGN_ERROR);
		}
		return confirm; 
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	private void checkOutOrder(Orders order, RechargeRecord rechargeRecord,WalletDetail wd) {
		TradeRecord tradeRecord = null;
		if (rechargeRecord != null) {
			// 充值
			tradeRecord = this.recharge(rechargeRecord);
		}
		// 支付
		this.payment(order, tradeRecord,wd); 
	}

	/**
	 * 充值
	 * 
	 * @param rechargeRecord
	 * @return
	 */
	private TradeRecord recharge(RechargeRecord rechargeRecord) {
		rechargeRecord.setPayStatus(true);
		rechargeRecord.setStatus((short) 1);
		rechargeRecord.setPayTime(new Date());
		rechargeRecordMasterMapper.update(rechargeRecord);

		TradeRecord trade = new TradeRecord();
		trade.setAccountId(rechargeRecord.getAccountId());
		trade.setBizId(rechargeRecord.getId().longValue());
		trade.setCreateTime(new Date());
		trade.setPaymentAmount(rechargeRecord.getPaymentAmount());
		trade.setTradeAmount(rechargeRecord.getPackageAmount());
		trade.setType((short)TradeType.ORDER_RECHARGE.type);
		trade.setUpdateTime(new Date());
		trade.setCode(this.getTradeNumber());
		tradeRecordMasterMapper.save(trade);

		Account account = accountClusterMapper.findById(rechargeRecord.getAccountId());
		AccountHistory history = new AccountHistory();
		history.copy(account);
		history.setCreateTime(new Date());
		history.setUpdateTime(new Date());
		history.setTradeId(trade.getId());
		accountHistoryMasterMapper.save(history);

		account.setAmount(new BigDecimal(account.getAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		account.setUsableAmount(new BigDecimal(account.getUsableAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		account.setRechargeAmount(new BigDecimal(account.getRechagePaymentAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		account.setRechagePaymentAmount(new BigDecimal(account.getRechagePaymentAmount().doubleValue() + trade.getPaymentAmount().doubleValue()));
		account.setUpdateTime(new Date());
		accountMasterMapper.update(account);

		CompanyTradeRecord ctr = companyTradeRecordClusterMapper.findLast();
		CompanyTradeRecord newCtr = new CompanyTradeRecord();
		newCtr.copy(ctr);
		newCtr.setCreateTime(new Date());
		newCtr.setUpdateTime(new Date());
		newCtr.setTradeId(trade.getId());
		newCtr.setTotalAmount(new BigDecimal(newCtr.getTotalAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		newCtr.setRechargeAmount(new BigDecimal(newCtr.getRechargeAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		newCtr.setRechargePaymentAmount( trade.getPaymentAmount().doubleValue() + newCtr.getRechargePaymentAmount().doubleValue());
		companyTradeRecordMasterMapper.save(newCtr);
		return trade;
	}
	
	private String getRechargeNumer() {
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
		Long increment = this.redisService.increment(RedisKey.ORDER_RECHARGE_SERIAL_NUMBER.key+sdf.format(day), 1);
		Double t = Math.pow(10,5);
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.intValue()+increment);
		return number.toString();
	}

	private String getTradeNumber() {
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
		Long increment = this.redisService.increment(RedisKey.ORDER_TRADE_SERIAL_NUMBER.key+sdf.format(day), 1);
		Double t = Math.pow(10,5);
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.intValue()+increment);
		return number.toString();
	}
	
	/**
	 * 结账扣款
	 * 
	 * @param order
	 * @param tradeRecord
	 */
	private void payment(Orders order, TradeRecord tradeRecord,WalletDetail wd) {
		Date current = new Date();
		Long rechargeTradeId = null; 
		short tradeType = (short)TradeType.ORDER_NONE_PAY.type;
		if (null != tradeRecord) {
			rechargeTradeId = tradeRecord.getId();
			tradeType = (short)TradeType.ORDER_RECHARGE_PAY.type;
		}else if(null!=wd){
			tradeType = (short)TradeType.ORDER_ACCOUNT_PAY.type;
			rechargeTradeId = wd.getId();
		}
		
		// TradeRecord 交易记录
		TradeRecord payTradeRecord = new TradeRecord();
		payTradeRecord.setAccountId(order.getUserId()); 
		payTradeRecord.setType(tradeType);
		payTradeRecord.setTradeAmount(order.getTotalAmount());
		payTradeRecord.setPaymentAmount(order.getActualAmount());
		payTradeRecord.setBizId(order.getId());
		payTradeRecord.setCode(getTradeNumber());
		payTradeRecord.setCreateTime(current);
		payTradeRecord.setTradeId(rechargeTradeId);
		tradeRecordMasterMapper.save(payTradeRecord);

		// Account 账户
		Account account = accountClusterMapper.findById(order.getUserId());
		// AccountHistory 个人账户历史
		AccountHistory accountHistory = new AccountHistory();
		accountHistory.copy(account);
		// 交易记录保存后返回的id
		accountHistory.setCreateTime(current);
		accountHistory.setUpdateTime(current);
		accountHistory.setTradeId(payTradeRecord.getId());
		accountHistoryMasterMapper.save(accountHistory); 
		if(null!=wd){ 
			account.setAmount(new BigDecimal(account.getAmount().doubleValue() - order.getActualAmount().doubleValue()));
			if(account.getUsableAmount().doubleValue()>=order.getActualAmount().doubleValue()){
				account.setUsableAmount(new BigDecimal(account.getUsableAmount().doubleValue() - order.getActualAmount().doubleValue()));
			}else{
				account.setGiftAmount(new BigDecimal(account.getGiftAmount().doubleValue()-(order.getActualAmount().doubleValue()-account.getUsableAmount().doubleValue())));
				account.setUsableAmount(new BigDecimal(0D));
			}  
		}else{
			account.setUsableAmount(new BigDecimal(account.getUsableAmount().doubleValue() - order.getActualAmount().doubleValue()));
			account.setAmount(new BigDecimal(account.getAmount().doubleValue() - order.getActualAmount().doubleValue()));
		} 
		
		account.setOrderAmount(new BigDecimal(account.getOrderAmount().doubleValue() + order.getTotalAmount().doubleValue()));
		account.setOrderPaymentAmount(new BigDecimal(account.getOrderPaymentAmount().doubleValue() + order.getActualAmount().doubleValue()));
		account.setUpdateTime(current);
		accountMasterMapper.update(account);

		CompanyTradeRecord ctr = companyTradeRecordClusterMapper.findLast();
		CompanyTradeRecord newCtr = new CompanyTradeRecord();
		newCtr.copy(ctr);
		newCtr.setCreateTime(new Date());
		newCtr.setUpdateTime(new Date());
		newCtr.setTradeId(payTradeRecord.getId());
		newCtr.setTotalAmount(new BigDecimal(newCtr.getTotalAmount().doubleValue() + payTradeRecord.getTradeAmount().doubleValue()));
		newCtr.setRechargeAmount(new BigDecimal(newCtr.getRechargeAmount().doubleValue() + payTradeRecord.getTradeAmount().doubleValue()));
		newCtr.setRechargePaymentAmount(payTradeRecord.getPaymentAmount().doubleValue() + newCtr.getRechargePaymentAmount().doubleValue());
		companyTradeRecordMasterMapper.save(newCtr);

		// 4.修改车位信息
		/*
		 * 检查车位状态是否为预下线，-->下线 否则 --> 可租用 如果订单为已挂起状态，不修改车位状态
		 */

		OrdersDetail od = ordersDetailClusterMapper.findByOrderId(order.getId());
		if (order.getStatus() == OrderStatus.UNPAID.value) { 
			this.stallClient.checkout(order.getStallId());
			od.setEndTime(current);
			od.setUpdateTime(current);  
			try { 
				new Thread(new LockUpThread( order.getStallId())).start();
			} catch (Exception e) {
				log.info("up lock throw exception");
			}
		} 
		// 更新订单
		order.setStatus(OrderStatus.COMPLETED.value);
		order.setUpdateTime(current);
		this.orderMasterMapper.update(order);
		ordersDetailMasterMapper.update(od); 
		// 3.更新优惠券信息
		if (null != od.getCouponsId()) { 
			this.couponClient.pay(new ReqCouponPay(od.getCouponsId(),order.getTotalAmount(),od.getCouponsMoney()));
		}  
		this.userClient.checkout(order.getUserId());
		//结账调用新版推送消息 
		Thread thread = new ProduceCheckBookingThread(order);
		thread.start();
		push(order.getUserId().toString(),"第三方支付通知","支付成功",PushType.ORDER_COMPLETE_NOTICE,true); 
	}
	
	@Async
	private void push(String uid,String title,String content,PushType type,Boolean status) {
		Token token = (Token)this.redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key+uid.toString());
		ReqPush rp = new ReqPush();
		rp.setAlias(uid);
		rp.setTitle(title); 
		rp.setContent(content);
		rp.setClient(token.getClient());
		rp.setType(type);
		rp.setData(status.toString()); 
		this.pushClient.push(rp);
	}
	class ProduceCheckBookingThread extends Thread{
		private Orders order;
		public ProduceCheckBookingThread(Orders order){
			this.order = order;
		}
		public void run(){ 
			try {
				if(StringUtils.isNotBlank(order.getDockId())){ 
					ReqOrder ro = new ReqOrder();
					ro.setActualAmount(order.getActualAmount());
					ro.setTotalAmount(order.getTotalAmount());
					ro.setDockId(order.getDockId());
					ro.setBeginTime(order.getCreateTime());
					ro.setEndTime(order.getEndTime());
					ro.setPlateNo(order.getPlateNo());
					ro.setPreId(order.getPreId());
					ro.setStatus(order.getStatus());
					ro.setOrderNo(order.getOrderNo());
					dockingClient.order(ro); 
				}
			} catch (Exception e) {
				log.info("call park producer error with check booking msg");
			}
		}
	}
	class LockUpThread extends Thread {
		private Long stallId; 
		public LockUpThread(Long stallId) {
			this.stallId = stallId;
		}  
		@Override
		public void run() { 
			stallClient.uplock(stallId);
		}  
	}

	@Override
	public Boolean verify(Long orderId, Long userId) {
		ResUserOrder order = this.ordersClusterMapper.findUserLatest(userId);
		Boolean flag = false;
		if(order==null||order.getUserId()!=userId) {
			flag = false;
		}else if(order.getStatus().intValue()==OrderStatus.COMPLETED.value) {
			flag = true;
		}
		return flag;
	}
	
	private Boolean alipay(String json) {
		Boolean flag = false; 
		Map<String,String> map = JsonUtil.toObject(json, HashMap.class);
		String number = map.get("out_trade_no");
		RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
		flag = this.appAlipayClient.verify(json, number, rr.getPaymentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		log.info("alipay verify :{},result:{}",json,flag);
		if(flag) {
			flag = false;
			Orders orders = ordersClusterMapper.findById(rr.getOrderId());
			this.checkOutOrder(orders, rr,null);
			flag = true;
		} 
		return flag;
	}
	
	private Boolean apple(String json) {
		Boolean flag = false;
		flag = this.applePayClient.verify(json);
		log.info("apple verify :{},result:{}",json,flag);
		if(flag) {
			flag = false; 
			Map<String,String> param = JsonUtil.toObject(json, HashMap.class);
			String number = param.get("orderId");
			RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
			Orders orders = ordersClusterMapper.findById(rr.getOrderId());
			this.checkOutOrder(orders, rr,null);
			flag = true;
		}
		
		return flag;
	}
	
	private Boolean wechat(String json) {
		Boolean flag = false;
		flag = this.appWechatClient.verify(json);
		log.info("alipay verify :{},result:{}",json,flag);
		if(flag) {
			flag = false;
			Map<String,String> param = JsonUtil.toObject(json, HashMap.class);
			if ("SUCCESS".equals(param.get("return_code")) && "SUCCESS".equals(param.get("result_code"))) { 
				String number = param.get("out_trade_no");
				RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
				Orders orders = ordersClusterMapper.findById(rr.getOrderId());
				this.checkOutOrder(orders, rr,null);
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public Boolean callback(String json, Integer source) { 
		log.info("json:{},source:{}",json,source);
		if(source == TradePayType.ALIPAY.type) {
			return this.alipay(json);
		}else if(source == TradePayType.APPLE.type) {
			return this.apple(json);
		}else if(source == TradePayType.WECHAT.type) {
			return this.wechat(json);
		}
		return false;
	}
}
