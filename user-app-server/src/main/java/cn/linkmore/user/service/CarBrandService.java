package cn.linkmore.user.service;
/**
 * 车辆品牌数据接口
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
public interface CarBrandService {

	/**
	 * @Description  查询车辆品牌list  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Object list();

	/**
	 * @Description  从第三方获取车辆品牌数据加载到redis中
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void load();

}
