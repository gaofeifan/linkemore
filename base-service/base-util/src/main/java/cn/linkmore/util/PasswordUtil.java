package cn.linkmore.util;


import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 工具类 - 密码加解密
 * @author liwenlong
 * @version 1.0
 *
 */
public class PasswordUtil {

	  private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	  /**
	   * 加密
	   * 
	   * @param password
	   * @return
	   */
	  public static String encode(String password) {
	    return encoder.encode(password);
	  }

	  /**
	   * 验证密码正确性
	   * 
	   * @param password：原始密码
	   * @param encodedPassword：加密过后的密码
	   * @return
	   */
	  public static boolean checkPassword(String password, String encodedPassword) {
	    return encoder.matches(password, encodedPassword);
	  }
	  
	  /**
	   * sha256 加密
	   * 
	   * @param password：原始密码
	   * @param encodedPassword：加密过后的密码
	   * @return
	   */
	  public static String encodeForSha(String password){
	    try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(password.getBytes("UTF-8"));
			return Hex.encodeHexString(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return null;  
		}
	  }
	  
	  
}
