package cn.linkmore.third.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;

import cn.linkmore.third.pay.wxmini.HttpTool;
import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;
import cn.linkmore.third.service.H5PayService;
import cn.linkmore.third.trade.ObjectUtils;
import cn.linkmore.third.trade.ThreadRepertory;
import cn.linkmore.third.trade.wx.PayCommonUtil;
import cn.linkmore.third.trade.wx.UniPayReqData;
import cn.linkmore.third.trade.wx.WeChatPay;

@Service
public class H5PayServiceImpl implements H5PayService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResH5Degree wxOpenid(ReqH5Token reqH5Token) {
		String appId = reqH5Token.getAppid();
		String code = reqH5Token.getCode();
		String secret = reqH5Token.getAppsecret();
		ResH5Degree res = new ResH5Degree();
		String openid = WeChatPay.oauth2GetOpenid(appId, code, secret);
		res.setOpenid(openid);
		log.info("wxOpenid>>>>>>" + openid);
		return res;
	}

	@Override
	public ResH5Degree aliOpenid(ReqH5Token reqH5Token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResH5Term wxpay(ReqH5Term reqH5Term) {
		ResH5Term resParm = new ResH5Term();
		try {
			log.info("存储配置信息>>>" + JSON.toJSON(reqH5Term));
			Map<String, Object> map = ObjectUtils.objectToMap(reqH5Term);
			ThreadRepertory.setParm(map);

			log.info("发起订单>>>" + reqH5Term.getOrderId());
			
			Date now = new Date();
			Date afterDate = new Date(now .getTime() + 300000);
			
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");  	
			String timeStart = formatter.format(now),timeExpire = formatter.format(afterDate);
			
			String requestXml = (String) UniPayReqData.getUniPayReq(reqH5Term.getOrderId(), reqH5Term.getOrderId(), 
					reqH5Term.getOrderId(), reqH5Term.getTotalAmount(), reqH5Term.getOrderId(), timeStart, 
					timeExpire, "JSAPI", reqH5Term.getOrderId(), reqH5Term.getOpenId(),reqH5Term.getAppId(),reqH5Term.getMchId(),reqH5Term.getMchKey());
			
			String response = HttpTool.sendPostUrl("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXml, "UTF-8");
			log.info("解析响应>>>" + reqH5Term.getOrderId()+requestXml);
			Map<String, Object> unioPayResponseMap = PayCommonUtil.getMapFromXML(response);
			
			if (!unioPayResponseMap.get("return_code").toString().equalsIgnoreCase("SUCCESS")) {
				log.info("发起失败 returnMsg>>>" + unioPayResponseMap.get("return_msg"));
				return null;
			}
			if (!unioPayResponseMap.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				log.info("发起失败 errCode>>>" + unioPayResponseMap.get("err_code"));
				log.info("发起失败 errCodeDes>>>" + unioPayResponseMap.get("err_code_des"));
				return null;
			}
			String noceStr = PayCommonUtil.getRandomString(32); // 随机串
			String preypayId = "prepay_id=" + unioPayResponseMap.get("prepay_id").toString();
			long timeStamp = Calendar.getInstance().getTimeInMillis(); // 时间戳

			SortedMap<String, Object> signMap = new TreeMap<String, Object>();
			signMap.put("appId", reqH5Term.getAppId());
			signMap.put("key", reqH5Term.getMchKey());
			signMap.put("timeStamp", timeStamp);
			signMap.put("package", preypayId);
			signMap.put("nonceStr", noceStr);
			signMap.put("signType", "MD5");
			String paySign = PayCommonUtil.createSign(signMap); // 签名
			log.info("签名>>>" + paySign);

			resParm.setTimeStamp(timeStamp);
			resParm.setPaySign(paySign);
			resParm.setAppId(reqH5Term.getAppId());
			resParm.setNonceStr(noceStr);
			resParm.setPack(preypayId);

		} catch (ParserConfigurationException | IOException | SAXException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			log.info("清除配置信息返回参数>>>");
			ThreadRepertory.removeParm();
		}
		return resParm;
	}

	@Override
	public ResH5Term alipay(ReqH5Term reqH5Term) {
		// TODO Auto-generated method stub
		return null;
	}

}