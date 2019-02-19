package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;

public interface StrategyGroupService {
	 /**
	  * 保存
	  * @param reqStrategyInterval
	  * @return
	  */
	int save(ReqStrategyGroup reqStrategyGroup);
	/**
	 * 更新
	 * @param reqStrategyInterval
	 * @return
	 */
	int update (ReqStrategyGroup reqStrategyGroup);
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	
	
	/**
	 * 更改状态-开启/关闭
	 * @param ids
	 * @return
	 */
	int updateStatus(Map<String, Object> map);

	/**
	 * 分页列表
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 列表无分页
	 * @param map
	 * @return
	 */
	List<ResStrategyGroup> findList(Map<String, Object> map);
	
	/**
	 * 获取单个对象
	 * @param id
	 * @return
	 */
	ResStrategyGroup selectByPrimaryKey(Long id);
	/**
	 * 获取车位分组树
	 * @param param
	 * @return
	 */
	Tree findTree(Map<String, Object> param);
	/**
	 * 根据专区id,areaid,startName,endName获取车区信息
	 * @param param
	 * @return
	 */
	List<ResStall> findAreaStall(Map<String, Object> param);
	
	/**
	 * 删除分组中的车位
	 * @param ids
	 * @return
	 */
	int deleteStall(Map<String, Object> map);
	
	/**
	 * 添加一个车位
	 * @param id
	 * @return
	 */
	int addStall(ReqStrategyGroupDetail reqStrategyGroupDetail);
	
	/**
	 * 显示分组信息
	 * @param id
	 * @return
	 */
	List<ResStrategyGroupArea> selectStallByPrimaryKey(Long id);
	/**
	 * 按名称查询车位是否存在
	 * @param stallName
	 * @return
	 */
	Long existsStall( Map<String, Object> map);
	/**
	 * 根据车位id查询分组获得分组下空闲车位数
	 * @param stallId
	 * @param preId 
	 * @return
	 */
	Long findFreeStall(Long stallId, Long preId);
	/**
	 * 查询附近空闲车位锁
	 * @param stallId
	 * @param preId
	 * @return
	 */
	String nearFreeStallLockSn(Long stallId, Long preId);
	


}
