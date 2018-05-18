package cn.linkmore.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.user.request.ReqPrefecture;
import cn.linkmore.user.response.ResPrefecture;

/**
 * Service接口 - 车区
 * @author liwenlong
 * @version 2.0
 *
 */
public interface PrefectureService {
	/**
	 * 获取车区列表
	 * @param request
	 * @return
	 */
	public List<ResPrefecture> list(ReqPrefecture rp,HttpServletRequest request);
}
