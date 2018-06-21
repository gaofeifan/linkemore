package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;

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
	 * @param stallId
	 * @return true 车位锁降下成功 false 车位锁降下失败
	 */
	void downlock(Long stallId);
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
	
}
