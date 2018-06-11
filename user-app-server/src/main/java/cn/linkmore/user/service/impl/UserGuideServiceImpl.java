package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserGuideClient;
import cn.linkmore.user.response.ResUserGuide;
import cn.linkmore.user.service.UserGuideService;
import cn.linkmore.util.ObjectUtils;

/**
 * 用户指南实现
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@Service
public class UserGuideServiceImpl implements UserGuideService {

	@Resource
	private UserGuideClient userGuideClient;
	
	@Override
	public List<ResUserGuide> find(String language) {
		List<cn.linkmore.account.response.ResUserGuide> list = this.userGuideClient.list(language);
		List<ResUserGuide> result = new ArrayList<>();
		for (cn.linkmore.account.response.ResUserGuide resUserGuide : list) {
			ResUserGuide guide = ObjectUtils.copyObject(resUserGuide, new ResUserGuide());
			result.add(guide);
		}
		return result;
	}

	
}
