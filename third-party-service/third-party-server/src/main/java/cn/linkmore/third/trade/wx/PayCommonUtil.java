package cn.linkmore.third.trade.wx;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * changlei
 */
public class PayCommonUtil {
	static Logger logger = LoggerFactory.getLogger(PayCommonUtil.class);
	 //微信参数配置  
    public static String API_KEY="";  
    public static String APPID="";  
    public static String MCH_ID="";  
    public static String DEVICE_INFO;
    //随机字符串生成  
    public static String getRandomString(int length) { //length表示生成字符串的长度      
           String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";         
           Random random = new Random();         
           StringBuffer sb = new StringBuffer();         
           for (int i = 0; i < length; i++) {         
               int number = random.nextInt(base.length());         
               sb.append(base.charAt(number));         
           }         
           return sb.toString();         
        }    
    //请求xml组装  
      public static String getRequestXml(SortedMap<String,Object> parameters){  
            StringBuffer sb = new StringBuffer();  
            sb.append("<xml>");  
            Set es = parameters.entrySet();  
            Iterator it = es.iterator();  
            while(it.hasNext()) {  
                Map.Entry entry = (Map.Entry)it.next();  
                String key = (String)entry.getKey();  
                String value = (String)entry.getValue();  
                if ("detail".equalsIgnoreCase(key) /*|| "attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)*/) {  
                    sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");  
                }else {  
                    sb.append("<"+key+">"+value+"</"+key+">");  
                }  
            }  
            sb.append("</xml>");  
            return sb.toString();  
        }  
      //生成签名  
      public static String createSign(String characterEncoding,SortedMap<String,Object> parameters){  
    	  try {
            StringBuffer sb = new StringBuffer();  
            Set es = parameters.entrySet();  
            Iterator it = es.iterator();  
            while(it.hasNext()) {  
                Map.Entry entry = (Map.Entry)it.next();  
                String k = (String)entry.getKey();  
                Object v = entry.getValue();  
                if(null != v && !"".equals(v)  
                        && !"sign".equals(k) && !"key".equals(k)) {  
                    sb.append(k + "=" + v + "&");  
                }  
            }  
            sb.append("key=" + "");
            logger.info(sb.toString());
            String sign = MD5.MD5Encode(sb.toString()).toUpperCase();  
            logger.info("sign->" + sign);
            return sign;  
    	  }catch(Exception e) {
    		  e.printStackTrace();
    	  }
    	  return null;
        }  
      //生成签名  
      public static String createSign(SortedMap<String,Object> parameters){  
    	  try {
    		  String sign = Signature.getSign(parameters);
    		  return sign;
    	  }catch(Exception e) {
    		  e.printStackTrace();
    	  }
    	  return null;
        }  
      public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

          //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder = factory.newDocumentBuilder();
          InputStream is =  Util.getStringStream(xmlString);
          Document document = builder.parse(is);

          //获取到document里面的全部结点
          NodeList allNodes = document.getFirstChild().getChildNodes();
          Node node;
          Map<String, Object> map = new HashMap<String, Object>();
          int i=0;
          while (i < allNodes.getLength()) {
              node = allNodes.item(i);
              if(node instanceof Element){
                  map.put(node.getNodeName(),node.getTextContent());
              }
              i++;
          }
          return map;

      }
}
