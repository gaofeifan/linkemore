package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqLocate;
import cn.linkmore.third.response.ResLocate;

/**
 * Service接口 - 定位
 * @author liwenlong
 * @version 2.0
 */
public interface LocateService {

	/**
	 * 根据经纬度获取位置信息
	 * @param req
	 * @return
	 */
	ResLocate getInfo(ReqLocate req);

}
