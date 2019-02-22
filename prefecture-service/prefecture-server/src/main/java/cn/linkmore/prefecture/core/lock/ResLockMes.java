package cn.linkmore.prefecture.core.lock;

public class ResLockMes {

	private boolean status;
	
	private Object obj;

	
	
	public ResLockMes(boolean status, Object obj) {
		super();
		this.status = status;
		this.obj = obj;
	}


	public boolean getStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
