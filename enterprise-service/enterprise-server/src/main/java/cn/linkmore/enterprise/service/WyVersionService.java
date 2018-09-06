package cn.linkmore.enterprise.service;

import cn.linkmore.common.response.ResWyAppVersion;

/**
 * 物业版版本管理
 * @author   GFF
 * @Date     2018年9月6日
 * @Version  v2.0
 */
public interface WyVersionService {

	/**
	 * @Description  查询当前版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResWyAppVersion currentAppVersion(int appType, Object object);

}
