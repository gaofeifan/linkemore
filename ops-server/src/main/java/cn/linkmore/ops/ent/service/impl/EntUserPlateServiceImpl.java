package cn.linkmore.ops.ent.service.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.ops.ent.service.EntUserPlateService;
import cn.linkmore.prefecture.client.OpsEntUserPlateClient;
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
	
	@Override
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
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
	public boolean exists(String plateNo) {
		return this.userPlateClient.exists(plateNo);
	}

	@Override
	public int saveBatch(List<ReqEntUserPlate> plateList) {
	    return this.userPlateClient.saveBatch(plateList);
	}
	
}
