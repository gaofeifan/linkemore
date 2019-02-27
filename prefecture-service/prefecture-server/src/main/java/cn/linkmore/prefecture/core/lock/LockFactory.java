package cn.linkmore.prefecture.core.lock;

import cn.linkmore.prefecture.request.ReqControlLock;

public class LockFactory {

	private ReqControlLock reqc;
	private LockService lockService = null;

	private LockFactory() {};
	
	public static LockFactory getInstance() {
		return LazyLockPactory.factory;
	}
	
	private static class LazyLockPactory{
		private static LockFactory factory = new LockFactory();
	}
	
	
	public synchronized LockService getLock(Class<?> clazz) {
		if(AppLockDecorator.class.isInstance(clazz)) {
			return new AppLockDecorator(lockService, reqc);
		}else if(AppLockMessageDecorator.class.isInstance(clazz)) {
			return new AppLockMessageDecorator(lockService, reqc);
		}else {
			return new LockServiceImpl();
		}
	}
	
	public LockService getLock() {
		return getLock(null);
	}
	
	public synchronized LockFactory setReqc(ReqControlLock reqc) {
		this.reqc = reqc;
		return this;
	}

	
}
