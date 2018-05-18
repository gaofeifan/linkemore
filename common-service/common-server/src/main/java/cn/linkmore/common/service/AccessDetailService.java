package cn.linkmore.common.service;

import cn.linkmore.common.request.ReqAccessDetail;

/**
 * 接口访问详情接口
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
public interface AccessDetailService {

	/**
	 * @Description  app接口访问详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void appSave(ReqAccessDetail accessDetail);

	/**
	 * @Description  小程序接口访问详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void miniSave(ReqAccessDetail accessDetail);

}
