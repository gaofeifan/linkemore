package cn.linkmore.prefecture.core.lock;

import cn.linkmore.prefecture.request.ReqControlLock;

public class AppLockMessageDecorator extends LockMessageDecorator {

	public AppLockMessageDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService, reqc);
	}
	

}
