package cn.linkmore.ops.base.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.CarBrandClient;

/**
 * 车辆品牌实现
 * @author   GFF
 * @Date     2018年6月15日
 * @Version  v2.0
 */
@Service
public class CarBrandServiceImpl implements CarBrandService {

	@Resource
	private CarBrandClient carBrandClient;
	
	@Override
	public Map<String,Object > load(){
		return this.carBrandClient.load();
	}
}
