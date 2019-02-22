package cn.linkmore.prefecture.core.lock;

import cn.linkmore.prefecture.request.ReqControlLock;

public class StaffLockDecorator extends LockDecorator {

	private ReqControlLock reqc;

	public StaffLockDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService);
		this.reqc = reqc;
	}
	
	
}
