package cn.linkmore.prefecture.core.lock;

import cn.linkmore.prefecture.request.ReqControlLock;

public class EntLockDecorator extends LockDecorator {
	private ReqControlLock reqc;

	public EntLockDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService);
		this.reqc = reqc;
	}
	
}
