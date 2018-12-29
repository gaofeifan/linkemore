package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.controller.staff.request.ReqAssignStall;
import cn.linkmore.prefecture.controller.staff.request.ReqLockIntall;
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.controller.staff.response.ResStaffNewAuth;
import cn.linkmore.prefecture.controller.staff.response.ResStaffNewAuthPre;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallDetail;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallSn;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallOps;

/**
 * Service接口 - 车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface StallService {
	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * @param id
	 */
	void order(Long id);
	/**
	 * 取消订单释放车位
	 * @param stallId
	 */
	boolean cancel(Long stallId);
	/**
	 * 结账立场释放车位
	 * @param stallId
	 * @return true 车位锁升起成功 false 车位锁升起失败
	 */
	boolean checkout(Long stallId);
	/**
	 * 升锁操作
	 * @param stallId
	 * @return true 车位锁升起成功 false 车位锁升起失败
	 */
	boolean uplock(Long stallId);
	/**
	 * 降锁操作
	 * @param stall
	 * @return true 车位锁降下成功 false 车位锁降下失败
	 */
	void downlock(ReqOrderStall stall);
	/**
	 * 根据车位id查询正常状态下车位信息
	 * @param preId
	 * @return
	 */
	List<ResStall> findStallsByPreId(Long preId);
	/**
	 * 根据车位id查询车位信息
	 * @param stallId
	 * @return
	 */
	ResStallEntity findById(Long stallId);
	/**
	 * 根据锁编号查询车位
	 * @param sn 编号
	 * @return
	 */
	ResStallEntity findByLock(String sn);
	

	/**
	 * 查询分页
	 * 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * 
	 * @param stall
	 */
	int save(ReqStall stall);

	/**
	 * 修改
	 * 
	 * @param stall
	 */
	int update(ReqStall stall);

	/**
	 * 校验名称
	 * 
	 * @param name
	 * @return
	 */
	int check(ReqCheck reqCheck);

	/**
	 * 绑定车位锁
	 * 
	 * @param stall
	 */
	int bind(ReqStall stall);

	/**
	 * 上下线修改
	 * 
	 * @param stall
	 */
	int updateStatus(ReqStall stall);

	/**
	 * 查询列表
	 * 
	 * @param param
	 * @return
	 */
	List<ResStall> findList(Map<String, Object> param);

	/**
	 * 保存并绑定
	 */
	void saveAndBind(Long preId, String stallName, String sn);
	
	
	List<ResStallOps> findListByParam(Map<String, Object> param);
	
	/**
	 * 订单关闭释放车位
	 * @param id
	 */
	void close(Long id);
	
	/**
	 * 查询车位列表
	 * @param stallIds
	 * @return
	 */
	List<ResStall> findStallList(List<Long> stallIds);
	/**
	 * 根据类型和stallIds查询车位信息
	 * @param param
	 * @return
	 */
	List<ResStall> findPreStallList(Map<String, Object> param);
	
	int updateBrand(Map<String, Object> param);
	
	
	/**
	 * 控制车位锁
	 * @param 
	 * @return
	 */
	void controling (ReqControlLock  reqc);
	
	/**
	 * 管理版控制车位锁
	 * @param 
	 * @return
	 */
	void operating (ReqControlLock  reqc);
	
	/**
	 * 管理版控制车位锁
	 * @param 
	 * @return
	 */
	void operatingsn (ReqControlLock  reqc);
	
	/**
	 * 查看车位锁状态
	 * @param 
	 * @return
	 */
	Map<String,Object>  watch(Long stallId);
	
	/**
	 * 查询车位锁信息
	 * @param 
	 * @return
	 */
	Map<String,Object>   lockStatus(List<String> parkcodes);
	
	/**
	 * @Description  物业版操作锁
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void operLockWY(ReqControlLock reqc);
	
	/**
	 * @Description  查询车区列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffPreList> findPreList(HttpServletRequest request, Long cityId);
	
	/**
	 * @Description  查询车位列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffStallList> findStallList(HttpServletRequest request, ReqStaffStallList staffList);
	
	/**
	 * @Description  查询车位详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResStaffStallDetail findStaffStallDetails(HttpServletRequest request, Long stallId);
	
	/**
	 * @Description  管理指定车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	String staffAssign(ReqAssignStall bean, HttpServletRequest request);
	
	/**
	 * @Description  删除管理版指定车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void staffAssignDel(ReqAssignStall bean);
	
	/**
	 * @Description  安装地锁
	 * @Author   cl
	 * @Version  v2.0
	 */
	void install(ReqLockIntall reqLockIntall, HttpServletRequest request);
	
	/**
	 * @Description  查询角色城市
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean checkStaffCityAuth(Long userId, Long cityId);

	/**
	 * @Description  根据条件查询车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStall> findStallsByPreIds(Map<String, Object> map);
	
	/**
	 * @Description  校验管理版车区
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean checkStaffPreAuth(Long userId, Long preId);
	
	/**
	 * @Description  校验管理版车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean checkStaffStallAuth(Long userId, Long stallId);
	
	/**
	 * @Description  复位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void reset(Long stallId, HttpServletRequest request);
	
	
	/**
	 * @Description  根据车位锁编号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResStaffStallSn findStaffStallSn(HttpServletRequest request, String sn);
	
	/**
	 * @Description  查询车位锁在一定时间端内的信号强度变化
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResSignalHistory lockSignalHistory(HttpServletRequest request, String sn);
	
	/**
	 * 取消绑定
	 * @param stallId
	 * @return
	 */
	int unBind(List<Long> ids);
	
	/**
	 * @Description  管理版使用
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> watch2(Long stallId);
	
	List<ResStaffNewAuth> findNewAuth(Long cityId, HttpServletRequest request);
	
	boolean control(Long stallId, HttpServletRequest request);
	
	void watchDownResult(Long stallId, HttpServletRequest request);
}
