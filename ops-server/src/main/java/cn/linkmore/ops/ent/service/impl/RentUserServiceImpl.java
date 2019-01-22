package cn.linkmore.ops.ent.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.ent.service.RentUserService;
import cn.linkmore.prefecture.client.OpsRentUserClient;
import cn.linkmore.security.response.ResPerson;
/**
 * 长租用户接口实现
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@Service
public class RentUserServiceImpl implements RentUserService {

	@Resource
	private OpsRentUserClient rentUserClient; 
	
	@Autowired
	private EnterpriseService enterService;
	
	@Override
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("property", "id");
		map.put("value", person.getEntId());
		ResEnterprise enter=enterService.find(map);
		if(enter != null) {
			List<ViewFilter> list = pageable.getFilters();
			ViewFilter vf = new ViewFilter();
	/*		vf.setProperty("createUserId");
			vf.setValue(person.getId());*/
			
			vf.setProperty("entId");
			vf.setValue(person.getEntId());
			
			list.add(vf);
		}
		return this.rentUserClient.findList(pageable);
	}

	@Override
	public void save(ReqRentUser user) {
		this.rentUserClient.save(user);
	}

	@Override
	public void update(ReqRentUser user) {
		this.rentUserClient.update(user);
	}

	@Override
	public void delete(List<Long> ids) {
		this.rentUserClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.rentUserClient.check(reqCheck);
	}

	
	
}
