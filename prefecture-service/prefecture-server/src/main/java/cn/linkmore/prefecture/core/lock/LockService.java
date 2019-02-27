package cn.linkmore.prefecture.core.lock;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.controller.staff.response.ResGateway;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.response.ResGatewayDetails;
import cn.linkmore.prefecture.response.ResGatewayGroup;
import cn.linkmore.prefecture.response.ResLockGatewayList;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockMessage;
import cn.linkmore.prefecture.response.ResLocksGateway;
import cn.linkmore.prefecture.response.ResUnBindLock;

public interface LockService {
	/**
	 * @Description  根据锁编号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockInfo lockInfo(String lockSn);

	
	/**
	 * @Description  查询网关信号强度
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResSignalHistory lockSignalHistory(String sn);
	
	/**
	 * @Description  升起锁  （只返回为操作成功或失败）
	 * @Author   GFF 
	 * @Version  v2.0
	 * @return true  false
	 */
	public boolean upLock(String sn) ;
	
	/**
	 * @Description  升起锁(返回具体的信息 用于自定义处理)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockMessage upLockMes(String sn) ;
	
	/**
	 * @Description  下降锁 （只返回为操作成功或失败）
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Boolean downLock(String sn);
	
	/**
	 * @Description  下降锁 （返回操作的具体信息  用于自定义处理）
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResLockMessage downLockMes(String sn);
	
	/**
	 * @return 
	 * @Description  根据分组编号查询锁列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public List<ResLockInfo> lockListByGroupCode(String groupCode);
	
	/**
	 * @Description  同步锁平台
	 * @Author   cl 
	 */
	public void setLockName(Map<String, Object> map );


	String saveGroup(String groupName);


	Boolean removeGroupCode(String groupCode);

	/**
	 * @Description  绑定网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean bindGroup(String groupCode, String serialNumber);

	/**
	 * @Description  解除绑定
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean unbindGroup(String groupCode, String serialNumber);

	/**
	 * @Description  加载锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean loadAllLock(String serialNumber);

	/**
	 * @Description  查询网关详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResGatewayDetails getGatewayDetails(String serialNumber);
	
	/**
	 * @Description  查询车区下的网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResGatewayGroup> getGatewayGroup(String groupCode );
	

	/**
	 * @Description  查询网关下未绑定锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUnBindLock> unBindLockList(String serialNumber );

	/**
	 * @Description  查询网关下绑定锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUnBindLock> bindLockList(String serialNumber );
	
	/**
	 * @Description  锁绑定网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean bindLock(String gatewaySerialNumbe,String lockSerialNumber);

	/**
	 * @Description  锁解绑网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean unBindLock(String gatewaySerialNumbe,String lockSerialNumber);
	
	/**
	 * @Description  查询跟该网关有信号所有的锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResLocksGateway> getLocksGateway(String serialNumber);
	
	/**
	 * @Description  查询锁关联的网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResLockGatewayList> getLockGatewayList(String lockSerialNumber);
	
	/**
	 * @Description  重启网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean restart(String serialNumber);
	
	/**
	 * @Description  批量绑定网关
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean batchBindGateway(String lockSerialNumber,String[] gatewaySerials);


	/**
	 * @Description  删除锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Boolean removeLock(String serialNumber);
}
