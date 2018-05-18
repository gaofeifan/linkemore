package cn.linkmore.user.service;

import java.util.List;

import cn.linkmore.user.response.ResCity;

/**
 * Service接口 - 城市
 * @author liwenlong
 * @version 2.0
 *
 */
public interface CityService {

	/**
	 * 城市列表
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return
	 */
	List<ResCity> list(String longitude,String latitude);

}
