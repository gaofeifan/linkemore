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
	 * 查询所有车区空闲车位数量
	 * @return
	 */
	public List<ResPrefectureList> refreshFreeStall();
}
