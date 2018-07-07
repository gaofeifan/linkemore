package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
/**
 * 远程调用实现 - 车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallClientHystrix implements StallClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass()); 
	 
	public void close(Long id) {
		log.info("prefecture service stall close(Long id) hystrix"); 
	}

	@Override
	public void order(Long id) {
		log.info("prefecture service stall order(Long id) hystrix"); 
	}

	@Override
	public boolean cancel(Long stallId) {
		log.info("prefecture service stall cancel(Long stallId) hystrix");
		return false;
	}

	@Override
	public void downlock(ReqOrderStall stall) {
		log.info("prefecture service stall downlock(ReqOrderStall stall) hystrix");
	}

	@Override
	public Boolean uplock(Long stallId) {
		log.info("prefecture service stall uplock(Long stallId) hystrix");
		return false;
	}

	@Override
	public Boolean checkout(Long stallId) {
		log.info("prefecture service stall checkout(Long stallId) hystrix");
		return false;
	}

	@Override
	public ResStallEntity findById(Long stallId) {
		log.info("prefecture service stall findById(Long stallId) hystrix");
		return new ResStallEntity();
	}
	public ResStallEntity findByLock(@PathVariable("sn") String sn) {
		log.info("prefecture service stall findByLock(String sn) hystrix");
		return null;
	}
	

	@Override
	public Tree tree() {
		log.info("prefecture service stall tree() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall list() hystrix");
		return null;
	}

	@Override
	public int save(ReqStall stall) {
		log.info("prefecture service stall save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqStall stall) {
		log.info("prefecture service stall update() hystrix");
		return 0;
	}

	@Override
	public int check(ReqCheck reqCheck) {
		log.info("prefecture service stall check() hystrix");
		return 0;
	}

	@Override
	public List<ResStallLock> sn(Long lockId) {
		log.info("prefecture service stall sn() hystrix");
		return null;
	}

	@Override
	public ResStallEntity detail(Long id) {
		log.info("prefecture service stall detail() hystrix");
		return null;
	}

	@Override
	public int bind(Long id, Long sid) {
		log.info("prefecture service stall bind() hystrix");
		return 0;
	}

	@Override
	public int changedUp(List<Long> ids) {
		log.info("prefecture service stall changedUp() hystrix");
		return 0;
	}

	@Override
	public int changedDown(Long id) {
		log.info("prefecture service stall changedDown() hystrix");
		return 0;
	}


	@Override
	public void saveAndBind(Long preId, String stallName, String sn) {
		log.info("prefecture service stall saveAndBind() hystrix");
	}

	@Override
	public List<ResStallOps> findList(Map<String, Object> param) {
		log.info("prefecture service List<ResStallOps> findList(Map<String, Object> param) hystrix");
		return null;
	}
	
}
