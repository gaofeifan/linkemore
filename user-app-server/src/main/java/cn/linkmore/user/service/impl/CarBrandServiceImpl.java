package cn.linkmore.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.CarBrandClient;
import cn.linkmore.user.service.CarBrandService;
/**
 * 车辆品牌数据接口实现
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class CarBrandServiceImpl implements CarBrandService {

	@Resource
	private CarBrandClient carBrandClient;
	
	@Override
	public Object list() {
		return this.carBrandClient.list();
	}
	
}
