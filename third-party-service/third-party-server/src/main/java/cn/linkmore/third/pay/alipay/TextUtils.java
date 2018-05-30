package cn.linkmore.third.pay.alipay;

public final class TextUtils
{
  public static boolean isEmpty(CharSequence s)
  {
    if (s == null) {
      return true;
    }
    return s.length() == 0;
  }
  
  public static boolean isBlank(CharSequence s)
  {
    if (s == null) {
      return true;
    }
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isWhitespace(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

	
  public static boolean equals(String key, String strKey) {
	if(isBlank(key) || isBlank(strKey)){
		return false;
	}
	if(key.equals(strKey))
	return true;
	return false;
  }
}
