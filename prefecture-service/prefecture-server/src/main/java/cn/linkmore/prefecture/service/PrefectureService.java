package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.response.ResStrategyBase;

/**
 * Service接口 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface PrefectureService {

	/**
	 * 根据ID查询对应的车区信息
	 * @param id 主键
	 * @return
	 */
	ResPrefectureDetail findById(Long id);
	
	/**
	 * 根据当前位置获取车区列表
	 * @param reqPrefecture
	 * @return
	 */
	List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture);
	
	/**
	 * 根据车区id查询计费策略
	 * @param preId
	 * @return
	 */
	ResPrefectureStrategy getPreStrategy(Long preId);
	/**
	 * 根据车区id集合查询车名名称集合
	 * @param ids
	 * @return
	 */
	List<ResPre> findList(List<Long> ids);
	/**
	 * 查询所有车区空闲车位
	 * @return
	 */
	List<ResPrefectureList> getStallCount();
	
	
	
	
	/**
	 * 查询分页
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 校验字段名称
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	/**
	 * 获取车区列表
	 * @return
	 */
	List<ResPreList> findSelectList();
	/**
	 * 新增
	 * @param prefecture
	 * @return
	 */
	int save(ReqPrefectureEntity prefecture);
	
	/**
	 * 更新
	 * @param prefecture
	 * @return
	 */
	int update(ReqPrefectureEntity prefecture);
	/**
	 * 获取城市下车区列表
	 * @param cityId
	 * @return
	 */
	List<Map<String, Object>> findByCity(Long cityId);
	/**
	 * 导出车区列表
	 * @param reqPreExcel
	 * @return
	 */
	List<ResPreExcel> exportList(ReqPreExcel reqPreExcel);
	
	/**
	 * 车区树
	 * @return
	 */
	Tree findTree();

}
