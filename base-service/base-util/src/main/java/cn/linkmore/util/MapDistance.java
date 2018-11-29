package cn.linkmore.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据 经纬度 获取距离
 * 
 * @author user
 */
public class MapDistance {

	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM） 参数为String类型
	 * 
	 * @param lat1
	 *            用户经度
	 * @param lng1
	 *            用户纬度
	 * @param lat2
	 *            商家经度
	 * @param lng2
	 *            商家纬度
	 * @return
	 */
	public static Integer getDistance(Double lat1, Double lng1, Double lat2, Double lng2) {

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1) - rad(lng2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

		return Integer.parseInt(distanceStr);
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 *
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	/**
	 * 获取当前用户一定距离以内的经纬度值 单位米 return minLat 最小经度 minLng 最小纬度 maxLat 最大经度 maxLng
	 * 最大纬度 minLat
	 */
	public static Map<String, Object> getAround(Double latitude, Double longitude, Double raidusMile) {
		Map<String, Object> map = new HashMap<String, Object>();

		Double degree = (24901 * 1609) / 360.0; // 获取每度

		Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		// 获取最小经度
		Double minLng = longitude - radiusLng;
		// 获取最大经度
		Double maxLng = longitude + radiusLng;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		// 获取最小纬度
		Double minLat = latitude - radiusLat;
		// 获取最大纬度
		Double maxLat = latitude + radiusLat;

		map.put("minLat", minLat);
		map.put("maxLat", maxLat);
		map.put("minLng", minLng);
		map.put("maxLng", maxLng);

		return map;
	}

	public static void main(String[] args) {
		 System.out.println(getAround(39.92953,116.523869, 13000d));
	}
}
