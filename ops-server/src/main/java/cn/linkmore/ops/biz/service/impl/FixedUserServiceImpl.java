package cn.linkmore.ops.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.biz.service.FixedUserService;
import cn.linkmore.prefecture.client.FixedUserClient;
import cn.linkmore.security.response.ResPerson;

@Service
public class FixedUserServiceImpl implements FixedUserService {

	@Autowired
	private FixedUserClient fixedUserClient;
	@Autowired
	private EnterpriseService enterService;

	@Override
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {

		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
        if(person==null) {
        	return fixedUserClient.findPage(pageable);
        }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("property", "id");
		map.put("value", person==null?null:person.getEntId());
		ResEnterprise enter = enterService.find(map);
		if (enter != null) {
			List<ViewFilter> list = pageable.getFilters();
			ViewFilter vf = new ViewFilter();
			vf.setProperty("createUserId");
			vf.setValue(person.getId());
			list.add(vf);
		}
		return fixedUserClient.findPage(pageable);
	}

	@Override
	public void pick(HttpServletRequest request, ReqFixedUserPick reqFixedUserPick) {
		
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		if(person!=null) {
			reqFixedUserPick.setCreateUserId(Integer.valueOf(person.getId().toString()));
        }
		fixedUserClient.pick(reqFixedUserPick);
	}

}
