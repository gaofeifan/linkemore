package cn.linkmore.third.pay.wxmini;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * 
 * @author duhq
 * 
 */
public class XMLUtil {

	/**
	 * 
	 * 解析String类型的xml流对象InputStream
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> doXMLParse(String strxml)throws IOException, DocumentException {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));;
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element e : elementList){
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;

		return map;
	}

}
