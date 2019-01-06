package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.prefecture.client.OpsEntUserPlateClient;

@Component
public class OpsEntUserPlateClientHystrix  implements OpsEntUserPlateClient{

	@Override
	public ViewPage list(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ReqEntUserPlate ent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ReqEntUserPlate ent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int saveBatch(List<ReqEntUserPlate> plateList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(Map<String, Object> checkParam) {
		// TODO Auto-generated method stub
		return false;
	}

}
