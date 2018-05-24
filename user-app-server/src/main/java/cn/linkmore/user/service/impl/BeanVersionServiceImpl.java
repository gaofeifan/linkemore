package cn.linkmore.user.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.BaseVersionClient;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.user.controller.BaseVersionController;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.BeanVersionService;
import cn.linkmore.user.service.UserService;
/**
 * 版本管理接口
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class BeanVersionServiceImpl implements BeanVersionService {

	
	@Resource
	private BaseVersionClient baseVersionClient;
	@Resource
	private UserService userService;
	@Override
	public ResVersionBean current(Integer source) {
		ResVersionBean bean = this.baseVersionClient.current(source);
		return bean;
	}

	@Override
	public void report(ReqVersion vrb,HttpServletRequest request) {
		ResUser user = userService.getCache(request);
		vrb.setUserId(user.getId());
		this.baseVersionClient.report(vrb);
	}
	
	
}
