package cn.linkmore.third.pay.loong;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import CCBSign.RSASig;
import cn.linkmore.third.config.AppLoongPayConfig;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.request.ReqLoongPayVerifySign;
import cn.linkmore.third.response.ResLoongPay;
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
	
	private static final String[] sortParam = new String[] {
			"MERCHANTID","POSID","BRANCHID","ORDERID","PAYMENT","CURCODE","REMARK1","REMARK2","TXCODE","MAC","TYPE","GATEWAY","CLIENTIP","REGINFO","PROINFO","REFERER"};;
	

	public static ResLoongPay create(ReqLongPay pay,AppLoongPayConfig config) {
		log.info("ReqLongPay====="+JsonUtil.toJson(pay));
		log.info("AppLongPayConfig====="+JsonUtil.toJson(config));
		String pub = "PUB="+(config.getPubKey().substring(config.getPubKey().length()-30))+("&");
		String thirdAppInfo = "comccbpay"+config.getMerchantId() + "alipay";
		StringBuilder param = new StringBuilder();
		param.append("MERCHANTID=").append(config.getMerchantId()).append("&").
			  append("POSID=").append(config.getPosId()).append("&").
			  append("BRANCHID=").append(config.getBranchId()).append("&").
			  append("ORDERID=").append(pay.getOrderId()).append("&").
			  append("PAYMENT=").append(pay.getAmount().doubleValue()).append("&").
			  append("CURCODE=").append("01").append("&").
			  append("TXCODE=").append(520100).append("&").
			  append("REMARK1=").append("").append("&").
			  append("REMARK2=").append("").append("&").
			  append("TYPE=").append(1).append("&").
			  append(pub).
			  append("GATEWAY=").append("").append("&").
			  append("CLIENTIP=").append("").append("&").
			  append("REGINFO=").append("").append("&").
			  append("PROINFO=").append("").append("&").
			  append("REFERER=").append("").
			  append("THIRDAPPINFO=").append(thirdAppInfo);
//		map.put("THIRDAPPINFO", thirdAppInfo);//客户端标识
//		map.put("TIMEOUT", "");//订单超时时间
		String en = MD5.md5En(param.toString());
		log.info("龙支付mac 【"+param.toString()+"】");
		log.info("龙支付mac 【"+en+"】");
		String url = param.insert(0, config.getUrl()+"?").append("&").append("MAC=").append(en).toString().replaceAll(pub, "");
		log.info("龙支付pay 【"+url+"】");
		ResLoongPay loongPay = new ResLoongPay();
		loongPay.setThirdAppInfo(thirdAppInfo);
		loongPay.setSign(url);
		return loongPay;
	}
/*	public static ResLoongPay create(ReqLongPay pay,AppLoongPayConfig config) {
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
		map.put("GATEWAY", "");
		map.put("CLIENTIP", "");
		map.put("REGINFO", "");//客户注册信息
		map.put("PROINFO", "");//商品信息
		map.put("REFERER", "");//商户URL
//		String thirdAppInfo = "comccbpay"+config.getMerchantId() + "alipay";
//		map.put("THIRDAPPINFO", thirdAppInfo);//客户端标识
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
//		loongPay.setThirdAppInfo(thirdAppInfo);
		loongPay.setSign(result.toString());
		return loongPay;
	}
*/
	public static void main(String[] args) {
		AppLoongPayConfig bean = new AppLoongPayConfig();
		bean.setBranchId("110000000");
		bean.setMerchantId("105002573995156");
		bean.setPosId("029915416");
		bean.setPubKey("30819d300d06092a864886f70d010101050003818b0030818702818100a121d79346cc2f116e55630f38f620fe8e599595a749c7335a2c8518fc541ae2ffa4862a1425574cff5fb001b64f7a74731ef156cb75029b3d0593634daa3ac3fe59e1b11dec0bd7b3b3257e44d4313be40540c9eed343ad6136c89d8c571d04299b2c2d2bf83a9d28c37dc77a731886fd868f7153b3bc1e361390364f4f0f77020113");
		bean.setUrl("https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain");
		ReqLongPay pay = new ReqLongPay();
		pay.setAmount(new BigDecimal(0.01));
		pay.setOrderId("sdfsdfsdfsd");
		pay.setUserId(77255L);
		ResLoongPay resLoongPay = create(pay , bean);
		System.out.println(JsonUtil.toJson(resLoongPay));
		
	}

	public static void callbackMsg(Map<String, Object> map) {
		
	}
	
	public static boolean verifySigature(ReqLoongPayVerifySign verifySign) {
		log.info(JsonUtil.toJson(verifySign));
		RSASig sig = RSASigSingle.getInstance(verifySign.getPub());
		return sig.verifySigature(verifySign.getSign(), verifySign.getSrt());
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
