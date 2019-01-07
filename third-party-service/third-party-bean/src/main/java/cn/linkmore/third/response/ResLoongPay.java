package cn.linkmore.third.response;

public class ResLoongPay {

	private String sign;
	
	private String thirdAppInfo;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getThirdAppInfo() {
		return thirdAppInfo;
	}

	public void setThirdAppInfo(String thirdAppInfo) {
		this.thirdAppInfo = thirdAppInfo;
	}

	@Override
	public String toString() {
		return "ResLoongPay [sign=" + sign + ", thirdAppInfo=" + thirdAppInfo + "]";
	}
	
	
}
