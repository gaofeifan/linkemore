/**
 * 
 */
package cn.linkmore.prefecture.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;
import cn.linkmore.prefecture.fee.InitLockFactory;

/**
 * @author zyf
 * @version 2.0
 * 
 */
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LockFactory lockFactory = InitLockFactory.getInstance();
		ResponseMessage<LockBean> res = lockFactory.findAvailableLock("15");
		System.out.println(res.getDataList().get(0).getOpenState());
		/*
		LockFactory lockFactory = InitLockFactory.getInstance();
		ResponseMessage<LockBean> res=lockFactory.lockDown("FF6F31BEBDB5");
		if(res.getMsgCode() == 200) {
			ResponseMessage<LockBean> res2= lockFactory.getLockInfo("FF6F31BEBDB5");
			//{"cycleBetty":0,"downCount":0,"openState":0,"slaveId":"FF6F31BEBDB5","upCount":0,"voltage":64}
			//openState 0标识降下  1表示竖起来
			System.out.println(JSON.toJSONString(res2.getData()));
		}
		try {
			Thread.sleep(10000);
			ResponseMessage<LockBean> res3= lockFactory.getLockInfo("FF6F31BEBDB5");
			//{"cycleBetty":0,"downCount":0,"openState":0,"slaveId":"FF6F31BEBDB5","upCount":0,"voltage":64}
			//openState 0标识降下  1表示竖起来
			System.out.println(JSON.toJSONString(res3.getData()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	*/}

}
