package cn.linkmore.third.service;

import cn.linkmore.third.response.ResLocate;

/**
 * Service接口 - 定位
 * @author liwenlong
 * @version 2.0
 */
public interface LocateService {

	/**
	 * 根据经纬度获取位置信息
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	ResLocate getInfo(String longitude,String latitude);

	/**
	 * 计算两点间距离
	 * @param oriLng 起点
	 * @param oriLat 起点
	 * @param desLng 起点
	 * @param desLat 起点
	 * @return
	 */
	String distance(String oriLng, String oriLat, String desLng, String desLat);

}
