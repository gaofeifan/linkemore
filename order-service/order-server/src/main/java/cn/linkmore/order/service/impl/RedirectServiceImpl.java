package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.Transaction;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.common.client.PayConfigClient;
import cn.linkmore.common.request.ReqFinshOrder;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResFinshOrder;
import cn.linkmore.common.response.ResPayConfig;
import cn.linkmore.order.config.OauthConfig;
import cn.linkmore.order.controller.h5.request.ReqPayParm;
import cn.linkmore.order.controller.h5.request.ReqSerch;
import cn.linkmore.order.controller.h5.response.PayRecord;
import cn.linkmore.order.controller.h5.response.ResPayParm;
import cn.linkmore.order.controller.h5.response.ResSearch;
import cn.linkmore.order.entity.AauthConfig;
import cn.linkmore.order.service.RedirectService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.H5PayClient;
import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;

@Service
public class RedirectServiceImpl implements RedirectService {

	@Autowired
	private OauthConfig oauthConfig;
	@Autowired
	private PayConfigClient payConfigClient;
	@Autowired
	H5PayClient h5PayClient;
	@Autowired
	private AauthConfig AuthConfig;
	@Autowired
	private RedisService redisService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResSearch getOrder(ReqSerch reqSerch) {
		// 查询当前所需支付订单
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("plateNo", reqSerch.getPlate());
		parameters.put("parkCode", reqSerch.getPreId());
		String response = HttpUtil.sendJson(oauthConfig.getParkOrder(), JsonUtil.toJson(parameters));
		Map<String, Object> order = new HashMap<>();
		order = JsonUtil.toObject(response, order.getClass());
		// 查不到订单
		if (order.isEmpty()) {
			throw new BusinessException(StatusEnum.PARK_CODE_NO_ORDER);
		}
		Integer code = Integer.valueOf(String.valueOf(order.get("code")));
		// 已经离场
		if (code == 500) {
			throw new BusinessException(StatusEnum.PARK_CODE_FINISH_);
		}
		;
		// 出错
		if (code != 200) {
			throw new BusinessException(StatusEnum.SERVER_EXCEPTION);
		}
		;
		// 获取当前订单
		Map<String, Object> data = (Map<String, Object>) order.get("data");
		// 查询过往订单
		ReqFinshOrder req = new ReqFinshOrder();
		req.setOrderNo(String.valueOf(data.get("orderNo")));
		List<ResFinshOrder> list = payConfigClient.getOrder(req);

		String entranceTime = data.get("entranceTime").toString();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		long useTime = 0l;
		long entrance  = 0l;
		String orderId = null;
		Map<String, Object> paymsg = new HashMap<>();
		try {
			Date entranceTime1 = sdf.parse(entranceTime);
			entrance = entranceTime1.getTime();
			useTime = now.getTime() -entrance;
			orderId = String.valueOf(now.getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		ResSearch res = new ResSearch();

		res.setBgTime(entrance);
		res.setLocation(data.get("parkName").toString());
		res.setPlate(data.get("plateNumber").toString());

		List<PayRecord> payrecords = new ArrayList<>();
		BigDecimal totalmoney = new BigDecimal(0);
		// 无过往订单
		if (list.isEmpty() && list.size() == 0) {
			totalmoney =  	new BigDecimal(data.get("amount").toString());
			res.setUseTime(useTime);
			res.setMoney(totalmoney);
			res.setPayrecords(payrecords);
			//放入所需支付订单
			paymsg.put("totalmoney",totalmoney);
			paymsg.put("orderNo",data.get("orderNo"));
			paymsg.put("entranceTime",data.get("entranceTime"));
			paymsg.put("plateNumber",data.get("plateNumber"));
			paymsg.put("parkName",data.get("parkName"));
	
			redisService.set("payuser:"+reqSerch.getOpenid(), paymsg, 60000);
			return res;
		}
		BigDecimal alreadyPay = new BigDecimal(0);
		// 整理过往记录及已支付款额
		for (ResFinshOrder resFinshOrder : list) {
			alreadyPay = alreadyPay.add(resFinshOrder.getAmount());
			PayRecord payrecord = new PayRecord();
			payrecord.setFinishTime(resFinshOrder.getFinishTime());
			payrecord.setMoney(resFinshOrder.getAmount());
			payrecords.add(payrecord);
		}
		ResFinshOrder resFinshOrder = list.get(list.size() - 1);
		Date r = resFinshOrder.getFinishTime();
		long staytime = now.getTime() - r.getTime();
		// 未超时
		if (staytime < Long.valueOf(String.valueOf(data.get("freeTime")))) {
			res.setUseTime(staytime);
			res.setMoney(new BigDecimal(0));
			res.setPayrecords(payrecords);
		}
		// 超时
		if (staytime >= Long.valueOf(String.valueOf(data.get("freeTime")))) {
			totalmoney = new BigDecimal(data.get("amount").toString()).subtract(alreadyPay);
			res.setUseTime(staytime);
			res.setMoney(totalmoney);
			res.setPayrecords(payrecords);
			//放入所需支付订单
			paymsg.put("totalmoney",totalmoney);
			paymsg.put("orderNo",data.get("orderNo"));
			paymsg.put("entranceTime",data.get("entranceTime"));
			paymsg.put("plateNumber",data.get("plateNumber"));
			paymsg.put("parkName",data.get("parkName"));
	
			redisService.set("payuser:"+reqSerch.getOpenid(), paymsg, 60000);
		}
		return res;
	}

	/**
	 * 识别客户端并获取code
	 */
	@Override
	public String distributed(Map<String, String> params, HttpServletRequest request) {
		String paytype = null, redirect = null, redirect_uri = null;
		Long preId = Long.valueOf(params.get("preId"));
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf(Transaction.wxclient) > 0) { // 是微信浏览器
			paytype = Transaction.WX;
			redirect_uri = oauthConfig.getWxCodeUrl();
			log.info("paytype->" + paytype);
		} else if (ua.indexOf(Transaction.aliclient) > 0) { // 支付宝浏览器
			paytype = Transaction.ZFB;
			redirect_uri = oauthConfig.getZfbCodeUrl();
			log.info("paytype->" + paytype);
		} else {
			log.info("paytype->" + paytype + "redirect->" + redirect); // 其他浏览器
			redirect_uri = oauthConfig.getH5Url();
			redirect = AuthConfig.getNoCode(redirect_uri);
			return redirect;
		}
		// 获取配置信息
		ReqPayConfig req = new ReqPayConfig();
		req.setPreId(preId);
		req.setType(paytype);
		ResPayConfig config = payConfigClient.getConfig(req);
		String appId = config.getAppId();
		// 跳转微信服务器
		if (paytype == Transaction.WX) {
			redirect = AuthConfig.getWxCode(appId, redirect_uri, preId);
		}
		// 跳转阿里服务器
		if (paytype == Transaction.ZFB) {
			redirect = AuthConfig.getZfbCode(appId, redirect_uri, preId);
		}
		log.info("redirect" + redirect);
		return redirect;
	}

	/**
	 * code换取用户信息
	 */
	@Override
	public String auth(Map<String, String> params) {
		String code = params.get("code");
		String auth_code = params.get("auth_code");
		Long preId = Long.valueOf(params.get("state"));
		String paytype = params.get("type");
		String openId = null;
		ReqPayConfig req = new ReqPayConfig();
		req.setPreId(preId);
		req.setType(paytype);
		ResPayConfig config = payConfigClient.getConfig(req);
		// 获取身份id
		ReqH5Token open = new ReqH5Token();
		open.setAppid(config.getAppId());
		open.setAppsecret(config.getAppSecret());
		// 跳转网页
		ResH5Degree res = new ResH5Degree();
		if (paytype == Transaction.WX) {
			open.setCode(code);
			res = h5PayClient.wxopenid(open);
		}
		if (paytype == Transaction.ZFB) {
			open.setCode(auth_code);
			res = h5PayClient.aliopenid(open);
		}
		if (res != null) {
			openId = res.getOpenid();
		}
		return AuthConfig.h5Index(preId, openId, paytype);
	}

	@Override
	public ResH5Degree Openid(ReqH5Token reqH5Token) {
		ResH5Degree res = h5PayClient.wxopenid(reqH5Token);
		return res;
	}

	@Override
	public ResPayParm wxparm(ReqPayParm reqPayParm) {
		// 查询当前需支付订单
		Map<String, Object> paymsg = (Map<String, Object>) redisService.get("payuser:"+reqPayParm.getOpenId());
		if(paymsg.isEmpty()||paymsg==null) {
			throw new BusinessException(StatusEnum.PARK_CODE_FINISH_);
		}
		String orderId =String.valueOf(paymsg.get("orderNo"));
		String detail = "凌猫停车";
		BigDecimal totalAmount = new BigDecimal(paymsg.get("totalAmount").toString());
		int a = totalAmount.compareTo(new BigDecimal(0) );
		//不足支付标准
        if(a!=1) {
        	throw new BusinessException(StatusEnum.PARK_CODE_FINISH_);
        }
		ReqPayConfig req = new ReqPayConfig();
		req.setPreId(reqPayParm.getPreId());
		req.setType(Transaction.WX);
		ResPayConfig config = payConfigClient.getConfig(req);
		log.info("config---" + JSON.toJSON(config));
		// 获取支付凭证
		ReqH5Term reqH5Term = new ReqH5Term();
		reqH5Term.setNotifyUrl(oauthConfig.getNotifyUrl());
		reqH5Term.setAppId(config.getAppId());
		reqH5Term.setAppSecret(config.getAppSecret());
		reqH5Term.setDetail(detail);
		reqH5Term.setMchId(config.getMchId());
		reqH5Term.setMchKey(config.getMchKey());
		reqH5Term.setOpenId(reqPayParm.getOpenId());
		reqH5Term.setOrderId(orderId);
		reqH5Term.setTotalAmount(totalAmount);
		// 获取支付凭证
		ResH5Term term = h5PayClient.wxpay(reqH5Term);
		if (term == null) {
			return null;
		}
		log.info("term---" + JSON.toJSON(term));
		ResPayParm parm = new ResPayParm();
		parm.setAppId(term.getAppId());
		parm.setNonceStr(term.getNonceStr());
		parm.setPack(term.getPack());
		parm.setPaySign(term.getPaySign());
		parm.setTimeStamp(term.getTimeStamp());
		return parm;
	}

	@Override
	public void wxNotify(Map<String, String> params) {
		//接受微信消息
		
		//通知闸机
		
		//插入订单
	}

}
