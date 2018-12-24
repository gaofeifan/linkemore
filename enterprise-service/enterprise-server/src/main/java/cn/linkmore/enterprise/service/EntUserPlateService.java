package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntUserPlate;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
/**
 * 企业免费车牌
 * @author   jiaohanbin
 * @Date     2018年11月26日
 * @Version  v2.0
 */
public interface EntUserPlateService {
	
	/**
	 * @Description  分页查询
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * @Description  新增
	 * @Version  v2.0
	 */
	void save(EntUserPlate entUserPlate);

	/**
	 * @Description  更新
	 * @Version  v2.0
	 */
	void update(EntUserPlate entUserPlate);

	/**
	 * @Description  删除
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	int exists(String plateNo);

	int saveBatch(List<ReqEntUserPlate> plateList);
	
}