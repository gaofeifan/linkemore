package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.account.entity.User;
import cn.linkmore.prefecture.request.ReqPrefecture;
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
	 * @param language 语言
	 * @return
	 */
	ResPrefectureDetail find(Long id,String language);
	
	/**
	 * 根据当前位置获取车区列表
	 * @param reqPrefecture
	 * @param user
	 * @return
	 */
	List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture,User user);
	
	/**
	 * 根据城市获取车区卡片列表
	 * @param cityId
	 * @param user 
	 * @param language 
	 * @param size
	 * @return
	 */
	List<ResPrefectureList> findPreListByCityId(Long cityId, String language, User user);
	/**
	 * 根据车区id查询计费策略
	 * @param preId
	 * @param language
	 * @return
	 */
	ResPrefectureStrategy getPreStrategy(Long preId, String language);

}
