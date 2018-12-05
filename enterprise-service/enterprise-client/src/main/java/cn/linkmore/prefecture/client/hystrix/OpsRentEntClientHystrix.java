package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.prefecture.client.OpsRentEntClient;
@Component
public class OpsRentEntClientHystrix implements OpsRentEntClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("=======Hystrix==========list(ViewPageable pageable)");
		return null;
	}

	@Override
	public void save(ReqRentEnt ent) {
		log.info("=======Hystrix==========void save(ReqRentEnt ent) ");
		// TODO Auto-generated method stub
	}

	@Override
	public void update(ReqRentEnt ent) {
		log.info("=======Hystrix==========update(ReqRentEnt ent");
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(List<Long> ids) {
		log.info("=======Hystrix==========void delete(List<Long> ids)");
		// TODO Auto-generated method stub
	}

	@Override
	public ViewPage stallListCompany(ViewPageable pageable) {
		log.info("=======Hystrix==========ViewPage stallListCompany(ViewPageable pageable)");
		return null;
	}

	@Override
	public List<Tree> tree(Long entId) {
		log.info("=======Hystrix==========Tree tree(Long entId)");
		return null;
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		log.info("=======Hystrix==========int updateStatus(map) ={}",JSON.toJSON(map));
		return 0;
	}


}
