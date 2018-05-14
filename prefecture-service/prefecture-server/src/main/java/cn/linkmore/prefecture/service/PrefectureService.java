package cn.linkmore.prefecture.service;

import java.util.List;

import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;

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
	ResPrefectureDetail find(Long id);
	
	/**
	 * 根据当前位置获取车区列表
	 * @param cityId
	 * @param size
	 * @return
	 */
	List<ResPrefectureDetail> findPreListByLoc(ReqPrefecture reqPrefecture);
	
	/**
	 * 根据城市获取车区列表
	 * @param cityId
	 * @param size
	 * @return
	 */
	List<ResPrefectureDetail> findPreListByCityId(Long cityId);

}
