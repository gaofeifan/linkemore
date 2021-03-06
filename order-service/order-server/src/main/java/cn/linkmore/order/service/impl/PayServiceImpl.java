package cn.linkmore.order.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.CouponStatus;
import cn.linkmore.bean.common.Constants.CouponType;
import cn.linkmore.bean.common.Constants.OrderPayType;
import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.bean.common.Constants.TradeType;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.notice.client.UserSocketClient;
import cn.linkmore.order.config.BaseConfig;
import cn.linkmore.order.controller.app.request.ReqPayConfirm;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.controller.app.response.ResPayCheckout;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.order.controller.app.response.ResPayWeixinMini;
import cn.linkmore.order.dao.cluster.AccountClusterMapper;
import cn.linkmore.order.dao.cluster.CompanyTradeRecordClusterMapper;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.cluster.RechargeRecordClusterMapper;
import cn.linkmore.order.dao.master.AccountHistoryMasterMapper;
import cn.linkmore.order.dao.master.AccountMasterMapper;
import cn.linkmore.order.dao.master.CompanyTradeRecordMasterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.dao.master.RechargeRecordMasterMapper;
import cn.linkmore.order.dao.master.TradeRecordMasterMapper;
import cn.linkmore.order.dao.master.WalletDetailMasterMapper;
import cn.linkmore.order.entity.Account;
import cn.linkmore.order.entity.AccountHistory;
import cn.linkmore.order.entity.CompanyTradeRecord;
import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.entity.RechargeRecord;
import cn.linkmore.order.entity.TradeRecord;
import cn.linkmore.order.entity.WalletDetail;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.order.response.ResOrderWeixin;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.PayService;
import cn.linkmore.prefecture.client.OpsEntUserPlateClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StrategyFeeClient;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.AppAlipayClient;
import cn.linkmore.third.client.AppLoongPayClient;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.client.ApplePayClient;
import cn.linkmore.third.client.DockingClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.WechatMiniClient;
import cn.linkmore.third.request.ReqAppAlipay;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.request.ReqApplePay;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.request.ReqLoongPayVerifySign;
import cn.linkmore.third.request.ReqOrder;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.third.response.ResLoongPay;
import cn.linkmore.third.response.ResWechatMiniOrder;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;
import cn.linkmore.util.XMLUtil;

/**
 * Service实现 - 支付
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class PayServiceImpl implements PayService {
	
	private UserFactory appUserFactory = AppUserFactory.getInstance();

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static ConcurrentHashMap<Long, String> headerOs = new ConcurrentHashMap<>();

	@Autowired
	private BaseConfig baseConfig;

	@Autowired
	private AppLoongPayClient appLoongPayClient;
	
	@Autowired
	private OrdersClusterMapper ordersClusterMapper;
	
	@Autowired
	private OrdersMasterMapper orderMasterMapper;

	@Autowired
	private TradeRecordMasterMapper tradeRecordMasterMapper;

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
	private AccountHistoryMasterMapper accountHistoryMasterMapper;

	@Autowired
	private CompanyTradeRecordMasterMapper companyTradeRecordMasterMapper;
	
	@Autowired
	private CompanyTradeRecordClusterMapper companyTradeRecordClusterMapper;
	
	@Autowired
	private StrategyFeeClient strategyFeeClient;

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
	private WechatMiniClient wechatMiniClient;

	@Autowired
	private PushClient pushClient;

	@Autowired
	private UserSocketClient userSocketClient;
	
	@Autowired
	private StallClient stallClient;
	
	@Autowired
	private DockingClient dockingClient;
	
	@Resource
	private OpsEntUserPlateClient userPlateClient; 

	private static final List<String> loongpay = Arrays.asList("USRMSG","ACCDATE","INSTALLNUM","ERRMSG","USRINFO");
	
	@Override
	public ResPayCheckout checkout(Long orderId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResUserOrder order = this.ordersClusterMapper.findUserLatest(cu.getId());
		if (!(order.getStatus().intValue() == OrderStatus.UNPAID.value
				|| order.getStatus().intValue() == OrderStatus.SUSPENDED.value)) {
			log.error("confirm order.status {}" + order.getStatus());
			throw new BusinessException(StatusEnum.ORDER_CHECK_EXPIRE);
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout orderId:{},userId:{}", orderId, cu.getId());
		if (order == null || order.getUserId().longValue() != cu.getId().longValue()) {
			return null;
		}
		Account account = this.accountClusterMapper.findById(order.getUserId());
		if (account == null) {
			account = this.initAccount(order.getUserId());
		}
		ResOrderCheckout roc = new ResOrderCheckout();
		roc.setAccountAmount(account.getUsableAmount());
		roc.setStartTime(order.getCreateTime());
		roc.setEndTime(new Date());
		if (order.getStatus() == OrderStatus.SUSPENDED.value) {
			roc.setEndTime(order.getStatusTime());
		}
		roc.setCouponCount(0);
		roc.setParkingTime(
				new Long((roc.getEndTime().getTime() - roc.getStartTime().getTime()) / (1000L * 60)).intValue());
		roc.setOrderId(orderId);
		if (cu.getClient().shortValue() == ClientSource.ANDROID.source) {
			roc.setPayType((short) TradePayType.WECHAT.type);
		} else if (cu.getClient().shortValue() == ClientSource.IOS.source) {
			roc.setPayType((short) TradePayType.APPLE.type);
		} else {
			roc.setPayType((short) TradePayType.WECHAT.type);
		}
		roc.setPlateNumber(order.getPlateNo());
		roc.setPrefectureName(order.getPreName());
		ResPrefectureDetail pre = this.prefectureClient.findById(order.getPreId());
		if (pre != null && order.getPreName() == null) {
			roc.setPrefectureName(pre.getName());
		}
		ResStallEntity stall = this.stallClient.findById(order.getStallId());
		if (stall != null) {
			roc.setStallName(stall.getStallName());
		}
		
		/*ReqStrategy strategy = new ReqStrategy();
		strategy.setBeginTime(roc.getStartTime().getTime());
		strategy.setEndTime(roc.getEndTime().getTime());
		strategy.setStrategyId(order.getStrategyId());
		Map<String, Object> map = this.strategyBaseClient.fee(strategy);
		String totalStr = map.get("totalAmount").toString();
		String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr));
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout totalAmount:{}", totalAmountStr);*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("stallId", stall.getId());
		param.put("plateNo", order.getPlateNo());
		param.put("startTime", sdf.format(roc.getStartTime()));
		param.put("endTime", sdf.format(roc.getEndTime()));
		Map<String, Object> map = this.strategyFeeClient.amount(param);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout param:{}  map:{}", JSON.toJSON(param), JSON.toJSON(map));
		if (map != null) {
			Object object = map.get("chargePrice");
			if (object != null) {
				String totalStr = object.toString();
				String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr));
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout totalAmount:{}", totalAmountStr);
				roc.setTotalAmount(new BigDecimal(Double.valueOf(totalAmountStr)));
				Map<String,Object> checkParam = new HashMap<String,Object>();
				checkParam.put("plateNo", order.getPlateNo());
				checkParam.put("preId", order.getPreId());
				boolean flag = userPlateClient.exists(checkParam);
				if(flag) {
					roc.setTotalAmount(new BigDecimal(0.00));
				}
			}
		}
		ResPayCheckout result = null;
		if (roc != null) {
			result = new ResPayCheckout();
			result.setAccountAmount(roc.getAccountAmount());
			result.setTotalAmount(roc.getTotalAmount());
			result.setPlateNumber(roc.getPlateNumber());
			result.setParkingTime(roc.getParkingTime());
			result.setPayType(roc.getPayType());
			result.setPrefectureName(roc.getPrefectureName());
			result.setStallName(roc.getStallName());
			result.setCouponCount(roc.getCouponCount());
			result.setEndTime(roc.getEndTime());
			result.setStartTime(roc.getStartTime());
			result.setOrderId(roc.getOrderId());
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout result:{}",JSON.toJSON(result));
		return result;
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

	private ResPayConfirm getConfirmResult(ResOrderConfirm confirm) {
		ResPayConfirm res = null;
		if (confirm != null) {
			res = new ResPayConfirm();
			res.setAmount(confirm.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			res.setPayType(confirm.getPayType());

			if (confirm.getPayType().shortValue() == TradePayType.ALIPAY.type) {
				res.setAlipay(confirm.getAlipay());
				res.setNumber(confirm.getNumber());
			} else if (confirm.getPayType().shortValue() == TradePayType.WECHAT.type) {
				res.setNumber(confirm.getNumber());
				ResOrderWeixin row = confirm.getWeixin();
				cn.linkmore.order.controller.app.response.ResPayWeixin weixin = new cn.linkmore.order.controller.app.response.ResPayWeixin();
				weixin.setAppid(row.getAppid());
				weixin.setNoncestr(row.getNoncestr());
				weixin.setPartnerid(row.getPartnerid());
				weixin.setPrepayid(row.getPrepayid());
				weixin.setSign(row.getSign());
				weixin.setTimestamp(row.getTimestamp());
				res.setWeixin(weixin);
			} else if (confirm.getPayType().shortValue() == TradePayType.APPLE.type) {
				res.setApple(confirm.getApple());
				res.setNumber(confirm.getNumber());
			} else if (confirm.getPayType().shortValue() == TradePayType.UNION.type
					|| confirm.getPayType().shortValue() == TradePayType.HUAWEI.type
					|| confirm.getPayType().shortValue() == TradePayType.XIAOMI.type) {
				res.setUnion(confirm.getUnion());
				res.setNumber(confirm.getNumber());
			}
		}
		return res;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public ResPayConfirm confirm(ReqPayConfirm roc, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResOrderConfirm confirm = null;
		ResCoupon coupon = null;
		if (roc.getCouponId() != null) {
			coupon = this.couponClient.get(roc.getCouponId());
			log.info("..........confirm coupon:{}", JsonUtil.toJson(coupon));
			if (coupon != null && coupon.getStatus() != CouponStatus.FREE.status) {
				coupon = null;
			} else if (coupon != null && coupon.getUserId().longValue() != cu.getId().longValue()) {
				coupon = null;
			}
		}
		Orders order = this.ordersClusterMapper.findById(roc.getOrderId());
		if(order.getStatus() == OrderStatus.COMPLETED.value) {
			throw new BusinessException(StatusEnum.ORDER_COMPLETED_PAY);
		}
		// 调用计费策略
		/*
		ReqStrategy reqStrategy = new ReqStrategy();
		reqStrategy.setBeginTime(order.getCreateTime().getTime());
		reqStrategy.setStrategyId(order.getStrategyId());
		reqStrategy.setEndTime(new Date().getTime());
		if (order.getStatus() == OrderStatus.SUSPENDED.value) {
			reqStrategy.setEndTime(order.getStatusTime().getTime());
		}
		Map<String, Object> rm = strategyBaseClient.fee(reqStrategy);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>confirm order:{} fee:{}",JSON.toJSON(order), JSON.toJSON(rm));
		String totalStr = rm.get("totalAmount").toString();
		*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String,Object>(); 
		param.put("stallId", order.getStallId());
		param.put("plateNo", order.getPlateNo());
		param.put("startTime", sdf.format(order.getCreateTime()));
		if (order.getStatus().intValue() == OrderStatus.SUSPENDED.value) {
			param.put("endTime", sdf.format(order.getStatusTime()));
		} else {
			param.put("endTime", sdf.format(new Date()));
		}
		Map<String, Object> map = this.strategyFeeClient.amount(param);
		log.info("..........checkout param:{}  map:{}", JSON.toJSON(param), JSON.toJSON(map));
		if (map != null) {
			Object object = map.get("chargePrice");
			if (object != null) {
				String totalStr = object.toString();
				String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr));
				log.info("..........checkout totalAmount:{}", totalAmountStr);
				order.setTotalAmount(new BigDecimal(Double.valueOf(totalAmountStr)));
			}
		}
		// 总金额
		Double amount = order.getTotalAmount().doubleValue();
		Double faceAmount = 0d;
		if (coupon != null) {
			faceAmount = coupon.getFaceAmount().doubleValue();
			if (coupon.getType().shortValue() == CouponType.DISCOUNT.type) {
				BigDecimal discountAmount = new BigDecimal(((100 - coupon.getDiscount()) / 100d) * amount);
				discountAmount = discountAmount.setScale(1, BigDecimal.ROUND_DOWN);
				if (coupon.getFaceAmount().doubleValue() > discountAmount.doubleValue()) {
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
		} else {
			orderPayType = OrderPayType.ACCOUNT.type;
		}
		log.info(".......... order pay type:{}", orderPayType);
		order.setActualAmount(new BigDecimal(amount - (null == coupon ? 0.00 : faceAmount)));
		if (null != coupon) {
			order.setCouponId(coupon.getId());
			order.setCouponAmount(new BigDecimal(faceAmount));
		} else {
			order.setCouponId(null);
			order.setCouponAmount(new BigDecimal(0.0));
		}

		Date endTime = new Date();
		if (order.getStatusHistory() != null) {
			endTime = order.getStatusTime();
		}
		
		Map<String,Object> checkParam = new HashMap<String,Object>();
		checkParam.put("plateNo", order.getPlateNo());
		checkParam.put("preId", order.getPreId());
		boolean flag = userPlateClient.exists(checkParam);
		if(flag) {
			order.setActualAmount(new BigDecimal(0.00));
		}

		// 判断实际支付金额是否为0 停车费-优惠券金额 为0则直接将订单状态改为已支付 做结账处理
		if ((order.getActualAmount().doubleValue() <= 0)) {
			// 修改订单状态为已支付并保存
			order.setActualAmount(new BigDecimal(0.0d));
			order.setPayType(orderPayType);
			order.setEndTime(endTime);
			this.updateConfirm(order);
			// 结账
			this.checkOutOrder(order, null, null);
			// 返回app信息
			confirm = new ResOrderConfirm();
			confirm.setAmount(new BigDecimal(0.0D));
			confirm.setNumber(null);
			confirm.setPayType((short) (orderPayType - OrderPayType.ACCOUNT.type));
			log.info("..........confirm order actualAmount <= 0 confirm:{}",JSON.toJSON(confirm));
			return getConfirmResult(confirm);
		}

		Account account = accountClusterMapper.findById(order.getUserId());
		if (account == null) {
			account = initAccount(order.getUserId());
		}
		if (roc.getPayType() == TradePayType.ACCOUNT.type) {
			log.info("..........confirm account pay = 0");
			Double usableAmount = account.getAmount().doubleValue();
			if ((order.getActualAmount().doubleValue() <= usableAmount)) {
				// 调起结账接口
				WalletDetail wd = new WalletDetail();
				wd.setAccountAmount(
						new BigDecimal(account.getAmount().doubleValue() - order.getActualAmount().doubleValue()));
				wd.setAmount(order.getActualAmount());
				wd.setAccountId(account.getId());
				wd.setType(WalletDetail.TYPE_CONSUME);
				wd.setCreateTime(new Date());
				this.walletDetailMasterMapper.save(wd);
				log.info("order:{}", JsonUtil.toJson(wd));

				order.setPayType(orderPayType);
				order.setEndTime(endTime);
				this.updateConfirm(order);

				this.checkOutOrder(order, null, wd);

				confirm = new ResOrderConfirm();
				confirm.setAmount(new BigDecimal(0.0D));
				confirm.setNumber(null);
				confirm.setPayType((short) orderPayType);
				return getConfirmResult(confirm);
			} else {
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
			orderPayType = OrderPayType.ACCOUNT.type + roc.getPayType();
			order.setPayType(orderPayType);
			order.setEndTime(endTime);
			this.updateConfirm(order);
			this.putOs(request, order.getUserId());
			// 支付宝 支付
			if (roc.getPayType() == TradePayType.ALIPAY.type) {
				ReqAppAlipay alipay = new ReqAppAlipay();
				alipay.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				alipay.setNumber(rechargeRecord.getCode());
				String info = appAlipayClient.order(alipay);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) TradePayType.ALIPAY.type);
				confirm.setAlipay(info);
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.WECHAT.type) {
				ReqAppWechatOrder reqawo = new ReqAppWechatOrder();
				reqawo.setAddress(request.getLocalAddr());
				reqawo.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				reqawo.setNumber(rechargeRecord.getCode());
				ResAppWechatOrder rawo = this.appWechatClient.order(reqawo);
				log.info("get wechat order:{}", JsonUtil.toJson(rawo));
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) TradePayType.WECHAT.type);
				ResOrderWeixin row = new ResOrderWeixin();
				row.setAppid(rawo.getAppid());
				row.setPartnerid(rawo.getPartnerid());
				row.setPrepayid(rawo.getPrepayid());
				row.setTimestamp(rawo.getTimestamp());
				row.setNoncestr(rawo.getNoncestr());
				row.setSign(rawo.getSign());
				confirm.setWeixin(row);
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.APPLE.type) {
				ReqApplePay rap = new ReqApplePay();
				rap.setTimestramp(new Date().getTime());
				rap.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				rap.setNumber(rechargeRecord.getCode());
				String tn = this.applePayClient.order(rap);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) roc.getPayType());
				confirm.setApple(tn);
				log.info("apple confir :{}", JsonUtil.toJson(confirm));
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.UNION.type || roc.getPayType() == TradePayType.HUAWEI.type
					|| roc.getPayType() == TradePayType.XIAOMI.type) {
				ReqApplePay rap = new ReqApplePay();
				rap.setTimestramp(new Date().getTime());
				rap.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				rap.setNumber(rechargeRecord.getCode());
				String tn = this.applePayClient.order(rap);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) roc.getPayType());
				confirm.setUnion(tn);
				log.info("union confir :{}", JsonUtil.toJson(confirm));
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.WECHAT_MINI.type) {
				ReqWechatMiniOrder wechat = new ReqWechatMiniOrder();
				wechat.setAddress(request.getLocalAddr());
				wechat.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				wechat.setNumber(rechargeRecord.getCode());
				wechat.setOpenId(cu.getOpenId());
				ResWechatMiniOrder mini = this.wechatMiniClient.order(wechat);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPaymentAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) TradePayType.WECHAT_MINI.type);
				ResPayConfirm res = new ResPayConfirm();
				res.setAmount(confirm.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				res.setPayType(confirm.getPayType());
				res.setNumber(confirm.getNumber());
				ResPayWeixinMini wxMini = new ResPayWeixinMini();
				wxMini.setId(mini.getId());
				wxMini.setNonce(mini.getNonce());
				wxMini.setPack(mini.getPack());
				wxMini.setSign(mini.getSign());
				wxMini.setStamp(mini.getStamp());
				wxMini.setType(mini.getType());
				res.setWeixinMini(wxMini);
				return res;
			} else if(roc.getPayType() == TradePayType.LOONG.type) {
				ReqLongPay longpay = new ReqLongPay();
				longpay.setAmount(rechargeRecord.getPaymentAmount());
				longpay.setOrderId(rechargeRecord.getCode());
				longpay.setUserId(cu.getId());
				ResLoongPay resLoongpay = appLoongPayClient.order(longpay);
				ResPayConfirm res = new ResPayConfirm();
				res.setPayType((short)TradePayType.LOONG.type);
				res.setNumber(rechargeRecord.getCode());
				res.setAmount(rechargeRecord.getPaymentAmount().doubleValue());
				res.setResLoongPay(new cn.linkmore.order.controller.app.response.ResLoongPay(resLoongpay.getSign(), resLoongpay.getThirdAppInfo()));
				return res;
				
			}else {
				throw new BusinessException(StatusEnum.ORDER_UNKNOW_PAY);
			}

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(StatusEnum.ORDER_PAY_SIGN_ERROR);
		}
	}

	private void updateConfirm(Orders order) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", order.getId());
		param.put("payType", order.getPayType());
		param.put("couponId", order.getCouponId());
		param.put("couponAmount", order.getCouponAmount());
		param.put("endTime", order.getEndTime());
		param.put("totalAmount", order.getTotalAmount());
		param.put("actualAmount", order.getActualAmount());
		this.orderMasterMapper.updateConfirm(param);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	private void checkOutOrder(Orders order, RechargeRecord rechargeRecord, WalletDetail wd) {
		TradeRecord tradeRecord = null;
		if (rechargeRecord != null) {
			// 充值
			tradeRecord = this.recharge(rechargeRecord);
		}
		// 支付
		this.payment(order, tradeRecord, wd);
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
		trade.setType((short) TradeType.ORDER_RECHARGE.type);
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
		account.setUsableAmount(
				new BigDecimal(account.getUsableAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		account.setRechargeAmount(
				new BigDecimal(account.getRechagePaymentAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		account.setRechagePaymentAmount(new BigDecimal(
				account.getRechagePaymentAmount().doubleValue() + trade.getPaymentAmount().doubleValue()));
		account.setUpdateTime(new Date());
		accountMasterMapper.update(account);

		CompanyTradeRecord ctr = companyTradeRecordClusterMapper.findLast();
		CompanyTradeRecord newCtr = new CompanyTradeRecord();
		newCtr.copy(ctr);
		newCtr.setCreateTime(new Date()); 
		newCtr.setUpdateTime(new Date());
		newCtr.setTradeId(trade.getId());
		newCtr.setTotalAmount(
				new BigDecimal(newCtr.getTotalAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		newCtr.setRechargeAmount(
				new BigDecimal(newCtr.getRechargeAmount().doubleValue() + trade.getTradeAmount().doubleValue()));
		newCtr.setRechargePaymentAmount(
				trade.getPaymentAmount().doubleValue() + newCtr.getRechargePaymentAmount().doubleValue());
		companyTradeRecordMasterMapper.save(newCtr);
		return trade;
	}

	private String getRechargeNumer() {
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Long increment = this.redisService.increment(RedisKey.ORDER_RECHARGE_SERIAL_NUMBER.key + sdf.format(day), 1);
		Double t = Math.pow(10, baseConfig.getRechargeNumber());
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.intValue() + increment);
		return number.toString();
	}

	private String getTradeNumber() {
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Long increment = this.redisService.increment(RedisKey.ORDER_TRADE_SERIAL_NUMBER.key + sdf.format(day), 1);
		Double t = Math.pow(10, baseConfig.getTradeNumber());
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.intValue() + increment);
		return number.toString();
	}

	/**
	 * 结账扣款
	 * 
	 * @param order
	 * @param tradeRecord
	 */
	private void payment(Orders order, TradeRecord tradeRecord, WalletDetail wd) {
		Date current = new Date();
		Long rechargeTradeId = null;
		short tradeType = (short) TradeType.ORDER_NONE_PAY.type;
		if (null != tradeRecord) {
			rechargeTradeId = tradeRecord.getId();
			tradeType = (short) TradeType.ORDER_RECHARGE_PAY.type;
		} else if (null != wd) {
			tradeType = (short) TradeType.ORDER_ACCOUNT_PAY.type;
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
		if (null != wd) {
			account.setAmount(
					new BigDecimal(account.getAmount().doubleValue() - order.getActualAmount().doubleValue()));
			if (account.getUsableAmount().doubleValue() >= order.getActualAmount().doubleValue()) {
				account.setUsableAmount(new BigDecimal(
						account.getUsableAmount().doubleValue() - order.getActualAmount().doubleValue()));
			} else {
				account.setGiftAmount(new BigDecimal(account.getGiftAmount().doubleValue()
						- (order.getActualAmount().doubleValue() - account.getUsableAmount().doubleValue())));
				account.setUsableAmount(new BigDecimal(0D));
			}
		} else {
			account.setUsableAmount(
					new BigDecimal(account.getUsableAmount().doubleValue() - order.getActualAmount().doubleValue()));
			account.setAmount(
					new BigDecimal(account.getAmount().doubleValue() - order.getActualAmount().doubleValue()));
		}

		account.setOrderAmount(
				new BigDecimal(account.getOrderAmount().doubleValue() + order.getTotalAmount().doubleValue()));
		account.setOrderPaymentAmount(
				new BigDecimal(account.getOrderPaymentAmount().doubleValue() + order.getActualAmount().doubleValue()));
		account.setUpdateTime(current);
		accountMasterMapper.update(account);

		CompanyTradeRecord ctr = companyTradeRecordClusterMapper.findLast();
		CompanyTradeRecord newCtr = new CompanyTradeRecord();
		newCtr.copy(ctr);
		newCtr.setCreateTime(new Date());
		newCtr.setUpdateTime(new Date());
		newCtr.setTradeId(payTradeRecord.getId());
		newCtr.setTotalAmount(
				new BigDecimal(newCtr.getTotalAmount().doubleValue() + payTradeRecord.getTradeAmount().doubleValue()));
		newCtr.setRechargeAmount(new BigDecimal(
				newCtr.getRechargeAmount().doubleValue() + payTradeRecord.getTradeAmount().doubleValue()));
		newCtr.setRechargePaymentAmount(
				payTradeRecord.getPaymentAmount().doubleValue() + newCtr.getRechargePaymentAmount().doubleValue());
		companyTradeRecordMasterMapper.save(newCtr);

		// 4.修改车位信息
		/*
		 * 检查车位状态是否为预下线，-->下线 否则 --> 可租用 如果订单为已挂起状态，不修改车位状态
		 */

		// OrdersDetail od = ordersDetailClusterMapper.findByOrderId(order.getId());
		if (order.getStatus() == OrderStatus.UNPAID.value || order.getStatus() == OrderStatus.SUSPENDED.value) {
			try {
				log.info(">>>>>>stall checkout thread preName = {},stallName = {}, mobile= {}",order.getPreName(),order.getStallName(),order.getUsername());
				new Thread(new StallCheckoutThread(order.getStallId())).start();
			} catch (Exception e) {
				log.info("up lock throw exception = {}",e.getMessage());
			}
		}
		// 更新订单
		order.setStatus(OrderStatus.COMPLETED.value);
		order.setUpdateTime(current);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", order.getId());
		param.put("status", OrderStatus.COMPLETED.value);
		param.put("updateTime", current);
		param.put("endTime", current);
		param.put("payTime", current);
		param.put("tradeId", payTradeRecord.getId());
		this.orderMasterMapper.updatePayment(param);
		// 3.更新优惠券信息
		if (null != order.getCouponId()) {
			ReqCouponPay rcp = new ReqCouponPay();
			rcp.setCouponId(order.getCouponId());
			rcp.setOrderAmount(order.getTotalAmount());
			rcp.setUsedAmount(order.getCouponAmount());
			this.couponClient.pay(rcp);
		}
		this.userClient.checkout(order.getUserId());
		// 结账调用新版推送消息
		Thread thread = new ProduceCheckBookingThread(order);
		thread.start();
		thread = new PushThread(order.getUserId().toString(), "订单支付通知", "支付成功", PushType.ORDER_COMPLETE_NOTICE, true);
		thread.start();
	}

	class PushThread extends Thread {
		private String uid;
		private String title;
		private String content;
		private PushType type;
		private Boolean status;

		public PushThread(String uid, String title, String content, PushType type, Boolean status) {
			this.uid = uid;
			this.title = title;
			this.content = content;
			this.type = type;
			this.status = status;
		}

		public void run() {
			send(uid, title, content, type, status);
		}

	}

	private void send(String uid, String title, String content, PushType type, Boolean status) {
		String os = headerOs.get(Long.decode(uid));
		Token token = null;
		if(os ==  null) {
			os = "0";
			token = (Token) this.redisService.get(appUserFactory.createUserIdRedisKey(Long.decode(uid), os));
			if(token == null) {
				token = (Token) this.redisService.get(appUserFactory.createUserIdRedisKey(Long.decode(uid), "1"));
			}
		}else {
			token = (Token) this.redisService.get(appUserFactory.createUserIdRedisKey(Long.decode(uid), os));
		}
		headerOs.remove(Long.decode(uid));
		if (token.getClient().intValue() == ClientSource.WXAPP.source) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			map.put("type", type.id);
			map.put("content", content);
			map.put("status", status);
			CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(token.getAccessToken(),null));
			userSocketClient.push(JsonUtil.toJson(map), cu.getOpenId());
		} else {
			ReqPush rp = new ReqPush();
			rp.setAlias(uid);
			rp.setTitle(title);
			rp.setContent(content);
			rp.setClient(token.getClient());
			rp.setType(type);
			rp.setData(status.toString());
			this.pushClient.push(rp);
		}

	}

	class ProduceCheckBookingThread extends Thread {
		private Orders order;

		public ProduceCheckBookingThread(Orders order) {
			this.order = order;
		}

		public void run() {
			try {
				if (StringUtils.isNotBlank(order.getDockId())) {
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

	class StallCheckoutThread extends Thread {
		private Long stallId;

		public StallCheckoutThread(Long stallId) {
			this.stallId = stallId;
		}

		@Override
		public void run() {
			log.info(">>>>>>>>>>>>>checkout>>>>>>>>>>>>{}",stallId);
			Boolean checkoutFlag = stallClient.checkout(stallId);
			log.info(">>>>>>>>>>>>>checkout checkoutFlag>>>>>>>>>>>>{}",checkoutFlag);
		}
	}

	@Override
	public ResOrderDetail verify(Long orderId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResUserOrder order = this.ordersClusterMapper.findUserLatest(cu.getId());
		Boolean flag = false;

		if (order == null || order.getUserId().longValue() != cu.getId().longValue()) {
			flag = false;
		} else if (order.getStatus().intValue() == OrderStatus.COMPLETED.value) {
			flag = true;
		}
		log.info("orderId:{},userId:{},verify:{},order:{}", orderId, cu.getId(), flag, JsonUtil.toJson(order));
		ResOrderDetail detail = null;
		if (flag && order.getUserId().longValue() == cu.getId().longValue()) {
			ResPrefectureDetail pre = prefectureClient.findById(order.getPreId());
			detail = new ResOrderDetail();
			detail.copy(order);
			//实际支付金额大于0发送停车券
			if(detail.getActualAmount().doubleValue() > 0) {
				log.info("pay send userId = {}, actualAmount = {}",order.getUserId(),detail.getActualAmount());
				couponClient.paySend(order.getUserId(), 7);
			}
			if (pre != null) {
				detail.setLeaveTime(pre.getLeaveTime());
			} else {
				detail.setLeaveTime(15);
			}
		}else {
			if(order!= null && order.getUserId().longValue() == cu.getId().longValue()) {
				log.info("get the order null reset the value falg :{}, order:{}", flag, JsonUtil.toJson(order));
				ResPrefectureDetail pre = prefectureClient.findById(order.getPreId());
				detail = new ResOrderDetail();
				detail.copy(order);
				if (pre != null) {
					detail.setLeaveTime(pre.getLeaveTime());
				} else {
					detail.setLeaveTime(15);
				}
			}else {
				log.info("verfiy error");
			}
		}
		return detail;
	}

	private Boolean alipay(String json) {
		Boolean flag = false;
		Map<String, String> map = JsonUtil.toObject(json, HashMap.class);
		String number = map.get("out_trade_no");
		RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
		flag = this.appAlipayClient.verify(json, number,
				rr.getPaymentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		log.info("alipay verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Orders orders = ordersClusterMapper.findById(rr.getOrderId());
			this.checkOutOrder(orders, rr, null);
			flag = true;
		}
		return flag;
	}

	private Boolean apple(String json) {
		Boolean flag = false;
		flag = this.applePayClient.verify(json);
		log.info("apple verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Map<String, String> param = JsonUtil.toObject(json, HashMap.class);
			String number = param.get("orderId");
			RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
			Orders orders = ordersClusterMapper.findById(rr.getOrderId());
			this.checkOutOrder(orders, rr, null);
			flag = true;
		}

		return flag;
	}

	private Boolean wechat(String json) {
		Boolean flag = false;
		flag = this.appWechatClient.verify(json);
		log.info("App Wechat Pay verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Map<String, String> param = JsonUtil.toObject(json, HashMap.class);
			if ("SUCCESS".equals(param.get("return_code")) && "SUCCESS".equals(param.get("result_code"))) {
				String number = param.get("out_trade_no");
				RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
				Orders orders = ordersClusterMapper.findById(rr.getOrderId());
				this.checkOutOrder(orders, rr, null);
				flag = true;
			}
		}
		return flag;
	}

	private void putOs(HttpServletRequest request,Long uid) {
		String os = request.getHeader("os");
		os = os == null ? "0" : os;
		headerOs.put(uid, os);
	}
	
	private Boolean wechatMini(String json) {
		Boolean flag = false;
		flag = this.wechatMiniClient.verify(json);
		log.info("Mini Wechat verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Map<String, String> param = JsonUtil.toObject(json, HashMap.class);
			if ("SUCCESS".equals(param.get("return_code")) && "SUCCESS".equals(param.get("result_code"))) {
				String number = param.get("out_trade_no");
				RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(number);
				Orders orders = ordersClusterMapper.findById(rr.getOrderId());
				this.checkOutOrder(orders, rr, null);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public void wechatOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(request);
			String json = JsonUtil.toJson(map);
			log.info("wechatOrderNotice:{}", json);
			Boolean flag = this.wechat(json);
			if (flag) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("return_msg", "OK");
				param.put("return_code", "SUCCESS");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<xml>");
				for (Map.Entry<String, String> entry : param.entrySet()) {
					buffer.append("<" + entry.getKey() + ">");
					buffer.append("<![CDATA[" + entry.getValue() + "]]>");
					buffer.append("</" + entry.getKey() + ">");
				}
				buffer.append("</xml>");
				String result = new String(buffer.toString().getBytes(), "utf-8");
				// 微信通知返回业务结果为success 给微信返回成功信息
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write(result);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final static String RESULT_SUCCESS = "success";
	private final static String RESULT_FAILURE = "fail";

	@Override
	public void alipayOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, String> paramMap = new HashMap<>();
			Map<String, String[]> map = request.getParameterMap();
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String param = it.next();
				String[] vals = map.get(param);
				String value = null != vals && vals.length > 0 ? (vals.length == 1 ? vals[0] : null) : null;
				if (null == value) {
					value = "";
					for (String v : vals) {
						value += "," + v;
					}
					value = value.substring(1, value.length());
				}
				paramMap.put(param, value);
			}
			String json = JsonUtil.toJson(paramMap);
			log.info("alipayOrderNotice:{}", json);
			Boolean flag = false;
			try {
				flag = this.alipay(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			PrintWriter pw = response.getWriter();
			pw.print(flag ? RESULT_SUCCESS : RESULT_FAILURE);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void appleOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		Enumeration<String> em = request.getParameterNames();
		String name = null;
		Map<String, String> respData = new HashMap<String, String>();
		while (em.hasMoreElements()) {
			name = em.nextElement();
			respData.put(name, request.getParameter(name));
		}
		String json = JsonUtil.toJson(respData);
		this.apple(json);
	}

	@Override
	public void appleLongOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		String POSTID = request.getParameter("POSID");
		String BRANCHID = request.getParameter("BRANCHID");
		String ORDERID = request.getParameter("ORDERID");
		String PAYMENT = request.getParameter("PAYMENT");
		String CURCODE = request.getParameter("CURCODE");
		String REMARK1 = request.getParameter("REMARK1");
		String REMARK2 = request.getParameter("REMARK2");
		String ACC_TYPE = request.getParameter("ACC_TYPE");
		String SUCCESS = request.getParameter("SUCCESS");
		String TYPE = request.getParameter("TYPE");
		String REFERER = request.getParameter("REFERER");
		String CLIENTIP = request.getParameter("CLIENTIP");
		String ACCDATE = request.getParameter("ACCDATE");
		String USRMSG = request.getParameter("USRMSG");
		String INSTALLNUM = request.getParameter("INSTALLNUM");
		String ERRMSG = request.getParameter("ERRMSG");
		String USRINFO = request.getParameter("USRINFO");
		String DISCOUNT = request.getParameter("DISCOUNT");
		String SIGN = request.getParameter("SIGN");
		StringBuilder result = new StringBuilder();
		result.append("POSID=").append(POSTID).append("&").
		append("BRANCHID=").append(BRANCHID).append("&").
		append("ORDERID=").append(ORDERID).append("&").
		append("PAYMENT=").append(PAYMENT).append("&").
		append("CURCODE=").append(CURCODE).append("&").
		append("REMARK1=").append(REMARK1).append("&").
		append("REMARK2=").append(REMARK2).append("&").
		append("ACC_TYPE=").append(ACC_TYPE).append("&").
		append("SUCCESS=").append(SUCCESS).append("&").
		append("TYPE=").append(TYPE).append("&").
		append("REFERER=").append(REFERER).append("&").
		append("CLIENTIP=").append(CLIENTIP);
		if(StringUtils.isNotBlank(ACCDATE)) {
			result.append("&").append("ACCDATE=").append(ACCDATE);
		}
		if(StringUtils.isNotBlank(USRMSG)) {
			result.append("&").append("USRMSG=").append(USRMSG);
		}
		if(StringUtils.isNotBlank(INSTALLNUM)) {
			result.append("&").append("INSTALLNUM=").append(INSTALLNUM);
		}
		if(StringUtils.isNotBlank(ERRMSG)) {
			result.append("&").append("ERRMSG=").append(ERRMSG);
		}
		if(StringUtils.isNotBlank(USRINFO)) {
			result.append("&").append("USRINFO=").append(USRINFO);
		}
		if(StringUtils.isNotBlank(DISCOUNT)){
			result.append("&").append("DISCOUNT=").append(DISCOUNT);
		}
		ReqLoongPayVerifySign sign = new ReqLoongPayVerifySign();
		sign.setSign(SIGN);
		sign.setSrt(result.toString());
		log.info("【龙支付回调】"+JsonUtil.toJson(sign));
		if(SUCCESS.equals("Y")) {
			boolean b = appLoongPayClient.verifySigature(sign);
			log.info("【龙支付回调校验】"+JsonUtil.toJson(b));
			if(b) {
				RechargeRecord rr = this.rechargeRecordClusterMapper.findByNumber(ORDERID);
				Orders orders = ordersClusterMapper.findById(rr.getOrderId());
				this.checkOutOrder(orders, rr, null);
			}
		}
		
	}

	@Override
	public void wechatMiniOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(request);
			String json = JsonUtil.toJson(map);
			log.info("wechatMiniOrderNotice:{}", json);
			Boolean flag = this.wechatMini(json);
			if (flag) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("return_msg", "OK");
				param.put("return_code", "SUCCESS");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<xml>");
				for (Map.Entry<String, String> entry : param.entrySet()) {
					buffer.append("<" + entry.getKey() + ">");
					buffer.append("<![CDATA[" + entry.getValue() + "]]>");
					buffer.append("</" + entry.getKey() + ">");
				}
				buffer.append("</xml>");
				String result = new String(buffer.toString().getBytes(), "utf-8");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write(result);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.info("wechat mini pay callback exception .");
		}
	}
}
