package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;

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

}
