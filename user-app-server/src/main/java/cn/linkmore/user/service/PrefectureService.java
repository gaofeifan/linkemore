package cn.linkmore.user.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import cn.linkmore.user.request.ReqPrefecture;
import cn.linkmore.user.response.ResPreCity;
import cn.linkmore.user.response.ResPrefectureList;
import cn.linkmore.user.response.ResPrefectureStrategy;

/**
 * Service接口 - 车区
 * @author liwenlong
 * @version 2.0
 *
 */
public interface PrefectureService {
	/**
	 * 获取车区地图列表
	 * @param request
	 * @return
	 */
	public List<ResPreCity> list(ReqPrefecture rp,HttpServletRequest request);
	
	/**
	 * 获取车区计费策略
	 * @param preId
	 * @param request
	 * @return
	 */
	public ResPrefectureStrategy findStrategyById(Long preId, HttpServletRequest request);
	/**
	 * 根据城市id获取车区列表
	 * @param rp
	 * @param request
	 * @return
	 */
	public List<ResPrefectureList> findPreListByCityId(Long cityId, HttpServletRequest request);
	/**
	 * 根据车位id查询空闲车位数量
	 * @param preId
	 * @return
	 */
	public Integer findFreeStallCount(Long preId);
}
