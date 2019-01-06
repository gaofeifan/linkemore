package cn.linkmore.third.pay.loong;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CCBSign.RSASig;
import cn.linkmore.third.config.AppLoongPayConfig;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.response.ResLoongPay;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;
/**
 * 建行龙支付
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
public class JianHangLong {
	private static Logger log = LoggerFactory.getLogger(JianHangLong.class);
	private static final List<String > macParam = Arrays.asList("INSTALLNUM",
			"SMERID","SMERNAME","SMERTYPEID","SMERTYPE","TRADECODE","TRADENAME",
			"SMEPROTYPE","PRONAME","THIRDAPPINFO","TIMEOUT","NoCredit","GATEWAY","NoDebit","USERNAME","IDNUMBER","NoCredit");
	
	public static ResLoongPay create(ReqLongPay pay,AppLoongPayConfig config) {
		log.info("ReqLongPay====="+JsonUtil.toJson(pay));
		log.info("AppLongPayConfig====="+JsonUtil.toJson(config));
		Map<String, String> map = new HashMap<String, String>();
		map.put("MERCHANTID", config.getMerchantId());
		map.put("POSID", config.getPosId());
		map.put("BRANCHID", config.getBranchId());
		map.put("ORDERID", pay.getOrderId()+"");
		map.put("PAYMENT", pay.getAmount()+"");
		map.put("CURCODE", "01");
		map.put("REMARK1", "");
		map.put("REMARK2", "");
		map.put("CLIENTIP", "");
		map.put("TXCODE", "520100");
		map.put("TYPE", "1");
		map.put("PUB", config.getPubKey().substring(config.getPubKey().length()-30));//公钥后30位
		map.put("GATEWAY", "UnionPay");
//		map.put("CLIENTIP", pay.getUserId()+"");
		map.put("REGINFO", "");//客户注册信息
		map.put("PROINFO", "");//商品信息
		map.put("REFERER", "");//商户URL
		String thirdAppInfo = "comccbpay"+config.getMerchantId() + "alipay";
		map.put("THIRDAPPINFO", thirdAppInfo);//客户端标识
//		map.put("TIMEOUT", "");//订单超时时间
		log.info("data====="+JsonUtil.toJson(map));
		
		StringBuilder propers = new StringBuilder();
		Set<Entry<String,String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			if(macParam.contains(entry.getKey())){
				if(StringUtils.isBlank(entry.getKey())) {
					continue;
				}
			}
			propers.append("&");
			propers.append(entry.getKey()).append("=").append(entry.getValue());
		}
		String substring = propers.substring(1);
		String en = MD5.md5En(substring);
		log.info("龙支付sign 【"+en+"】");
		StringBuilder result = new StringBuilder(config.getUrl()).append("?").append(substring).append("&MAC=").append(en);
		log.info("龙支付pay 【"+result+"】");
		ResLoongPay loongPay = new ResLoongPay();
		loongPay.setThirdAppInfo(thirdAppInfo);
		loongPay.setSign(result.toString());
		return loongPay;
	}


	public static void callbackMsg(Map<String, Object> map) {
		
	}
}
class MD5{
	private static Logger log = LoggerFactory.getLogger(MD5.class);
	public static String md5En(String str) {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(str);
        log.info("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }
}
