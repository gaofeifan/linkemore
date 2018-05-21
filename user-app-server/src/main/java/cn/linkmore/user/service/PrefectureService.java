package cn.linkmore.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import cn.linkmore.user.request.ReqPrefecture;
import cn.linkmore.user.response.ResPrefecture;
import cn.linkmore.user.response.ResPrefectureDetail;
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
	public List<ResPrefecture> list(ReqPrefecture rp,HttpServletRequest request);
	/**
	 * 获取车区详情
	 * @param preId
	 * @param request
	 * @return
	 */
	public ResPrefectureDetail findById(Long preId, HttpServletRequest request);
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
}
