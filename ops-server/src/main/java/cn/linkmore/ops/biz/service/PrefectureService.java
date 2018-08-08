package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.response.ResDistrict;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.ops.biz.response.ResPrefecture;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResFeeStrategy;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;

public interface PrefectureService {
	/**
	 * 专区下拉列表
	 * @return
	 */
	List<ResPreList> findSelectList();
	/**
	 * 城市列表
	 * @return
	 */
	List<ResCity> findCityList();
	/**
	 * 区域列表
	 * @return
	 */
	List<ResDistrict> findDistrictList(Long cityId);
	/**
	 * 计费系统列表
	 * @return
	 */
	List<ResOldDict> findBillSystemList();
	/**
	 * 计费策略下拉框列表
	 * @return
	 */
	List<ResFeeStrategy> findStrategyList();
	
	/**
	 * 专区信息列表
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 校验
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
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
	 * 查看详情
	 * @param id
	 * @return
	 */
	ResPrefectureDetail findById(Long id);
	/**
	 * 导出列表
	 * @param bean
	 * @return
	 */
	List<ResPreExcel> exportList(ReqPreExcel bean);
	/**
	 * 查询城市下车区列表
	 * @param cid
	 * @return
	 */
	List<Map<String, Object>> findByCity(Long cid);
	
	List<ResPrefectureDetail> findAll();

	List<ResPrefectureDetail> findList(Map<String, Object> param);
	/**
	 * 校验车区是否存在
	 * @param preName
	 * @return
	 */
	ResPrefectureDetail checkName(String preName);
	
	List<cn.linkmore.prefecture.response.ResPrefecture> findPreList();
	
}
