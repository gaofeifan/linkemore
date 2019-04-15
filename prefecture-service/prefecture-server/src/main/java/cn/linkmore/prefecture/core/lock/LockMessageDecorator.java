package cn.linkmore.prefecture.core.lock;


import cn.linkmore.prefecture.request.ReqControlLock;

public class LockMessageDecorator extends LockDecorator {
	private ReqControlLock reqc;
	public LockMessageDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService);
		this.reqc = reqc;
	}

	public ReqControlLock getReqc() {
		return reqc;
	}
	

}
