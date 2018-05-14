package cn.linkmore.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @author leihe@uworks.cc
 */
public class StringUtil {

  /**
   * <pre>
   * 判断是否为空，为空则返回true
   * 为空的条件：null、""、"null"
   * </pre>
   * 
   * @param obj
   * @return
   */
  public static boolean isBlank(Object obj) {
    if (obj == null) {
      return true;
    }
    String str = obj.toString().trim();
    if ("".equals(str) || "null".equalsIgnoreCase(str)) {
      return true;
    }
    return false;
  }

  /**
   * <pre>
   * 判断是否不为空，不为空则返回true
   * 为空的条件：null、""、"null"
   * </pre>
   * 
   * @param obj
   * @return
   */
  public static boolean isNotBlank(Object obj) {
    return !isBlank(obj);
  }

  /**
   * <pre>
   * 获取换行符
   * </pre>
   * 
   * @return
   */
  public static String getNewLine() {
    return System.getProperty("line.separator");
  }

  /**
   * 获取uuid字符串
   */
  public static String uuid() {
    return UUID.randomUUID().toString();
  }

  /**
   * 获取uuid字符串，无横杠
   */
  public static String uuidNotLine() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  /**
   * 去除前后空格，若obj为null返回""空字串
   * 
   * @param obj
   * @return
   */
  public static String trim(Object obj) {
    if (obj == null) {
      return "";
    }
    return obj.toString().trim();
  }

  /**
   * <p>
   * 生成随机码
   *
   * <p>
   * 若指定的长度为0，则返回空白字符串
   *
   * @param codeLength
   *          随机码长度
   * @return
   */
  public static String randomCode(int codeLength) {
    if (codeLength <= 0) {
      return "";
    }

    long powResult = 1L;
    for (int i = 0; i < codeLength / 2; i++) {
      powResult *= 10;
    }
    if ((codeLength & 0x1) == 0) {
      powResult *= powResult;
    } else {
      powResult *= powResult * 10;
    }
    long randomResult = RandomUtils.nextLong(new Random(System.currentTimeMillis()));
    if (randomResult < 0) {
      randomResult += Long.MAX_VALUE;
    }
    StringBuilder code = new StringBuilder(Long.toString(randomResult));
    if (code.length() > codeLength) {
      code.setLength(codeLength);
    } else {
      int[] source = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
      int sourceHash = source.hashCode();
      int index = code.length();
      while ((index < codeLength)) {
        code.append(sourceHash % 10);
        sourceHash /= 10;
        index++;
      }
    }
    return String.valueOf(code);
  }

}
