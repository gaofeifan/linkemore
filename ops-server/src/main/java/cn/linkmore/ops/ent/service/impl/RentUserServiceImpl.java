package cn.linkmore.ops.ent.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.ops.ent.service.RentUserService;
import cn.linkmore.prefecture.client.OpsRentUserClient;
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
	
	@Override
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
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
