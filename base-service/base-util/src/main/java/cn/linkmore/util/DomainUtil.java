package cn.linkmore.util;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/**
 * 工具类 - 实体与数据库表
 * @author liwenlong
 * @version 1.0
 *
 */
public class DomainUtil {
	public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     * 
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     * 
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串2
     * 
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    } 
    
    /**
	 * 验证手机号码
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 验证集合中是否存在重复数据
	 * 
	 * @param list
	 * @return boolean
	 */
	public static boolean hasSame(List<? extends Object> list) {
		if (null == list)
			return false;
		return list.size() == new HashSet<Object>(list).size();
	}
	
	/**
	 * 车牌号
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean checkPlateNo(String str) {
		String regExp = "^([\\u4e00-\\u9fa5][a-zA-Z](([DF](?![IO])[A-Z0-9][0-9]{4})|([0-9]{5}[DF])))|([冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使A-Z]{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳领]{1})$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
