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
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.ent.service.EntUserPlateService;
import cn.linkmore.prefecture.client.OpsEntUserPlateClient;
import cn.linkmore.prefecture.client.OpsPrefectureClient;
import cn.linkmore.security.response.ResPerson;
/**
 * 公司免费车牌接口实现
 * @author   jiaohanbin
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@Service
public class EntUserPlateServiceImpl implements EntUserPlateService {

	@Resource
	private OpsEntUserPlateClient userPlateClient; 
	
	@Autowired
	private EnterpriseService enterService;
	
	@Resource
	private OpsPrefectureClient prefectureClient;
	
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
			/*vf.setProperty("createUserId");
			vf.setValue(person.getId());*/
			
			vf.setProperty("entId");
			vf.setValue(person.getEntId());
			
			list.add(vf);
		}
		return this.userPlateClient.list(pageable);
	}

	@Override
	public void save(ReqEntUserPlate user) {
		this.userPlateClient.save(user);
	}

	@Override
	public void update(ReqEntUserPlate user) {
		this.userPlateClient.update(user);
	}

	@Override
	public void delete(List<Long> ids) {
		this.userPlateClient.delete(ids);
	}

	@Override
	public boolean exists(Map<String,Object> checkParam) {
		return this.userPlateClient.exists(checkParam); 
	}

	@Override
	public int saveBatch(List<ReqEntUserPlate> plateList) {
	    return this.userPlateClient.saveBatch(plateList);
	}
	
}
