package cn.linkmore.prefecture.util;

import com.linkmore.lock.bean.AbuttingBean;
import com.linkmore.lock.factory.LockFactory;

public class InitLockFactory {
	private static LockFactory lockFactory;

	private InitLockFactory() {
	}

	public static LockFactory getInstance() {
		if (lockFactory == null) {
			lockFactory = LockFactory.getInstance();
			AbuttingBean abuttingBean = new AbuttingBean();
			abuttingBean.setHuaQingUrl("http://plockopen.huaching.com/api");
			abuttingBean.setLinkmoreUrl("http://192.168.1.81:8081");
			lockFactory.setAbuttingBean(abuttingBean);
		}
		return lockFactory;
	}
}
