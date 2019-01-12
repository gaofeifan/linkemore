package cn.linkmore.third.pay.loong;

import CCBSign.RSASig;

public class RSASigSingle {

	private RSASigSingle() {}
	
	private static RSASig sig = null;
	
	public static RSASig getInstance(String pub) {
		if(sig == null) {
			synchronized(RSASigSingle.class) {
				if(sig == null) {
					sig = new RSASig();
					sig.setPublicKey(pub);
				}
			}
		}
		return sig;
	}
}
