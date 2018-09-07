package cn.linkmore.enterprise.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.WyAppVersionClient;
import cn.linkmore.common.response.ResWyAppVersion;
import cn.linkmore.enterprise.service.WyVersionService;
/**
 * 物业版版本管理
 * @author   GFF
 * @Date     2018年9月6日
 * @Version  v2.0
 */
@Service
public class WyVersionServiceImpl implements WyVersionService {

	@Resource
	private WyAppVersionClient wyAppVersionClient;
	@Override
	public ResWyAppVersion currentAppVersion(int appType, Object object) {
		return this.wyAppVersionClient.current(appType);
	}

	
}
